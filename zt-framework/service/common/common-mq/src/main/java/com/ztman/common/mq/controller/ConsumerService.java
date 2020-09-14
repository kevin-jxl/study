package com.ztman.common.mq.controller;

import com.ztman.common.mq.content.MessageEvent;
import org.springframework.stereotype.Component;

/**
 * 监听消息进行消费
 */
@Component
public class ConsumerService {
    //@EventListener(condition = "#event.msgs[0].topic=='user-topic' && #event.msgs[0].tags=='white'")
    public void rocketmqMsgListener(MessageEvent event) {
//        try {
//            List<MessageExt> msgs = event.getMsgs();
//            for (MessageExt msg : msgs) {
//                System.err.println("消费消息:"+new String(msg.getBody())+"消息ID："+msg.getTransactionId());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}