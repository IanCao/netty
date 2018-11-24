package com.ke.netty.utils;


import com.ke.netty.data.attr.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        Boolean flag = loginAttr.get();
        System.out.println("是否已经登录成功的标记为 ： " + flag);
        if (flag == null) {
            return false;
        }
        return flag;
    }
}
