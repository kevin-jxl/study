package com.example.demo.trancactional;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 项目名称：testQRcode
 * 类 名 称：TrancactionTest
 * 类 描 述：TODO
 * 创建时间：2020/8/11 9:51
 * 创 建 人：纪星亮
 */
public class TrancactionTest {
    private TransactionTemplate template;

    public void test() {
        // 编程式事务操作
        template.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                // 数据库操作
                return null;
            }
        });

        // 业务


    }
}