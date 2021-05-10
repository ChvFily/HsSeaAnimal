package com.hs.sea_water.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hs.sea_water.entity.Video;

/**
 * @author chvfily
 * @since 2021-05-10
 * 接口  解耦合
 * */

public interface IVideoService extends IService<Video> {
	
	/**
	 * 根据ID获取Video
	 * @param id Int
	 * @return List < Video >
	 * */
	public List<Video> getVideoAll();
	/**
	 * 根据ID获取Video
	 * @param id Int
	 * @return Video
	 * */
	public Video getVideoById(int id);
}
