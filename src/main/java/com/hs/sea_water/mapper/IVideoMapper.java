package com.hs.sea_water.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	
	@Select("select * from video")
	public List<Video> getVideoAll();
	
//	@Select("select * from video where id = ?")
//	public Video getVideoById(int id);
	
	@Update("update video set v_path =#{v_path},v_save_time =  now() where id=#{id}")
	public int updateVideo(@Param("id") int id,@Param("v_path") String v_path);
	
	@Update("update video set v_name =#{v_name},v_title =#{v_title} where id=#{id}")
	public int updateVideoNT(@Param("id") int id,@Param("v_title") String v_title,@Param("v_name") String v_name);
	
	@Insert("insert into video(v_path,v_save_time) values(#{v_path},now())")
	public int insertVideo(@Param("v_path") String v_path);
	
	@Delete("DELETE FROM info WHERE id=#{videoId}")
	public int delById(@Param("videoId") Integer videoId);
}
