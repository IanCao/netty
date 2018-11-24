package com.ke.netty.data;

import com.ke.netty.data.packet.Packet;
import lombok.Data;


/**
 * @author caoyixiong
 * @Date: 2018/11/24
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;
    private String groupId;
    private String groupName;
    private String userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
