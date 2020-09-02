package com.example.demo.aop;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 项目名称：testQRcode
 * 类 名 称：ElSetClass
 * 类 描 述：TODO
 * 创建时间：2020/8/13 17:22
 * 创 建 人：纪星亮
 */
public class ElSetClass {
    private static ExpressionParser parser = new SpelExpressionParser();

    /**
     *
     * @param key   需要替换的占位符
     * @param valus     占位符的名字数组
     * @param objs      运行中动态传入的方法参数的值
     * @return
     */
    public static String getkey(String key, String[] valus, Object[] objs) {
        // 第一步，将key解析成el表达式
        Expression exp = parser.parseExpression(key);

        // 第二步，初始化赋值上下文
        EvaluationContext context = new StandardEvaluationContext();
        if (objs.length <= 0) {
            return null;
        }

        for (int i = 0; i < objs.length; i ++) {
            context.setVariable(valus[i], objs[i]);
        }

        // 第三步，模拟el表达式运算进行赋值
        return exp.getValue(context, String.class);
    }

    public static void main(String[] args) {
        String s = "#id+' '+#name";

        String id = "id";
        String name = "name";

        String[] names = new String[] {id, name};

        Object[] persons = new Object[2];

        persons[0] = "1";
        persons[1] = "这是";

        System.out.println(getkey(s, names, persons));
    }
}