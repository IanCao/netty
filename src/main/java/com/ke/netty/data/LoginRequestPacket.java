package com.ke.netty.data;

import com.ke.netty.data.packet.Command;
import com.ke.netty.data.packet.Packet;
import lombok.Data;

/**
 * @author caoyixiong
 * @Date: 2018/11/22
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
