package com.artemis.football.connector;

import com.artemis.football.model.Response;
import com.artemis.football.model.entity.User;
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
    private static ConcurrentMap<Integer, Channel> userIdChannels = new ConcurrentHashMap<>();


    public static void add(Channel channel, User user) {
        group.add(channel);
        checked.add(channel.hashCode());
        userIdChannels.put(user.getId(), channel);
    }

    public static boolean contains(Channel channel) {
        return checked.contains(channel.hashCode());
    }

    public static boolean notContains(Channel channel) {
        return !contains(channel);
    }

    /**
     * 广播
     */
    public static void broadcast(Object o) {
    }

    /**
     * 用户下线，通讯组里删除，检查里删除
     */
    public static void remove(Channel channel) {
        group.remove(channel);
        checked.remove(channel.hashCode());
        if (channel.hasAttr(IBaseConnector.USER)) {
            Integer uid = channel.attr(IBaseConnector.USER).get().getId();
            if (uid != null) {
                userIdChannels.remove(uid);
            }
        }
    }

    public static Channel getChannel(Object uid) {
        return userIdChannels.getOrDefault(uid, null);
    }

    public static void show() {
        checked.forEach(k -> log.info(k + ""));
        userIdChannels.forEach((k, v) -> log.info(k + " : " + v + "\t"));
    }
}
