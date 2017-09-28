package com.liusong.micro.gzhPay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtil {

	private static final char[] CODESEQUENCE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	public static String createRandomStrByTime(String format,int n){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		StringBuilder sb = new StringBuilder(dateFormat.format(new Date()));
		Random random = new Random();
		if(n > 0){
			for(int i = 0;i<n;i++){
				sb.append(CODESEQUENCE[random.nextInt(CODESEQUENCE.length)]);
			}
		}
		return sb.toString();
	}
	
}
