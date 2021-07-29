package com.hs.sea_water.controller;


import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bytedeco.javacpp.RealSense.intrinsics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hs.sea_water.entity.FileServer;
import com.hs.sea_water.entity.ImageEntity;
import com.hs.sea_water.entity.Info;
import com.hs.sea_water.entity.Video;
import com.hs.sea_water.service.IImageService;
import com.hs.sea_water.service.IInfoService;
import com.hs.sea_water.service.IVideoService;
import com.hs.sea_water.service.Impl.FileServerServiceImpl;
import com.hs.sea_water.srs.OnDvr;
import com.hs.sea_water.util.Helper;
import com.hs.sea_water.util.VideoUtil;

//srs回调接口
@CrossOrigin
@RestController
@RequestMapping("/srs")  // 向我们发起服务器发起请求 
public class SrsController { 
	@Autowired IInfoService is;
	@Autowired IVideoService vs;  
	@Autowired IImageService iis;
	@Autowired FileServerServiceImpl fss;
	String puPath = "http://210.37.8.148:8888/sea"; //公共目录
	
	
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
		ImageEntity imageE =new ImageEntity();
		String videoTitle = Helper.getFileNameFromUrl(dvr.file); //返回视频名称+时间搓 shuixia.20210316123652703.mp4
		v.setvPath(puPath+"/live/"+videoTitle);
		v.setvName(dvr.stream);  //视频名称
		v.setvTitle(dvr.stream);
		v.setvLiveStartTime(null);
		v.setVSaveTime(new Date());
		
		//生成url
		String serverIp = Helper.getIpAddress(request);
		String url = getUrl(serverIp, dvr.file);
		String iIconPath = "";
		
		String title = Helper.getNamtAndTime(v.getvPath()); // 视频 chvfily.20212131545651
		iIconPath = puPath+"/videoImg/"+title+".jpg"; //预览图片名称
		info.setiTitle(v.getvTitle());      
		info.setiCode("3333");  // 3333 代表live直播
		info.setiCodeTitle("live");
		info.setiName(v.getvName());
		info.setiSrcType("0");
		info.setiIconPath(iIconPath);
		
		if(url==null) {
			System.out.println("解析视频下载地址失败！"+dvr.file); 
		}else {
			System.out.println("解析视频下载地址成功！"+url); 
			if(vs.save(v)){
				// 保存图片
				imageE.setiTitle(info.getiTitle());
				imageE.setiImg1(info.getiIconPath());
				imageE.setiImg2(info.getiIconPath());
				if(iis.save(imageE)) {
					// 资源添加到对应的info表映射
					int video_id;
					int image_id;
					video_id = vs.getOne(Wrappers.lambdaQuery(Video.class)
							     .eq(Video::getvPath, v.getvPath())).getId(); 
					image_id = iis.getOne(Wrappers.lambdaQuery(ImageEntity.class)
						     .eq(ImageEntity::getiImg1, imageE.getiImg1())).getId();
					info.setvId(video_id);
					info.setiId(image_id);	
					is.save(info);
					
					String srcPath = "//mnt//file//sea//"; //公共资源路径
					String str = v.getvPath();  // /home/xwcbxy/video/hs_an_videos/
    				String str1=str.substring(0, str.indexOf("/sea"));
    			    String videoFileName = "/mnt/file/"+str.substring(str1.length()+1, str.length());
    				String vTitle = Helper.getNamtAndTime(videoFileName);
    	            String outputPath = srcPath+"videoImg//"+vTitle+".jpg"; // /home/xwcbxy/video/videoImg/test.mp4
    	            makeFrame(videoFileName, outputPath);
				}
			}
		}	
		return "0";
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
	
	
	/**
	 * 生成图片截图
	 * @param inFile  视频路径 
	 * @param outFile 截图存放路径 
	 * @author chvfily
	 * @since 2021-06-06
	 * */
	private void makeFrame(String inFile,String outFile) {
		VideoUtil videoUtil = new VideoUtil();
		
		int index = 1;
        File file = new File(outFile);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
			System.out.println(videoUtil.getVideoImg(inFile,index,outFile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
