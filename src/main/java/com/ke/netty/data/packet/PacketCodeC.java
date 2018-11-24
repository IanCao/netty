package com.ke.netty.data.packet;

import com.ke.netty.data.*;
import com.ke.netty.data.serializer.Serializer;
import io.netty.buffer.ByteBuf;

/**
 * @author caoyixiong
 * @Date: 2018/11/23
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class PacketCodeC {
    private static final int MAGIC_NUMBER = 0X12345678;

    public static PacketCodeC INSTANCE = new PacketCodeC();

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 创建ByteBuf 对象
        // ioBuffer() 方法会返回适配 io 读写相关的内存，
        // 它会尽可能创建一个直接内存，直接内存可以理解为不受 jvm 堆管理的内存空间，
        // 写到 IO 缓冲区的效果更高。
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        // 序列化Java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过magic number
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        //序列化算法标识
        byte serializerAlgorithm = byteBuf.readByte();

        // 获取指令
        byte command = byteBuf.readByte();

        //数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Class getRequestType(byte command) {
        if (command == Command.LOGIN_REQUEST) {
            return LoginRequestPacket.class;
        }
        if (command == Command.LOGIN_RESPONSE) {
            return LoginResponsePacket.class;
        }
        if (command == Command.MESSAGE_REQUEST) {
            return MessageRequestPacket.class;
        }
        if (command == Command.MESSAGE_RESPONSE) {
            return MessageResponsePacket.class;
        }
        if (command == Command.DATA) {
            return DataPacket.class;
        }
        if (command == Command.CREATE_GROUP_REQUEST) {
            return CreateGroupRequestPacket.class;
        }
        if (command == Command.CREATE_GROUP_RESPONSE) {
            return CreateGroupResponsePacket.class;
        }
        if (command == Command.JOIN_GROUP_REQUEST) {
            return JoinGroupRequestPacket.class;
        }
        if (command == Command.JOIN_GROUP_RESPONSE) {
            return JoinGroupResponsePacket.class;
        }
        if (command == Command.QUIT_GROUP_REQUEST) {
            return QuitGroupRequestPacket.class;
        }
        if (command == Command.QUIT_GROUP_RESPONSE) {
            return QuitGroupResponsePacket.class;
        }
        if (command == Command.LIST_GROUP_MEMBERS_REQUEST) {
            return ListGroupMembersRequestPacket.class;
        }
        if (command == Command.LIST_GROUP_MEMBERS_RESPONSE) {
            return ListGroupMembersResponsePacket.class;
        }
        if (command == Command.SEND_TO_GROUP_REQUEST) {
            return SendToGroupRequestPacket.class;
        }
        if (command == Command.SEND_TO_GROUP_RESPONSE) {
            return SendToGroupResponsePacket.class;
        }
        return Packet.class;
    }

    private Serializer getSerializer(byte algorithm) {
        return Serializer.DEFAULT;
    }
}
