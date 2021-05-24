package com.hs.sea_water.srs;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * 推流回调参数 post
 * </p>
 * @author chvfily
 * @since 2021-05-20
 * */
public class OnPublish {
	public String ip;  
	public String id;
	public String app;
	public String name;
	public String url;    
	public boolean active;
	public int clients;
	//{"code":0,"server":49308,"stream":{"id":49310, "name":"dk","vhost":49309,"app":"live","live_ms":1611216178205,"clients":1,
	//"frames":83842,"send_bytes":47426233452939428, "recv_bytes":47426234264940626,"kbps":{"recv_30s":2499,"send_30s":0},"publish":{"active":true,"cid":538},"video":
	//{"codec":"H264","profile":"High","level":"3.1","width":1280,"height":720},"audio":{"codec":"AAC","sample_rate":44100,"channel":2,"profile":"LC"}}}
	/**
	 * <p>
	 * 	请求回调接口回应的数据 格式 
	 * </p>
	 * @author chvfily
	 * 
	 * */
	public static OnPublish from(JSONObject jo) {
		OnPublish s = new OnPublish();
		if(jo.containsKey("id")) s.id = jo.getString("id");
		if(jo.containsKey("app")) s.app = jo.getString("app");
		if(jo.containsKey("name")) s.name = jo.getString("name");
		if(jo.containsKey("clients")) s.clients = jo.getIntValue("clients");
		if(jo.containsKey("publish")) {
			JSONObject p = jo.getJSONObject("publish");
			s.active = p.getBooleanValue("active");
		}
		return s;
	}
	// 直播端口开发 
}
