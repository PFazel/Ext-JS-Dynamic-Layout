package com.extdl.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Hashtable;

/**
 * User: fereidooni
 * Date: Aug 4, 2010
 * Time: 2:59:33 PM
 */
public class Restrictor {
    private int periodInMilliSecs;
    private int baseCount;
    private Hashtable<String, Integer> IPMap;
    private long tick;

    public Restrictor(int periodInHour, int baseCount) {
        this.periodInMilliSecs = periodInHour * 3600000;//change hour to milliseconds
        this.baseCount = baseCount;
        IPMap = new Hashtable<String, Integer>();
        tick = (new Date()).getTime() / periodInMilliSecs;
    }

    public void newRequest(HttpServletRequest req) {
        String IP = req.getRemoteAddr();
        long newTick = (new Date()).getTime() / periodInMilliSecs;
        synchronized (Restrictor.class) {
            if (tick < newTick) {
                tick = newTick;
                IPMap.clear();
                IPMap.put(IP, 1);
            } else {
                if (IPMap.get(IP) == null) {
                    IPMap.put(IP, 1);
                } else {
                    IPMap.put(IP, IPMap.get(IP) + 1);
                }
            }
        }
    }

    public Boolean isCaptchaRequired(HttpServletRequest req) {
        String ip = req.getRemoteAddr();
        long newTick = (new Date()).getTime() / periodInMilliSecs;
        Boolean result;
        synchronized (Restrictor.class) {
            result = ((newTick == tick) && (IPMap.get(ip) != null) && IPMap.get(ip) >= baseCount) || baseCount == 0;
        }
        return result;
    }
}
