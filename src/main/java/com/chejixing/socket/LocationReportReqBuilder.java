package com.chejixing.socket;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.chejixing.socket.message.LocationReportReq;

public class LocationReportReqBuilder {
	private LocationReportReq req = new LocationReportReq();
	private DateFormat df = new SimpleDateFormat("yyMMddHHmmss");

    //by zjh:��������
    private static final int FACTOR=1000000;
    
    public void addTemps(List<Double> temps){
    	for(Double temp:temps){
    		this.addTemp(temp);
    	}
    }
    
    public void addHumidities(List<Double> humidities){
    	for(Double hum: humidities){
    		req.getHumidities().add(hum);
    	}
    }
	
	public void setLat(int lat){
		req.setLat((double)lat/FACTOR);
	}
	public void setLon(int lon){
		req.setLon((double)lon/FACTOR);
	}
	public void setAltitude(int eleviation){
		req.setEleviation(eleviation);
	}
	public void setSpeed(int speed){
		req.setSpeed(speed / 10);
	}
	public void setDirection(int direction){
		req.setDirection(direction);
	}
	public void setCollectTime(String time) throws ParseException{
		req.setCollectTime(df.parse(time));
	}
	public void addTemp(Double temp){
		this.req.getTemperatures().add(temp);
	}
	
	public LocationReportReq getResult(){
		return this.req;
	}
}
