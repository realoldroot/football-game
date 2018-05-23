package com.artemis.football.connector;

import com.artemis.football.model.Response;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author zhengenshen
 * @date 2018-05-21 17:37
 */

@Slf4j
public class SessionManager {
    private static ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    public static Set<Integer> checked = new ConcurrentSkipListSet<>();
    private static ConcurrentMap<String, Channel> userIdChannels = new ConcurrentHashMap<>();


    public static void add(Channel channel) {
        group.add(channel);
        checked.add(channel.hashCode());
    }

    public static boolean contains(Channel channel) {
        return checked.contains(channel.hashCode());
    }

    public static boolean notContains(Channel channel) {
        return !contains(channel);
    }

    public static void close() {
        checked.clear();
        group.close();
    }

    /**
     * 广播
     *
     * @param o
     */
    public static void broadcast(Object o) {
        group.writeAndFlush(Response.broadcast(o));
    }

    /**
     * 用户下线，通讯组里删除，检查里删除
     */
    public static void remove(Channel channel) {
        group.remove(channel);
        checked.remove(channel.hashCode());
    }

    public static void loginSuccessReturn() {

    }

    /**
     * 鉴权成功上线
     */
    public static void online(Channel channel) {
        add(channel);
        channel.writeAndFlush(Response.success());
    }

    public static void offline(Channel channel) {
        remove(channel);
    }


    public static void show() {
        checked.forEach(k -> log.error(k + ""));
    }
}
