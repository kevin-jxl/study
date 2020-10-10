package com.example.demo.qrcode;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.User;

public class QrCodeTest {
 
	public static void main(String[] args) throws Exception {
		User user = new User();
		user.setAge("11");
		user.setName("测试");
		user.setSex("0");

		String s = JSON.toJSONString(user);
		// 存放在二维码中的内容
		String text = s;
		// 嵌入二维码的图片路径
//		String imgPath = "D:\\workspace\\text\\img\\logo.png";
		String imgPath = "";
		// 生成的二维码的路径及名称
		String destPath = "D:\\workspace\\text\\img\\qrcode\\logo.jpg";
		//生成二维码
		/*
		* text：编码到二维码中的内容，这里是“https://www.baidu.com/”
		imgPath：要嵌入二维码的图片路径，如果不写或者为null则生成一个没有嵌入图片的纯净的二维码
		destPath：生成的二维码的存放路径
		true：表示将嵌入二维码的图片进行压缩，如果为“false”则表示不压缩。*/
		QRCodeUtil.encode(text, imgPath, destPath, true);
		// 解析二维码
		// destPath：将要解析的二维码的存放路径
		String str = QRCodeUtil.decode(destPath);
		// 打印出解析出的内容
		System.out.println(str);
 
	}
 
}