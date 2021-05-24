package com.hs.sea_water.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hs.sea_water.entity.FileServer;
import com.hs.sea_water.entity.Info;
import com.hs.sea_water.entity.Video;
import com.hs.sea_water.service.IInfoService;
import com.hs.sea_water.service.IVideoService;
import com.hs.sea_water.serviceI.mpl.FileServerServiceImpl;
import com.hs.sea_water.srs.OnDvr;
import com.hs.sea_water.util.Helper;

//srs回调接口
@CrossOrigin
@RestController
@RequestMapping("/srs")  // 向我们发起服务器发起请求 
public class SrsController { 
	@Autowired IInfoService is;
	@Autowired IVideoService vs;  
	@Autowired FileServerServiceImpl fss;
	
	
	@RequestMapping("/getTest") // 主页              // 返回的是指定的数据格式
	public String getting() {
		int idString = vs.getOne(Wrappers.lambdaQuery(Video.class)
				  .eq(Video::getvPath, "/hs_an_videos/chvfily_test.20210523203753098.mp4")).getId();
		return "hello!";
	}
	 
	/**
	 * <p>
	 * 	视频录制接口
	 * 	当视频录制完成后，触发该方法
	 * 	保存视频文件的名称，时间、地点、路径
	 *  保存两个表的数据
	 * </p>
	 * @author chvfily
	 * @since 2021-05-21
	 * */
	@RequestMapping("/dvr")  
	public String on_dvrCallback(HttpServletRequest request,@RequestBody OnDvr dvr) {  // HttpServletRequest request,
		System.out.println("hello!");
		System.out.println(JSON.toJSONString(dvr));
		Video v = new Video();
		Info info = new Info();
		String puPath = "hs_an_videos/live/"; 
		String videoTitle = Helper.getFileNameFromUrl(dvr.file); //返回视频名称+时间搓 shuixia.20210316123652703.mp4
		v.setvPath(puPath+videoTitle);
		v.setvName(dvr.stream);  //视频名称
		v.setvTitle(dvr.stream);
		v.setvLiveStartTime(null);
		v.setVSaveTime(new Date());
		
		//生成url
		String serverIp = Helper.getIpAddress(request);
		String url = getUrl(serverIp, dvr.file);
		if(url==null) {
			System.out.println("解析视频下载地址失败！"+dvr.file); 
		}else {
			System.out.println("解析视频下载地址成功！"+url); 
			if(vs.save(v)){
				// 资源添加到对应的info表映射
				int video_id;
				String iIconPath = "";
				video_id = vs.getOne(Wrappers.lambdaQuery(Video.class)
						     .eq(Video::getvPath, v.getvPath())).getId(); 
				String title = Helper.getNamtAndTime(v.getvPath()); // 视频 chvfily.20212131545651
				iIconPath = "videoImg/"+title+".jpg"; //预览图片名称
				info.setiTitle(v.getvTitle());      
				info.setiCode("3333");  // 3333 代表live直播
				info.setiCodeTitle("live");
				info.setiName(v.getvName());
				info.setiSrcType("0");
				info.setiId(video_id);
				info.setvId(0);	
				info.setiIconPath(iIconPath);
				is.save(info);
			}
		}	
		return "0";
	}
	@RequestMapping("/on_dvr")
	public String on_dvrCB() {
		
		return "on_dvr";
	}

	// 视频url 地址
	private String getUrl(String ip,String file) {
		List<FileServer> fsList =  fss.list();
		for(FileServer fs : fsList) {
			if(fs.getServerIp().equals(ip)) {
				String docDir = fs.getServerDocDir();
				int idx = file.indexOf(docDir);
				if(idx>=0){
					String path = file.substring(docDir.length()); 
					String port =80==fs.getServerPort()?"":":"+fs.getServerPort();
					return fs.getServerSchema()+"://"+fs.getServerIp()+port+path; //自行拼接起来视频地址
				}
			}
		}
		return null;
	}
}
