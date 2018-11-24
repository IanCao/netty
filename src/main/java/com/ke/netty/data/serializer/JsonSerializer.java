package com.ke.netty.data.serializer;

import com.alibaba.fastjson.JSON;

/**
 * @author caoyixiong
 * @Date: 2018/11/22
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
public class JsonSerializer implements Serializer, SerializerAlgorithm {
    @Override
    public byte getSerializerAlgorithm() {
        return JSON_ONE;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);

    }
}
