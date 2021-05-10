package com.hs.sea_water.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hs.sea_water.entity.Info;
import com.hs.sea_water.entity.Video;
import com.hs.sea_water.mapper.IInfoMapper;
import com.hs.sea_water.mapper.IVideoMapper;
import com.hs.sea_water.service.IInfoService;
import com.hs.sea_water.service.IVideoService;
import com.hs.sea_water.serviceI.mpl.InfoServiceImpl;
import com.hs.sea_water.serviceI.mpl.VideoServiceImpl;

@CrossOrigin //支持跨域问题
@Controller  // 表示默认视图路径
public class webController implements ErrorController{
	
	
	private static final String ERROR_PATH = "/error";
	
	@Autowired IInfoMapper m_infoMapper;
	@Autowired IVideoMapper m_videoMapper;
	@Autowired VideoServiceImpl vs;
	@Autowired InfoServiceImpl is;
	
	
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
		 * 数据分析
		 * */
//		List<Video> videoList = m_videoMapper.getVideoAll();
//		List<Info> infoList = m_infoMapper.getVideoAll();
		
		if(pageIndex==null||pageIndex<1) pageIndex=1;
		Page<Info> p = new Page<>(pageIndex,18);
		long totalPages = 0;//总页数
		try {
			IPage<Info> rs = is.page(p,Wrappers.lambdaQuery(Info.class).orderByDesc(Info::getId));
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
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return ERROR_PATH;
	}
	
}
