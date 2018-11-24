package com.ke.netty.data;

import com.ke.netty.data.packet.Packet;
import lombok.Data;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@Data
public class DataPacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return DATA;
    }
}
