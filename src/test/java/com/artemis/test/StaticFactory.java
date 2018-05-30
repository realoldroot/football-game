package com.artemis.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhengenshen
 * @date 2018-05-30 9:31
 */

public class StaticFactory {

    public static Map<Integer, Customer> maps = new HashMap<>();

    public static Customer get(int id) {
        return maps.get(id);
    }

    public static void add(Customer... c) {
        for (Customer customer : c) {
            maps.put(customer.getId(), customer);
        }

    }
}
