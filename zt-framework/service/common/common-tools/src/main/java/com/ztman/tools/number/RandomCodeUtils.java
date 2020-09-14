package com.ztman.tools.number;

import java.text.Collator;
import java.util.*;

/**
 * 项目名称：spark
 * 类 名 称：RandomCodeUtils
 * 类 描 述：随机编号生成类
 * 创建时间：2020/9/11 11:20
 * 创 建 人：纪星亮
 */
public class RandomCodeUtils {

    /**
     * 随机生成由数字、字母组成的N位验证码
     *
     * @return 返回一个字符串
     */
    public static String getCode(int n) {
        char arr[] = new char[n];
        int i = 0;
        while (i < n) {
            char ch = (char) (int) (Math.random() * 124);
            if (ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9') {
                arr[i++] = ch;
            }
        }
        //将数组转为字符串
        return new String(arr);
    }

    public static void main(String[] args) {
//            System.out.println(getCode(6));
        // 首字母排序
        Comparator<Object> com= Collator.getInstance(java.util.Locale.CHINA);
        String[] newArray={"a中山","汕头","广州","安庆","阳江","南京","武汉","北京","安阳","北方"};
        HashMap map = new HashMap<>();
        HashMap map1 = new HashMap<>();
        HashMap map2 = new HashMap<>();
        map1.put("id", "b汕头");
        map2.put("id", "a中山");
        map.put("id", "c广州");
        List<HashMap> list1 = new ArrayList<>();
        list1.add(map);
        list1.add(map1);
        list1.add(map2);
        List<String> list = Arrays.asList(newArray);
        Collections.sort(list1, new Comparator<HashMap>() {
            @Override
            public int compare(HashMap o1, HashMap o2) {

                return com.compare(o1.get("id"), o2.get("id"));
            }
        });
        for(HashMap i:list1){
            System.out.print(i.toString()+"  ");
        }
    }

}