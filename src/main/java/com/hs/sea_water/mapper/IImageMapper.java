package com.hs.sea_water.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hs.sea_water.entity.ImageEntity;
/**
 * @author chvfily
 * @since 2021-06-03
 *  主要对数据表操作
 * */
@Mapper
public interface IImageMapper extends BaseMapper<ImageEntity>{
	
		/**
		 * 自定义查询功能
		 * */
		@Select("select * from image")
		public List<ImageEntity> getImageAll();
		
		@Update("update image set i_img1 =#{img1} where id=#{id}")
		public int updateImg1(@Param("id") int id,@Param("img1") String img1);
		
		@Update("update image set i_img2 =#{img2} where id=#{id}")
		public int updateImg2(@Param("id") int id,@Param("img2") String img2);
		
		@Update("update image set i_title =#{i_title} where id=#{id}")
		public int updateImgTitle(@Param("id") int id,@Param("i_title") String i_title);
		
		@Insert("insert into image(i_img1) values(#{img1})")
		public int insertImageImg1(@Param("img1") String img1);
		
		@Insert("insert into image(i_img2) values(#{img2})")
		public int insertImageImg2(@Param("img2") String img2);
		
		@Delete("DELETE FROM image WHERE id=#{imgId}")
		public int delById(@Param("imgId") Integer imgId);
		
		

}
