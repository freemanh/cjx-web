package com.chejixing.biz.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Entity
@Table(name = "alarm")
public class Alarm extends XBean {
	private Byte grade = 0x01;
	private String descp;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date clearTime;
	@ManyToOne
	@JoinColumn(name = "device_id")
	private XDevice device;
	private boolean messageSent = false;
	@Column(name = "alarm_type")
	private AlarmType type;
	private Double reading;
	@ManyToOne
	@JoinColumn(name = "sensor_id")
	private XSensor sensor;
	private Double min;
	private Double max;

	public Alarm() {
		super();
	}

	public Alarm(XDevice device, AlarmType type) {
		this.device = device;
		this.type = type;
	}

	public Alarm(double reading, XSensor sensor, double min, double max, AlarmType type) {
		this.sensor = sensor;
		this.device = sensor.getDevice();
		this.reading = reading;
		this.min = min;
		this.max = max;
		this.type = type;
	}

	public String getFormatEL() {
		switch (type) {
		case POWEROFF:
			return "'设备：' + device.name + '出现断电报警，请及时处理！'";
		case OVER_HEAT:
			return "'传感器：[' + sensor.name + ']温度出现异常！当前温度：' + reading + '，正常温度区间为：[' + min + '-' + max + ']'";
		case OVER_HUM:
			return "'传感器：[' + sensor.name + ']湿度出现异常！当前湿度：' + reading + '，正常湿度区间为：[' + min + '-' + max + ']'";
		default:
			throw new IllegalArgumentException("Unknow device alarm type:" + type);
		}
	}

	/**
	 * @return
	 * @deprecated TODO: remove to other class, this class will be focus on
	 *             database mapping
	 */
	public String getFormattedMessage() {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext elContext = new StandardEvaluationContext(this);
		return parser.parseExpression(this.getFormatEL()).getValue(elContext, String.class);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Alarm) {
			Alarm u = (Alarm) obj;
			Long uid = u.getId();
			if (uid != null && uid.equals(this.getId())) {
				return true;
			}
		}
		return false;
	}

	public Byte getGrade() {
		return this.grade;
	}

	public void setGrade(Byte grade) {
		this.grade = grade;
	}

	public String getDescp() {
		return this.descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getClearTime() {
		return this.clearTime;
	}

	public void setClearTime(Date clearTime) {
		this.clearTime = clearTime;
	}

	public boolean isMessageSent() {
		return messageSent;
	}

	public void setMessageSent(boolean messageSent) {
		this.messageSent = messageSent;
	}

	public XDevice getDevice() {
		return device;
	}

	public void setDevice(XDevice device) {
		this.device = device;
	}

	public AlarmType getType() {
		return type;
	}

	public void setType(AlarmType type) {
		this.type = type;
	}

	public Double getReading() {
		return reading;
	}

	public void setReading(Double reading) {
		this.reading = reading;
	}

	public XSensor getSensor() {
		return sensor;
	}

	public void setSensor(XSensor sensor) {
		this.sensor = sensor;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

}