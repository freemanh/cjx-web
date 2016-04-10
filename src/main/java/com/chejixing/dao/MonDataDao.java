package com.chejixing.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chejixing.biz.bean.XDevice;
import com.chejixing.biz.bean.XMonData;
import com.chejixing.biz.bean.XSensor;
import com.chejixing.socket.message.LocationReportReq;

@Repository
public class MonDataDao {
	private static Logger logger = LoggerFactory.getLogger(MonDataDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public String getTableName(String deviceCode) {
		return "MONDATA_" + deviceCode;
	}

	public void createTable(XDevice device) {
		String sql = "create table " + this.getTableName(device.getCode()) + "(" + "id bigint(20) not null auto_increment," + "collect_time datetime not null," + "device_code varchar(255) not null, "
				+ "humidity double not null," + "sensor_index tinyint(4) not null," + "temperature double not null," + "create_time datetime not null," + "original_temp double,"
				+ "original_humidity double," + "primary key(id))";
		this.sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	public void save(XSensor sensor) {
		if (sensor.getTemperature() == null || sensor.getHumidity() == null) {
			logger.warn("该传感器温度或者湿度为空，不保存历史记录");
			return;
		}
		logger.warn("开始保存温湿度信息");
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				"insert into " + this.getTableName(sensor.getDevice().getCode()) + " (collect_time, device_code, humidity, sensor_index, temperature, create_time) " + " values(?,?,?,?,?,?)");
		Date collectTime = sensor.getCollectTime();
		if (collectTime == null) {
			collectTime = new Date();
		}
		query.setTimestamp(0, collectTime);
		query.setString(1, sensor.getDevice().getCode());
		query.setDouble(2, sensor.getHumidity());
		query.setInteger(3, sensor.getDevice().getSensors().indexOf(sensor));
		query.setDouble(4, sensor.getTemperature());
		query.setTimestamp(5, new Date());

		query.executeUpdate();
		logger.warn("已保存温湿度信息:code=" + sensor.getDevice().getCode() + ",temp=" + sensor.getTemperature() + ",hum=" + sensor.getHumidity());

	}

	@SuppressWarnings("unchecked")
	public List<XMonData> query(long sensorId, Date collectDate, int hour) {
		Session session = sessionFactory.getCurrentSession();

		XSensor sensor = (XSensor) session.get(XSensor.class, sensorId);
		XDevice device = sensor.getDevice();

		String tableName = this.getTableName(sensor.getDevice().getCode());

		Calendar cal = Calendar.getInstance();
		cal.setTime(collectDate);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Date beginDate = cal.getTime();

		cal.add(Calendar.HOUR_OF_DAY, 1);

		Date endDate = cal.getTime();

		SQLQuery query = (SQLQuery) session.createSQLQuery(
				"select id as id,  collect_time as collectTime, device_code as deviceCode, humidity, temperature, sensor_index as sensorIndex" + " from " + tableName
						+ " where device_code=:deviceCode and sensor_index=:sensorIndex and collect_time between :beginDate and :endDate order by collect_time").setResultTransformer(
				Transformers.aliasToBean(XMonData.class));

		query.addScalar("sensorIndex", IntegerType.INSTANCE);
		query.addScalar("id", LongType.INSTANCE);
		query.addScalar("collectTime");
		query.addScalar("deviceCode");
		query.addScalar("humidity");
		query.addScalar("temperature");

		query.setString("deviceCode", device.getCode());
		query.setInteger("sensorIndex", device.getSensors().indexOf(sensor));
		query.setTimestamp("beginDate", beginDate);
		query.setTimestamp("endDate", endDate);
		return query.list();

	}

	// 查询一段时间范围内的历史数据
	public List historyQuery(Map result, long sensorId, Date beginTime, Date endTime, int from, int maxCount) {
		Session session = sessionFactory.getCurrentSession();
		XSensor sensor = (XSensor) session.get(XSensor.class, sensorId);
		XDevice device = sensor.getDevice();

		String tableName = this.getTableName(sensor.getDevice().getCode());
		String fromClause = " from " + tableName + " where device_code=:deviceCode and sensor_index=:sensorIndex and collect_time between :beginDate and :endDate order by collect_time";
		// 先查总条数
		SQLQuery query = (SQLQuery) session.createSQLQuery("select count(*) as count" + fromClause);
		query.addScalar("count", IntegerType.INSTANCE);
		query.setString("deviceCode", device.getCode());
		query.setInteger("sensorIndex", device.getSensors().indexOf(sensor));
		query.setTimestamp("beginDate", beginTime);
		query.setTimestamp("endDate", endTime);
		Integer count = (Integer) query.uniqueResult();

		query = (SQLQuery) session.createSQLQuery("select id as id, temperature,  humidity,  date_format(collect_time,'%Y-%m-%d %H:%i') as collectTime" + fromClause);
		// .setResultTransformer(Transformers.aliasToBean(XMonData.class));

		// query.addScalar("sensorName", IntegerType.INSTANCE);
		// query.addScalar("id", LongType.INSTANCE);
		// query.addScalar("collectTime");
		// query.addScalar("deviceCode");
		// query.addScalar("humidity");
		// query.addScalar("temperature");

		query.setString("deviceCode", device.getCode());
		query.setInteger("sensorIndex", device.getSensors().indexOf(sensor));
		query.setTimestamp("beginDate", beginTime);
		query.setTimestamp("endDate", endTime);

		// query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		query.setFirstResult(from);
		query.setMaxResults(maxCount);

		// key的名称仅仅适用于jquery dataTables
		result.put("iTotalDisplayRecords", count);
		List list = query.list();
		result.put("data", list);
		result.put("iTotalRecords", list.size());

		return query.list();
	}

	// by zjh:实时温湿度曲线查询
	public List getRealTimeSpline(String deviceId, Integer sensorId) throws Exception {
		String hql = "select temperature ,humidity,collect_time as collectTime" + " from " + getTableName(deviceId) + " where sensor_index=:sensorId" + " order by collect_time desc";

		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery(hql);

		// Date to = new Date();
		// Date from = new Date(to.getTime()-to.getTime()%6000*60*1000);
		// q.setDate("from", from);//.setDate("to", to);

		q.setInteger("sensorId", sensorId);
		q.setMaxResults(10);
		q.setFirstResult(0);
		List list = q.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> listOrderByCreateDate(XDevice device, int i) {
		String sql = "select * from " + getTableName(device.getCode()) + " where sensor_index=:sensorIndex " + " order by create_time";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addScalar("temperature", DoubleType.INSTANCE).addScalar("humidity", DoubleType.INSTANCE).addScalar("id", LongType.INSTANCE);
		query.setInteger("sensorIndex", i);

		return query.list();
	}

	public void updateData(double refinedTemp, double originalTemp, double refinedHumidity, double originalHumidity, XDevice device, Long id) {
		String sql = "update " + getTableName(device.getCode()) + " set temperature=:refinedTemperature, humidity=:refinedHumidity, original_temp=:originalTemp, original_humidity=:originalHumidity"
				+ " where id=:id";

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setDouble("refinedTemperature", refinedTemp);
		query.setDouble("refinedHumidity", refinedHumidity);
		query.setDouble("originalTemp", originalTemp);
		query.setDouble("originalHumidity", originalHumidity);
		query.setLong("id", id);

		query.executeUpdate();
	}

	// by zjh:保存GPS信息
	public void saveLocation(String deviceCode, LocationReportReq loc) throws Exception {
		// 可改造为在注册时创建表
		createLocationTable(deviceCode);

		Session session = sessionFactory.getCurrentSession();
		String tableName = getTableName(deviceCode, "Location");
		// 历史记录
		String sql = "insert into " + tableName + "(device_code,latitude,longitude,elevation,direction,speed,collect_time)" + " values(?,?,?,?,?,?,?)";
		Query query = session.createSQLQuery(sql).setString(0, deviceCode).setDouble(1, loc.getLat()).setDouble(2, loc.getLon()).setInteger(3, loc.getEleviation()).setInteger(4, loc.getDirection())
				.setDouble(5, loc.getSpeed()).setTimestamp(6, loc.getCollectTime());
		query.executeUpdate();
		// 实时经纬度信息
		sql = "update XDevice set longitude=?,latitude=? where phoneNo=?";
		query = session.createSQLQuery(sql);
		query.setDouble(0, loc.getLat()).setDouble(1, loc.getLon()).setString(2, deviceCode);
		query.executeUpdate();
	}

	// test only
	static double offset = 0;

	// 查询实时经纬度
	public List getDeviceLocations() throws Exception {
		String sql = "select new map(name as name,longitude as lon,latitude as lat) from XDevice where company=? and longitude is not null and latitude is not null";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		query.setLong(0, getCompanyId())
		// .setDouble(1,offset)
		;
		offset += 0.01;
		return query.list();
	}

	// 查询历史经纬度
	public List getDeviceHistoryLocations(String deviceCode, Date startTime, Date endTime) throws Exception {
		String tableName = getTableName(deviceCode, "location");
		String sql = "select longitude as lon,latitude as lat from " + tableName + " where device_code='" + deviceCode + "' and collect_time between :startTime and :endTime";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query.setDate("startTime", startTime);
		query.setDate("endTime", endTime);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		;
		return query.list();
	}

	// 设备列表
	public List getDevices() throws Exception {
		String sql = "select new map(name as name,code as code) from XDevice";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		return query.list();
	}

	public void createLocationTable(String deviceCode) throws Exception {
		Session session = sessionFactory.getCurrentSession();

		String tableName = getTableName(deviceCode, "Location");
		String sql = "create table if not exists " + tableName + "(" + "device_code varchar(255) not null," + "latitude double," + "longitude double," + "elevation int," + "direction int,"
				+ "speed double," + "collect_time datetime);";
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();

	}

	// 得到分表的表名
	public String getTableName(String deviceCode, String type) {
		return type + "_" + deviceCode;
	}

	// 根据名称查找sensor
	public XSensor findSensor(String name) throws Exception {
		Session s = sessionFactory.getCurrentSession();

		String hql = "from XSensor s where s.name=?";
		Query query = s.createQuery(hql);
		query.setString(0, name);
		Object result = query.uniqueResult();
		return (XSensor) result;
	}

	// 当前公司id
	public Long getCompanyId() {
		// todo:需要换成实际的id
		return 1L;
	}

	public void save(XMonData monData) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
				"insert into " + this.getTableName(monData.getDeviceCode()) + " (collect_time, device_code, humidity, sensor_index, temperature, create_time) " + " values(?,?,?,?,?,?)");
		query.setTimestamp(0, monData.getCollectTime());
		query.setString(1, monData.getDeviceCode());
		if (null == monData.getHumidity()) {
			query.setParameter(2, null);
		} else {
			query.setDouble(2, monData.getHumidity());
		}
		query.setInteger(3, monData.getSensorIndex());
		if (null == monData.getTemperature()) {
			query.setParameter(4, null);
		} else {
			query.setDouble(4, monData.getTemperature());
		}

		query.setTimestamp(5, new Date());

		query.executeUpdate();
	}

	public void update(XMonData data) {
		String tableName = this.getTableName(data.getDeviceCode());

		String sql = "update " + tableName + " set device_code = :deviceCode , sensor_index = :sensorName, temperature = :temp, humidity = :hum " + " where id=:id";

		Session session = sessionFactory.getCurrentSession();
		SQLQuery update = session.createSQLQuery(sql);
		update.setParameter("deviceCode", data.getDeviceCode());
		update.setParameter("sensorName", data.getSensorIndex());
		update.setParameter("temp", data.getTemperature());
		update.setParameter("hum", data.getHumidity());
		update.setParameter("id", data.getId());

		update.executeUpdate();
	}

	public void delete(XMonData data) {
		String tableName = this.getTableName(data.getDeviceCode());

		SQLQuery stat = sessionFactory.getCurrentSession().createSQLQuery("delete from " + tableName + " where id=?");
		stat.setParameter(0, data.getId());
		stat.executeUpdate();
	}
}
