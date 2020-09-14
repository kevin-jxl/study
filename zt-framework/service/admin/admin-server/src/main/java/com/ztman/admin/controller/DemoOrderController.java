package com.ztman.admin.controller;

//import com.ztman.admin.common.mq.controller.ProducerController;
//import com.ztman.product.api.entity.DemoProduct;
//import com.ztman.product.api.entity.MessageProductDto;
import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.*;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
        import org.springframework.web.bind.annotation.*;


/**
 *
 *
 * @author ztman code generator  分布式事务测试控制器-暂关
 * @date 2018-10-24 10:02:36
 */
@RestController
@RequestMapping("/demoorder")
@Slf4j
/**
 * demo
 */
public class DemoOrderController {
    /*@Autowired
    private DemoOrderService demoOrderService;

    @Autowired
    private RemoteProductService remoteProductService;

    @Autowired
    private  RemoteUserService remoteUserService;
    @Autowired
    private DcClientService dcClientService;

    //消息队列依赖
    @Autowired
    private TransactionMQProducer transactionProducer;




    *//**
     *  列表
     * @param params
     * @return
     *//*
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        return  demoOrderService.selectPage(new Query<>(params), new EntityWrapper<>());
    }


    *//**
     * 信息
     * @param id
     * @return R
     *//*
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id){
        DemoOrder demoOrder = demoOrderService.selectById(id);
        return new R<>(demoOrder);
    }

    *//**
     * 保存
     * @param demoOrder
     * @return R
     *//*
    @PostMapping
    public R save(@RequestBody DemoOrder demoOrder){
        demoOrderService.insert(demoOrder);
        return new R<>(Boolean.TRUE);
    }

    *//**
     * 修改
     * @param demoOrder
     * @return R
     *//*
    @PutMapping
    public R update(@RequestBody DemoOrder demoOrder){
        final boolean[] dbresult = {false};
        //步骤一：发送预处理消息到消息队列
        TransactionListener transactionListener = new TransactionListenerImpl();
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("事务消息检查线程");
                log.info("事务消息检查");
                return thread;
            }
        });
        transactionProducer.setExecutorService(executorService);
        transactionProducer.setTransactionListener(new TransactionListener() {
            private Boolean localresult=false;
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {

                log.info("1.执行本地事务......."+message.getTransactionId());
                Boolean result= demoOrderService.updateById(demoOrder);
                if(result){
                    log.info("2.本地事务执行成功，确认预处理消息");
                    localresult=true;
                }else{
                    log.info("2.本地事务执行失败，回滚预处理消息");
                    localresult=true;
                }
                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                log.info("4.检查本地事务执行结果...");
                if(localresult){
                    dbresult[0] =true;
                    return LocalTransactionState.COMMIT_MESSAGE;
                }else{
                    dbresult[0] =false;
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
        });
        //封装消息
        MessageProductDto productDto=new MessageProductDto(demoOrder.getProductId().toString(),demoOrder.getProductNum().toString(),DateUtil.now());
        String ms = JSONObject.toJSONString(productDto);
        Message msg = new Message("user-topic","white",ms.getBytes());
        SendResult sendResult = null;
        try {
            sendResult = transactionProducer.sendMessageInTransaction(msg,demoOrder);
            log.info("3.调用结果"+sendResult);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        if(sendResult.getSendStatus()== SendStatus.SEND_OK  &&   dbresult[0] ==true){
            return new R<>(Boolean.TRUE);
        }else{
            return new R<>(Boolean.FALSE);
        }

    }




    *//**
     * 删除
     * @param id
     * @return R
     *//*
    @DeleteMapping("/{id}")
    public R delete(@PathVariable  Integer id){
        demoOrderService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }

    *//**
     * 分布式事务测试
     *//*
    @GetMapping("/doTranction")
    @ApiOperation("测试分布式服务带参数")
    public void doTranction(){
        R info=   remoteProductService.info(1);

        System.out.println(info.getData().toString());
    }

    *//**
     * 分布式事务测试
     *//*
    @GetMapping("/doTranctionNoPara")
    @ApiOperation("测试分布式服务不带参")
    public void doTranction2(){
        R info=   remoteProductService.info();

        System.out.println(info.getData().toString());
    }
    *//**
     * 分布式事务测试
     *//*
    @GetMapping("/doUserTranction")
    @ApiOperation("测试分布式服务--用户数据")
    public void doUserTranction(){
        R info=   remoteUserService.info("admin","hh");

        System.out.println(info.getData().toString());
    }

    *//**
     * 分布式事务测试
     *//*
    @GetMapping("/doDc")
    @ApiOperation("测试分布式服务--无权限")
    public void doDc(){
        String result= dcClientService.consumer();

        System.out.println(result);
    }*/
}
