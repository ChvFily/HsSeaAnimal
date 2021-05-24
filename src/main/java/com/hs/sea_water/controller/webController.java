package com.hs.sea_water.controller;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hs.sea_water.entity.FileServer;
import com.hs.sea_water.entity.Info;
import com.hs.sea_water.entity.Video;
import com.hs.sea_water.mapper.IInfoMapper;
import com.hs.sea_water.mapper.IVideoMapper;
import com.hs.sea_water.service.IInfoService;
import com.hs.sea_water.service.IVideoService;
import com.hs.sea_water.serviceI.mpl.FileServerServiceImpl;
import com.hs.sea_water.srs.OnPublish;
import com.hs.sea_water.util.VideoUtil;

@CrossOrigin //支持跨域问题
@Controller  // 表示默认视图路径
public class webController implements ErrorController{
	
	
	private static final String ERROR_PATH = "/error";
	
	@Autowired IInfoMapper m_infoMapper;
	@Autowired IVideoMapper m_videoMapper;
	@Autowired IVideoService vs;
	@Autowired IInfoService is;
	@Autowired FileServerServiceImpl fss; // 实现类
	@Autowired RestTemplate rt;
	
	/**
	 * 获取所有服务器的播放数据流 
	 * */
	private List<OnPublish>  getActiveStreams() {
		List<OnPublish> ret = new ArrayList<>();
		List<FileServer> fsList = fss.list();  // 获取服务器列表 （多个IP）
		for(FileServer fs:fsList) {
			String api = "http://"+fs.getServerIp()+":"+fs.getSrsApiPort(); //http://210.37.1.20:1985/api/v1/streams/
			String url = api+"/api/v1/streams/";   // http://ip:1985/api/v1/streams/ 推流接口 推流情况
			String rs =  rt.getForObject(url, String.class); // 获取所有的视频流数据   
			JSONObject jo = JSONObject.parseObject(rs);  //  划分多个视频流数据 推流个数 stream 
			JSONArray arr = jo.getJSONArray("streams");  // 获取视频流的组
			//获取推流
			for(int i=0;i<arr.size();i++) {
				JSONObject s = arr.getJSONObject(i);
				OnPublish ls = OnPublish.from(s);  // 获取只需要的 数据 段 
				ls.ip = fs.getServerIp();
				if(ls.active) ret.add(ls);
			}
		}
		// String url = liveProperties.getSrsApiServer()+"/api/v1/streams/";
		return ret;
	}
	
	/**
	 * 测试返回主页
	 * */
	@RequestMapping("/getTest") // 主页
	@ResponseBody        // 返回的是指定的数据格式
	public String getting() {
		List<Info> infoList = is.getInfoAll();
		List<Video> videoList = vs.getVideoAll();
		return " hello!";
	}
	/**
	 * 获取视频流
	 * 更新视频的图片
	 * */
	@RequestMapping("/streams.do")
	public String streams(Model model) {
		//获取视频流列表
		List<OnPublish> liveList = getActiveStreams();  //返回 
		String url = "";
		// String dir = "D:\\liveTempImg/";  //本地图片
		String dir = "//home//xwcbxy//video//liveImg//"; 
		//获取数据流地址 rtmp
		//拼凑地址
		for(OnPublish live:liveList) {
			// 获取数据流地址
			url="rtmp://"+live.ip+":1935/"+live.app+"/"+live.name;
			// 生成对应的图片
			VideoUtil.getFirtPicByStream(url, dir+live.name+".jpg");
		}
		// String url = "rtmp://"+ip+":1935/"+app+"/"+name;  
		model.addAttribute("streams", liveList);   // 获取 stream 字段的 数据 
		return "fragment/liveFragment";
	}
	/**
	 * <p>
	 * 	返回一页数据
	 * </p>
	 * @author yuxuhang
	 * @since 2021-05-10
	 * @param pageIndex 当前页码
	 * */
	@RequestMapping("/") 
	public String home(Model model,Integer pageIndex) {
		/**
		 * 返回直播数据
		 * 返回视频数据
		 * 返回分类数据
		 * */
		
		if(pageIndex==null||pageIndex<1) pageIndex=1;
		Page<Info> p = new Page<>(pageIndex,18);
		long totalPages = 0;//总页数
		try {
			IPage<Info> rs = is.page(p,Wrappers.lambdaQuery(Info.class)
					.eq(Info::getiSrcType, "0") // 0.视频 1.图片
					.orderByDesc(Info::getId));
			totalPages = rs.getPages();
		}catch (Exception e) {
			e.printStackTrace();
			
		} 
		
		model.addAttribute("page",p); // 添加数据到 主页点的前端 所有内容
		model.addAttribute("totalPages",totalPages); //需要展示的页 内容
		return "index";
	}
	
	/**
	 * 根据 info.id 进入show-page.html
	 * @author chvfily
	 * @param id info.id
	 * */
	@RequestMapping("/showPage")
	public String showSrc(Model model,int id) {
		/**
		 * 根据id返回视频或图片数据
		 * */
		Video video =vs.getVideoById(id);
		Info info = is.getInfoById(id);
		model.addAttribute("video",video);
		model.addAttribute("info",info);
		return "show-page";
	}
	
	
	@RequestMapping("/liveDetails")   // url = /liveDetails?id=xxxx&ip=  通过 get 方式 获取 数据
    public String liveDetails(String ip,String id,Model model){
//		if(id==null || ip==null) return "liveDetails";
		FileServer fs =  fss.getById(ip);
		//String url = liveProperties.getSrsApiServer()+"/api/v1/streams/"+id;  回调窗口 
		String url =  "http://"+fs.getServerIp()+":"+fs.getSrsApiPort()+"/api/v1/streams/"+id; //返回一个直播根据id确定
		String rs =  rt.getForObject(url, String.class);
		JSONObject jo = JSONObject.parseObject(rs);
		OnPublish ls = OnPublish.from(jo.getJSONObject("stream"));
		if(!ls.active)	return "liveDetails";
		ls.url = "http://"+fs.getServerIp()+":"+fs.getSrsHttpPort()+"/"+ls.app+"/"+ls.name;
		model.addAttribute("live", ls);
        return "liveDetails";
    }
	/**
	 * @author yuxuhang
	 * @deprecated 获取一个直播的在线人数
	 * @param  id   根据id 信号数据 
	 * @return  number of clients
	 * */
	@RequestMapping("/clients") 
    public String liveClients( String id , Model model){
		/*
		 * 获取LiveStream 数据
		 * 循环遍历
		 * 返回对应id的 clients
		 *  
		 * */
		int liveClients = 0;
		List<OnPublish> LsList = new ArrayList<>(); // 存放 getActiveStreams() LiveStream
		LsList = getActiveStreams();
		for(OnPublish ls:LsList) {
			if(id.equals(ls.id)) {
				liveClients = ls.clients;
			}
		}
		model.addAttribute("clients", liveClients);
		return "fragment/liveClients";
    }
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return ERROR_PATH;
	}
	
}
