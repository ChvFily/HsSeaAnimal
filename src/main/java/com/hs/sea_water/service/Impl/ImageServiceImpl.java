package com.hs.sea_water.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hs.sea_water.entity.ImageEntity;
import com.hs.sea_water.mapper.IImageMapper;
import com.hs.sea_water.service.IImageService;

@Service
public class ImageServiceImpl extends ServiceImpl<IImageMapper,ImageEntity> implements IImageService{
	
	@Autowired IImageMapper iImageMapper;
	
	@Override
	public List<ImageEntity> getImageAll() {
		// TODO Auto-generated method stub
		return iImageMapper.getImageAll();
	}

	@Override
	public ImageEntity getImageById(int id) {
		// TODO Auto-generated method stub
		return iImageMapper.selectById(id);
	}
}
