package com.artemis.football.connector;

import com.artemis.football.model.entity.User;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author zhengenshen
 * @date 2018-05-21 17:37
 */

@Slf4j
public class SessionManager {
    private static ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private static ConcurrentMap<Integer, Channel> userIdChannels = new ConcurrentHashMap<>();


    public static void add(Channel channel, User user) {
        group.add(channel);
        userIdChannels.put(user.getId(), channel);
    }

    /**
     * 用户下线，通讯组里删除
     * 如果通道存储的有用户信息，一样清理
     */
    public static void remove(Channel channel) {
        group.remove(channel);
        if (channel.hasAttr(IBaseConnector.USER)) {
            Integer uid = channel.attr(IBaseConnector.USER).get().getId();
            if (uid != null) {
                userIdChannels.remove(uid);
            }
        }
    }

    public static Channel getChannel(long uid) {
        return userIdChannels.getOrDefault(uid, null);
    }

    public static void show() {
        userIdChannels.forEach((k, v) -> log.info(k + " : " + v + "\t"));
    }
}
