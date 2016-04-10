package com.chejixing.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 流量统计类
 */
public class IoCounter{
    public static IoCounter inst = new IoCounter();
    {
        Timer timer = new Timer("counter");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                inst.record();
            }
        },0,60*1000*60*24);
    }

    private final Logger logger = LoggerFactory.getLogger(IoCounter.class);
    //统计所有的指标
    protected Map data = new HashMap();

    public IoCounter() {
        super();
    }

    //指标累加
    public void setInc(Object key,Number value){
         Number old = (Number) data.get(key);
         if(old!=null){
             value = value.longValue()+old.longValue();
         }
        data.put(key,value);

    }

    //保存到日志
    public void record(){
        logger.error(
                "流量统计:{}", data);
    }

}
