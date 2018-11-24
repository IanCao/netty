package com.ke.netty.data.serializer;


/**
 * @author caoyixiong
 * @Date: 2018/11/22
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public interface Serializer extends SerializerAlgorithm {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * 序列化算法 取具体的序列化算法标识
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成Java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
