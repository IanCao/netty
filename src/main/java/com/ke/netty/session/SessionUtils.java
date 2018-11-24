package com.ke.netty.session;

import com.ke.netty.data.attr.Attributes;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class SessionUtils {
    // userId -> channel 的映射
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<String, Channel>();
    private static final Map<String, ChannelGroup> groupIdChannelMap = new ConcurrentHashMap<String, ChannelGroup>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelMap.get(groupId);
    }

    public static void setChannelGroup(String groupId, ChannelGroup channels) {
        groupIdChannelMap.put(groupId, channels);
    }

}
