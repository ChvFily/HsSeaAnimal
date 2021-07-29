package com.hs.sea_water.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hs.sea_water.entity.Video;
import com.hs.sea_water.mapper.IVideoMapper;
import com.hs.sea_water.service.IVideoService;

/**
 * <p>
 * Video服务实体类
 * </p>
 * @author chvfily
 * @since 2021-05-10
 * 
 * */
@Service
public class VideoServiceImpl extends ServiceImpl<IVideoMapper,Video> implements IVideoService {
	
	 @Autowired IVideoMapper IVideoMapper;

	@Override
	public List<Video> getVideoAll() {
		// TODO Auto-generated method stub
		return IVideoMapper.getVideoAll();
	}

	@Override
	public Video getVideoById(int id) {
		// TODO Auto-generated method stub
		return IVideoMapper.selectById(id);
	}

}
