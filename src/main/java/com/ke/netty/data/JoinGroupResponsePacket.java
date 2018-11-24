package com.ke.netty.data;

import com.ke.netty.data.packet.Packet;
import lombok.Data;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@Data
public class JoinGroupResponsePacket extends Packet {
    private boolean success;
    private String groupId;
    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
