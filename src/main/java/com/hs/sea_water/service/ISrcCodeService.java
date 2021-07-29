package com.hs.sea_water.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hs.sea_water.entity.SrcCode;

public interface ISrcCodeService extends IService<SrcCode>{
	
	List<SrcCode> getImageAll();
	/**
	 * @param id Int
	 * @return ImageEntity
	 * */
	SrcCode getSrcCodeById(int id);

}
