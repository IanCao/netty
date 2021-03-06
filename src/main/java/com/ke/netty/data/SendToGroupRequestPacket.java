package com.ke.netty.data;

import com.ke.netty.data.packet.Packet;
import lombok.Data;

/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@Data
public class SendToGroupRequestPacket extends Packet{
    private String groupId;
    private String message;
    @Override
    public Byte getCommand() {
        return SEND_TO_GROUP_REQUEST;
    }
}
