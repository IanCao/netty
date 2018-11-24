package com.ke.netty.data.packet;

/**
 * @author caoyixiong
 * @Date: 2018/11/22
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
    Byte DATA = 5;
    Byte CREATE_GROUP_REQUEST = 6;
    Byte CREATE_GROUP_RESPONSE = 7;

    Byte JOIN_GROUP_REQUEST = 8;
    Byte JOIN_GROUP_RESPONSE = 9;

    Byte QUIT_GROUP_REQUEST = 10;
    Byte QUIT_GROUP_RESPONSE = 11;

    Byte LIST_GROUP_MEMBERS_REQUEST = 12;
    Byte LIST_GROUP_MEMBERS_RESPONSE = 13;

    Byte SEND_TO_GROUP_REQUEST = 14;
    Byte SEND_TO_GROUP_RESPONSE = 15;
}
