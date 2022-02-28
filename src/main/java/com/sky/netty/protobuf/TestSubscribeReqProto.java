package com.sky.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;


/**
 * 测试proto java的功能
 */
public class TestSubscribeReqProto {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        // 将java对象编码为byte数组
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        // 将二进制byte数组解码为原始对象
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("sky");
        builder.setProductName("Netty权威指南");
        builder.setAddress("南京市江宁区");
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("编码前："+req.toString());

        // 编码
        byte[] bytes = encode(req);
        // 解码
        SubscribeReqProto.SubscribeReq reqDecoded = decode(bytes);

        System.out.println("解码后："+reqDecoded.toString());
        System.out.println(".equal:"+ req.equals(reqDecoded));

        System.out.println("测试："+reqDecoded.getAddress());
    }
}
