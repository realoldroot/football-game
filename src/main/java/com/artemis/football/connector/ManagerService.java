package com.artemis.football.connector;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhengenshen
 * @date 2018-05-19 10:49
 */

public class ManagerService {

    private static ConcurrentHashMap<Integer, Integer> authStep = new ConcurrentHashMap<>();

    private static int pId = 0;

    static boolean checkIp(String ip) {
        // ArrayList<Long[]> ipList = Boot.getManagerAllowIps();
        // for (Long[] longs : ipList) {
        //     if (BaseIp.checkIp(ip, longs)) {
        //         return true;
        //     }
        // }
        return true;
    }

    static Integer getStep(Integer cid) {
        Integer setp = authStep.get(cid);
        if (setp == null) {
            setp = 1;
            authStep.put(cid, 1);
        }
        return setp;
    }

    static void goNextSetp(Integer cid) {
        Integer step = authStep.get(cid);
        if (step == null) {
            authStep.put(cid, 1);
        } else {
            authStep.put(cid, ++step);
        }
    }

    static void clear(Integer cid) {
        authStep.remove(cid);
    }

    static int getPid() {
        if (pId == 0) {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            String name = runtime.getName();
            try {
                pId = Integer.parseInt(name.substring(0, name.indexOf('@')));
            } catch (Exception e) {
                pId = -1;
            }
        }
        return pId;
    }
}
