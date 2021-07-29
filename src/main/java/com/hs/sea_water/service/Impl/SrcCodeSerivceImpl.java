package com.hs.sea_water.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hs.sea_water.entity.ImageEntity;
import com.hs.sea_water.entity.SrcCode;
import com.hs.sea_water.mapper.IImageMapper;
import com.hs.sea_water.mapper.ISrcCodeMapper;
import com.hs.sea_water.service.IImageService;
import com.hs.sea_water.service.ISrcCodeService;

@Service
public class SrcCodeSerivceImpl  extends ServiceImpl<ISrcCodeMapper,SrcCode> implements ISrcCodeService {

	@Autowired ISrcCodeMapper iSrcCodeMapper;
	@Override
	public List<SrcCode> getImageAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SrcCode getSrcCodeById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
