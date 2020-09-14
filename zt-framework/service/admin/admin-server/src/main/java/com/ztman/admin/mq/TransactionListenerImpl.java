package com.ztman.admin.mq;

//import org.apache.rocketmq.client.producer.LocalTransactionState;
//import org.apache.rocketmq.client.producer.TransactionListener;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;


/**
 * Created by Lenovo on 2018/10/30.  分布式事务测试--暂关
 * 事务消息监听器--作用：
 */
//public class TransactionListenerImpl implements TransactionListener {
public class TransactionListenerImpl {
 /*   @Autowired
    private DemoOrderService demoOrderService;

    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    *//**
     * 处理本地事务
     * @param msg
     * @param arg
     * @return
     *//*
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.println("执行本地事务");
        //类型转换
        Boolean result= demoOrderService.updateById((DemoOrder)arg);
        if(result){
            System.out.println("本地事务执行成功，消息提交");
            return LocalTransactionState.COMMIT_MESSAGE;
        }else{
            System.out.println("本地事务执行失败，消息回滚");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
//        int value = transactionIndex.getAndIncrement();
//        int status = value % 3;
//        localTrans.put(msg.getTransactionId(), status);

    }

    *//**
     * 检查本地事务执行结果
     * @param msg
     * @return
     *//*
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("检查本地事务执行结果");
//        Integer status = localTrans.get(msg.getTransactionId());
//        if (null != status) {
//            switch (status) {
//                case 0:
//                    return LocalTransactionState.UNKNOW;
//                case 1:
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                case 2:
//                    return LocalTransactionState.ROLLBACK_MESSAGE;
//            }
//        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }*/
}