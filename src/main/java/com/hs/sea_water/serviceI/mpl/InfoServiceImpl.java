package com.hs.sea_water.serviceI.mpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hs.sea_water.entity.Info;
import com.hs.sea_water.mapper.IInfoMapper;
import com.hs.sea_water.service.IInfoService;

/**
 * <p>
 * 	info 实体类
 * </p>
 * @author chvfily
 * @since 2021-05-10
 * */
@Service
public class InfoServiceImpl extends ServiceImpl<IInfoMapper,Info> implements IInfoService{
	
	 @Autowired IInfoMapper iInfoMapper; // 需要实例化 通过自动装配实现
	
	public InfoServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取所有的数据信息 
	 * */
	@Override
	public List<Info> getInfoAll(){
		return iInfoMapper.getVideoAll();
	}
	
	/**
	 * 根据 id 获取信息
	 * */
	@Override
	public Info getInfoById(int id) {
		
		return iInfoMapper.selectById(id);
	}

}
