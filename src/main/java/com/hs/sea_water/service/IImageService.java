package com.hs.sea_water.service;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hs.sea_water.entity.ImageEntity;


public interface IImageService extends IService<ImageEntity> {
	
	/**
	 * 获取全部信息
	 * @return List < ImageEntity > 
	 * */
	List<ImageEntity> getImageAll();
	/**
	 * @param id Int
	 * @return ImageEntity
	 * */
	ImageEntity getImageById(int id);
	
//	 int updateImg1( int id,String img1);
//	
//	 int updateImg2( int id,String img2);
//	
//	 int insertImageImg1(String img1);
//	
//	 int insertImageImg2(String img2);
//	public int updateImg1(@Param("id") int id,@Param("i_img1") String img1);
//	
//	public int updateImg2(@Param("id") int id,@Param("i_img2") String img2);
//	
//	public int insertImageImg1(@Param("img1") String img1);
//	
//	public int insertImageImg2(@Param("img2") String img2);
	

}
