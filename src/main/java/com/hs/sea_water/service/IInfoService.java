package com.hs.sea_water.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hs.sea_water.entity.Info;

/**
 * <p>
 * info 接口
 * </p>
 * @author chvfily
 * @since 2021-05-10
 * */

public interface IInfoService extends IService<Info>{
	/**
	 * 获取全部信息
	 * @return List < Info > 
	 * */
	List<Info> getInfoAll();
	/**
	 * @param id Int
	 * @return Info
	 * */
	Info getInfoById(int id);
}
