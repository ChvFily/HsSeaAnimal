package com.hs.sea_water.util;

import javax.servlet.http.HttpServletRequest;

public class Helper {
	public static final String S_USERNAME="S_USERNAME";
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 返回视频名称
	 * */
	public static String getFileNameFromUrl(String url) {
//		int idx = url.lastIndexOf(".");
//		if(idx>0)return url.substring(url.lastIndexOf("/")+1,idx);
		return url.substring(url.lastIndexOf("/")+1);
	}
	public static String getNamtAndTime(String url) {
		int idx = url.lastIndexOf(".");
		return url.substring(url.lastIndexOf("/")+1,idx);
		
	}
}
