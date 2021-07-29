package com.hs.sea_water.util;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hs.sea_water.entity.Video;
import com.hs.sea_water.service.IVideoService;


/**
 * 定时任务 获取video 的其中一帧
 * @author yuxuhang
 * @since 2021/05/03
 * 
 */
@Component
public class FirstFrameRefaceTaskUtil {
	
	@Autowired IVideoService videoService; //增删拆查改 接口
	boolean enableReface = true; //是否可以更新 
	/**
	 * 1.获取视频所有的路径+名称+时间戳
	 * 2.更新所有图片资源所有的图像资源
	 * */
    @Scheduled(cron = "0 */1 * * * ?")  //每分钟 且每天更新一次 
    public void execute() {
    	if(enableReface) {
    		enableReface  = false;
    		List<Video> videoList = videoService.getVideoAll(); // 获取前三个所有视频列表 0,1,2
    		String srcPath = "//mnt//file//sea//"; //公共资源路径
            // 生成对应的第一帧图像 
    		VideoUtil videoUtil = new VideoUtil();
    		
    		for(Video video:videoList){
    			if(videoList.size()!=0) {
    				String str = video.getvPath();  // /home/xwcbxy/video/hs_an_videos/
    				String str1=str.substring(0, str.indexOf("/sea"));
    			    String videoFileName = "/mnt/file/"+str.substring(str1.length()+1, str.length());
    				String title = Helper.getNamtAndTime(videoFileName);
    	            String outputPath = srcPath+"videoImg//"+title+".jpg"; // /home/xwcbxy/video/videoImg/test.mp4
    	            
    	            int index = 5;
    	            File file = new File(outputPath);
    	            if (!file.getParentFile().exists()){
    	                file.getParentFile().mkdirs();
    	            }
    	            try {
    	    			System.out.println(videoUtil.getVideoImg(videoFileName,index,outputPath));
    	    		} catch (Exception e) {
    	    			// TODO Auto-generated catch block
    	    			e.printStackTrace();
    	    		}
    			}
    		}// end for       
    	}   
    }

}

