package com.hs.sea_water.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hs.sea_water.entity.Video;

/**
 * @author chvfily
 * @since 2021-05-10 
 * */

@Mapper
public interface IVideoMapper extends BaseMapper<Video>{
	/**
	 * BaseMapper 存在了对 video 的增删查改方法
	 * */
	
	@Select("select * from src_video")
	public List<Video> getVideoAll();
	
//	@Select("select * from src_video where id = ?")
//	public Video getVideoById(int id);
}
