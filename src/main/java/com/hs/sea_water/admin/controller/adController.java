package com.hs.sea_water.admin.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.logging.Log;
import org.bytedeco.javacpp.RealSense.intrinsics;
import org.bytedeco.javacpp.tesseract.UNICHAR.const_iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hs.sea_water.admin.AddData;
import com.hs.sea_water.admin.util.KitFileUtil;
import com.hs.sea_water.entity.ImageEntity;
import com.hs.sea_water.entity.Info;
import com.hs.sea_water.entity.SrcCode;
import com.hs.sea_water.entity.Video;
import com.hs.sea_water.mapper.IImageMapper;
import com.hs.sea_water.mapper.IInfoMapper;
import com.hs.sea_water.mapper.ISrcCodeMapper;
import com.hs.sea_water.mapper.IVideoMapper;
import com.hs.sea_water.service.IImageService;
import com.hs.sea_water.service.IInfoService;
import com.hs.sea_water.service.ISrcCodeService;
import com.hs.sea_water.service.IVideoService;


@CrossOrigin //支持跨域问题
@Controller  // 表示默认视图路径
public class adController implements ErrorController{
	
	
	@Autowired IVideoService vs;
	@Autowired IInfoService is;
	@Autowired IImageService iis;// imageServer
	@Autowired ISrcCodeService ss;
	@Autowired IImageMapper iImageMapper;
	@Autowired IVideoMapper iVideoMapper;
	@Autowired ISrcCodeMapper iSrcCodeMapper;
	@Autowired IInfoMapper iInfoMapper;
	private static final Logger logger = LoggerFactory.getLogger(adController.class.getName());
	
	

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping("/admin/")
	public String getAdmin(Model model) {
		
		model.addAttribute("title","后台管理");
		return "admin/admin";
	}
	
	@RequestMapping("/admin/test")
	@ResponseBody
	public String getAdTest(Model model) {
		
		String codeTitleString = ss.getOne(Wrappers.lambdaQuery(SrcCode.class)  // 查找对应的id
			     .eq(SrcCode::getSrcCode, "1111")).getSrcTitle();
		return codeTitleString;
	}
	
	@RequestMapping("/admin/getContent")
	public String getContent(Model model) {
		
		model.addAttribute("title","内容管理");
		return "admin/content";
	}
	
	/** 加载数据*/
	@RequestMapping("/admin/contentData.json")
	@ResponseBody  // 发送数据能力 
	public List<Info> getDataContent(Model model) {
		List<Info> data = is.getInfoAll();
	    return data;
	}
	
	@RequestMapping("/admin/getType")
	public String getType(Model model) {
		
		model.addAttribute("title","分类管理");
		return "admin/type";
	}
	@RequestMapping("/admin/getLunbo")
	public String getLunbo(Model model) {
		
		model.addAttribute("title","轮播管理");
		return "admin/lunbo";
	}
	@RequestMapping("/admin/getAbout")
	public String getAbout(Model model) {
		
		model.addAttribute("title","联系我们管理");
		return "admin/about";
	}
	
	/**删除单个数据*/
	@RequestMapping("/admin/delOne")
	@ResponseBody
	public Map<String, Object> delOne(String infoId) {
		Map<String, Object> map = new HashMap<>();
		int info_id = Integer.valueOf(infoId).intValue();
		Info info = is.getInfoById(info_id);
		int imageId = info.getiId();
		int videoId = info.getvId();
		
		//删除对应的文件
		String img1 = iis.getImageById(imageId).getiImg1();
		String img2 = iis.getImageById(imageId).getiImg2();
		String videoPath = vs.getVideoById(videoId).getvPath();
		// 解析正确的路径
		
		try {
			iImageMapper.delById(imageId);
			iVideoMapper.delById(videoId);
			iInfoMapper.delById(info_id);
			map.put("msg", "1");
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "0");
		}
		return map;
	}
	
	/**删除选中所有数据*/
	@RequestMapping("/admin/delSelects")
	@ResponseBody   
	public Map<String, Object> delSelects(HttpServletRequest request) {
		/**
		 * 返回了json格式数据
		 * */
		Map<String, Object> map = new HashMap<>();
		
		String listString;
		String reString = request.getParameter("id");
//		Object object = request.getParameter("data");
//		for(Info info:infoList) {
//			int i = 0;
//			map.put("id"+i, info.getId());
//			i++;
//		} 
		return map;
	}
	
	/**
	 * 转跳到 编辑页面
	 * */
	@RequestMapping("/admin/contentEdit")
	public String exitContent(Model model, HttpServletRequest request) {
		int infoId = Integer.valueOf(request.getParameter("info_id")).intValue(); //转化为int 类型
		Info infoEntity = is.getInfoById(infoId);
		ImageEntity imageEntity = iis.getImageById(infoEntity.getiId());
		Video videoEntity = vs.getVideoById(infoEntity.getvId());
		model.addAttribute("infoE",infoEntity);
		model.addAttribute("imageE",imageEntity);
		model.addAttribute("videoE",videoEntity);
		infoEntity.getId();
		return "admin/contentEdit";
	}
	
	/** 更新数据*/
	@RequestMapping("/admin/contentUpData")
	@ResponseBody
	public Map<String, Object> upDataContent(HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<>();
		Info infoEntity =new Info();
		ImageEntity imageEntity = new ImageEntity();
		Video videoEntity = new Video();
		SrcCode srcCode = new SrcCode();
		int v_id = Integer.valueOf(request.getParameter("videoId")).intValue();
		int i_id = Integer.valueOf(request.getParameter("imgId")).intValue();
		imageEntity = iis.getImageById(i_id);
		videoEntity = vs.getVideoById(v_id);
		infoEntity.setCla(request.getParameter("cla"));
		infoEntity.setDistribution(request.getParameter("distribution"));
		infoEntity.setEcologicalHabit(request.getParameter("ecological_habit"));
		infoEntity.setFamily(request.getParameter("family"));
		infoEntity.setGenus(request.getParameter("genus"));
		infoEntity.setSpecies(request.getParameter("species"));
		infoEntity.setGrow(request.getParameter("grow"));
		infoEntity.setiCode(request.getParameter("i_code"));
		String codeTitleString = ss.getOne(Wrappers.lambdaQuery(SrcCode.class) // 查找对应的id
			     .eq(SrcCode::getSrcCode, request.getParameter("i_code"))).getSrcTitle();
		infoEntity.setiCodeTitle(codeTitleString); //分类的名称 需要建立一个表
		infoEntity.setiIconPath(imageEntity.getiImg1()); //需要插入图片地址
		infoEntity.setiIntroduce(request.getParameter("i_introduce"));
		infoEntity.setiName(request.getParameter("i_name"));
		SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH-mm-ss" );
		String dataString = sdf.format(new Date());
		infoEntity.setiSaveTime(dataString);
		infoEntity.setiSrcType("0");
		infoEntity.setiTitle(request.getParameter("i_title"));
		infoEntity.setKingdom(request.getParameter("kingdom"));
		infoEntity.setOrd(request.getParameter("ord"));
		infoEntity.setPhylum(request.getParameter("phylum"));
		infoEntity.setShapeFeatures(request.getParameter("shape_features"));
		infoEntity.setSurvivalStatus(request.getParameter("survival_status"));
		infoEntity.setvId(Integer.valueOf(request.getParameter("videoId")).intValue()); //插入视频的id index 
		infoEntity.setiId(Integer.valueOf(request.getParameter("imgId")).intValue()); //插入图片的id index
		int infoId = Integer.valueOf(request.getParameter("infoId")).intValue();
		if(is.update(infoEntity, Wrappers.lambdaQuery(Info.class) // 查找对应的id
			     .eq(Info::getId, infoId))) {  // infoId
			map.put("s", "1");
		} else {
			map.put("s", "0");
		}
		return map;
	}
	
	/** 添加数据*/
	@RequestMapping("/admin/contentAdd")
	public String addData() {
		return "admin/addData";
	}
	
	/** 保存数据*/
	@RequestMapping("/admin/contentSave")
	@ResponseBody  // 发送数据能力
	public Map<String, Object> saveData(Model model,HttpServletRequest request) {
		
		
		Map<String, Object> map = new HashMap<>();
		
		Info infoEntity =new Info();
		ImageEntity imageEntity = new ImageEntity();
		Video videoEntity = new Video();
		SrcCode srcCodeEntity = new SrcCode();
		 
		int v_id = Integer.valueOf(request.getParameter("videoId")).intValue();
		int i_id = Integer.valueOf(request.getParameter("imgId")).intValue();
		imageEntity = iis.getImageById(i_id);
		videoEntity = vs.getVideoById(v_id);
		/**
		 * 插入传递过来的数据
		 * info 更新数据表
		 * video 更新 v_name v_title
		 * image 数据表 title
		 * 
		 * {"i_title":"","i_name":"","i_code":"","imgId":"","image":"","videoId":"108",
		 * "classVideo":"http://210.37.8.148:8888/sea/video/jellyfish.mp4",
		 * "layuiVideo":"","i_introduce":"","kingdom":"","phylum":"","cla":"","ord":"",
		 * "family":"","genus":"","species":"","distribution":"","ecological_habit":"","shape_features":"",
		 * "grow":"","survival_status":""}
		 * */
		
		infoEntity.setCla(request.getParameter("cla"));
		infoEntity.setDistribution(request.getParameter("distribution"));
		infoEntity.setEcologicalHabit(request.getParameter("ecological_habit"));
		infoEntity.setFamily(request.getParameter("family"));
		infoEntity.setGenus(request.getParameter("genus"));
		infoEntity.setSpecies(request.getParameter("species"));
		infoEntity.setGrow(request.getParameter("grow"));
		infoEntity.setiCode(request.getParameter("i_code"));
		String codeTitleString = ss.getOne(Wrappers.lambdaQuery(SrcCode.class)  // 查找对应的id
			     .eq(SrcCode::getSrcCode, request.getParameter("i_code"))).getSrcTitle();
		infoEntity.setiCodeTitle(codeTitleString); //分类的名称 需要建立一个表
		infoEntity.setiIconPath(imageEntity.getiImg1()); //需要插入图片地址
		infoEntity.setiIntroduce(request.getParameter("i_introduce"));
		infoEntity.setiName(request.getParameter("i_name"));
		SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH-mm-ss" );
		String dataString = sdf.format(new Date());
		infoEntity.setiSaveTime(dataString);
		infoEntity.setiSrcType("0");
		infoEntity.setiTitle(request.getParameter("i_title"));
		infoEntity.setKingdom(request.getParameter("kingdom"));
		infoEntity.setOrd(request.getParameter("ord"));
		infoEntity.setPhylum(request.getParameter("phylum"));
		infoEntity.setShapeFeatures(request.getParameter("shape_features"));
		infoEntity.setSurvivalStatus(request.getParameter("survival_status"));
		infoEntity.setvId(Integer.valueOf(request.getParameter("videoId")).intValue()); //插入视频的id index 
		infoEntity.setiId(Integer.valueOf(request.getParameter("imgId")).intValue()); //插入图片的id index
		
		is.save(infoEntity); //保存图片
		
		//保存视频的名称 name 和title
		String NameString = request.getParameter("i_name");
		String titleString = request.getParameter("i_title");
		
		// 更新
		
		iImageMapper.updateImgTitle(i_id, titleString);
		iVideoMapper.updateVideoNT(v_id, titleString, NameString);
		
		map.put("msg", "保存成功");
		
		
		return map;
	}
	
	/** 上传视频*/
	@RequestMapping(value = "/admin/uploadVideo",method = RequestMethod.POST)
    @ResponseBody
    public Object uploadVideo(@RequestParam("layuiVideo") MultipartFile[] layuiFile, @RequestParam("videoId") String videoId,HttpServletRequest request, HttpServletResponse response){
		
		logger.info("进入wph的layui视频上传接口》》》》》》》》》》》》》》》");
		String videoPublicPathString = "/mnt/file/sea/"; //部署的环境的路径
		String serverUrlString  = "http://210.37.8.148.8888/sea/";
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> map2=new HashMap<>();
        KitFileUtil kitFileUtil=new KitFileUtil();
        map = kitFileUtil.kitFileUtil(layuiFile, request, response); // 返回保存地址 ，保存的IP 或 域名 地址注意在 kitFileUtil 类里面做出修改
        // 视频路径 插入数据库  并返回id  
        String url[]=(String[])map.get("url");
        String videoUrlString = url[0];
        if(!videoId.isEmpty()) {
        	int toVIdeoId = Integer.valueOf(videoId).intValue(); //转化为数字的格式
        	Video video;
        	if((video = vs.getVideoById(toVIdeoId))!= null) {
        		int video_id = video.getId();
//        		String delPahtString = map.get("savePath").toString(); //  解析路径 在转化路径时，需要转换路径   //删除原来的路径
        		String oldVideoPaht = vs.getVideoById(video_id).getvPath(); // 获取原来的视频路径
        		//解析为本地路径
        	    String pathsubString = 	oldVideoPaht.substring(oldVideoPaht.lastIndexOf(serverUrlString)+1); //解析后 的字符串
        		String delPahtString = videoPublicPathString+pathsubString;
        		File file1 = new File(delPahtString);
				if(file1.exists()) {
					file1.delete();
					System.out.println("删除成功");
				}
        		iVideoMapper.updateVideo(video_id, videoUrlString);
        		map2.put("v_id", video_id);
        	}else {
            	iVideoMapper.insertVideo(videoUrlString);
            	int v_id   = vs.getOne(Wrappers.lambdaQuery(Video.class) // 查找对应的id
     						     .eq(Video::getvPath, videoUrlString)).getId();
            	map2.put("v_id", v_id);
            }
        }else {
        	iVideoMapper.insertVideo(videoUrlString);
        	int v_id   = vs.getOne(Wrappers.lambdaQuery(Video.class) // 查找对应的id
 						     .eq(Video::getvPath, videoUrlString)).getId();
        	map2.put("v_id", v_id);
        }
        
        String error = map.get("error").toString();
        if("101".equals(error)){
            map2.put("error",101);
            map2.put("message",map.get("message"));
            return map;
        }
        map2.put("error",0);
        
        map2.put("url",url[0]);
//        map2.put("url", "http://210.37.8.148:8888/sea/video/jellyfish.mp4");
        return JSONObject.toJSON(map2);
    }
	
	//跳转预览页面
    @RequestMapping(value = "/admin/goLook", method = RequestMethod.GET)
    public Object goLook(Model model, String vUrl){
    	
        model.addAttribute("vUrl",vUrl);
        return "admin/videopre";
    }
	
	/** 保存图片*/
	@RequestMapping("/admin/uploadImg")
	@ResponseBody  // 发送数据能力
	public Map<String, Integer> uploadImg(@RequestParam("image") MultipartFile file, @RequestParam("imgId") String imgId,@RequestParam("imgNum") String imgNum,HttpServletRequest request)throws Exception  {
		Map<String, Integer> map = new HashMap<>(3);
		ImageEntity imageE =new ImageEntity();
		String sava_path = "/mnt/file/sea/image/";  //保存本路径 
		String urlPathString = "http://210.37.8.148:8888/sea/image/";
		String path =null;// 文件路径
        double fileSize = file.getSize();
        System.out.println("文件的大小是" + fileSize);
        byte[] sizebyte=file.getBytes();
        System.out.println("文件的byte大小是" + sizebyte.toString());
        if (file !=null) {// 判断上传的文件是否为空
            String type =null;// 文件类型
            String fileName = file.getOriginalFilename(); //文件原名称
            System.out.println("上传的文件原名称:" + fileName);
            // 判断文件类型
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") +1, fileName.length()) :null;
            if (type !=null) {// 判断文件类型是否为空
 
                if ("GIF".equals(type.toUpperCase()) ||"PNG".equals(type.toUpperCase()) ||"JPG".equals(type.toUpperCase())) {
 
                    // 项目在容器中实际发布运行的根路径
                    String realPath = request.getSession().getServletContext().getRealPath("/");
                    // 自定义的文件名称
                    String trueFileName = String.valueOf(System.currentTimeMillis()) +"." + type;
                    // 设置存放图片文件的路径
                    // 添加时间搓
                    String datatimeStr  = " "+new Date().getTime();
                    String dataStr =  datatimeStr.substring(datatimeStr.length()/2); // 截取时间搓后半部分
                    path = sava_path+dataStr+fileName;
                    urlPathString = urlPathString+dataStr+fileName; // 数据库保存的路径
                    System.out.println("存放图片文件的路径:" + path);
 
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                    System.out.println("文件成功上传到指定目录下");
                    // 开始插入图片 并返回id  | 修改图片名称 并返回id
                    
                    if(!imgId.isEmpty()) {  //如果查询的数据为非空 则查询数据库的信息
                    	int toImgId = Integer.valueOf(imgId).intValue(); //转化为数字的格式
            			int toImgNum = Integer.valueOf(imgNum).intValue(); //转化为数字的格式
            			//做一下判断 查不到数据时
            			if((iis.getImageById(toImgId)) != null ) { //如果查到了数据，删除原来的文件 并修改文件路径 
            				imageE = iis.getImageById(toImgId);
            				int img_id = imageE.getId();
            				String delFilePath;
            				// 更新数据 ，修改名称 删除原来的文件
            				if(toImgNum == 1) {
            					// 删除原来的文件
            					delFilePath = imageE.getiImg1();
            					
            					iImageMapper.updateImg1(img_id, urlPathString); // 修改图片1 
            				}else {
            					delFilePath = imageE.getiImg2();
            					iImageMapper.updateImg2(img_id, urlPathString); // 修改图片2 
            				}
            				if(delFilePath!=null) {
            	        		//解析为本地路径
            	        	    String pathsubString = 	delFilePath.substring(delFilePath.lastIndexOf(urlPathString)+1); //解析后 的字符串
            	        		String delPathString = sava_path+pathsubString;
            					File file1 = new File(delPathString);
            					if(file1.exists()) {
            						file1.delete();
            						System.out.println("删除成功");
            					}
            				}
            				
            				map.put("img_id", img_id); //返回指定的id 更改数据内容 （文件名称）
            				return map;
            			}else {
//            				//插入数据数据
            				int imageId;
            				if(toImgNum == 1) {
            					iImageMapper.insertImageImg1(urlPathString); // 保存数据
            					imageId  = iis.getOne(Wrappers.lambdaQuery(ImageEntity.class) // 查找对应的id
              						     .eq(ImageEntity::getiImg1, urlPathString)).getId();
            				}else {
            					iImageMapper.insertImageImg2(urlPathString); // 保存数据
            					imageId  = iis.getOne(Wrappers.lambdaQuery(ImageEntity.class)
              						     .eq(ImageEntity::getiImg2, urlPathString)).getId();
            				}
            				map.put("img_id", imageId);  //如果返回的是空 新加数据和返回对应的id
            				return map;
            			}
            		}else {
        				//插入数据数据
            			int imageId;
            			int toImgNum = Integer.valueOf(imgNum).intValue(); //转化为数字的格式
        				if(toImgNum == 1) {
        					iImageMapper.insertImageImg1(urlPathString); // 保存数据
        					imageId  = iis.getOne(Wrappers.lambdaQuery(ImageEntity.class) // 查找对应的id
          						     .eq(ImageEntity::getiImg1, urlPathString)).getId();
        				}else {
        					iImageMapper.insertImageImg2(urlPathString); // 保存数据
        					imageId  = iis.getOne(Wrappers.lambdaQuery(ImageEntity.class)
          						     .eq(ImageEntity::getiImg2, urlPathString)).getId();
        				}
        				map.put("img_id", imageId);  //如果返回的是空 新加数据和返回对应的id
        				return map;
        			}
                }
 
            }
        }
        map.put("msg", 11);
        return map;
    }
	
}
