package com.hs.sea_water.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hs.sea_water.entity.Info;

/**
 * @author chvfily
 * @since 2021-05-10
 *  主要对数据表操作
 * */
@Mapper
public interface IInfoMapper extends BaseMapper<Info>{
	/**
	 * BaseMapper<Info> 可选  包含了对数据表的增删查到功能  
	 * */
	
	
	/**
	 * 自定义查询功能
	 * */
	@Select("select * from info")
	public List<Info> getVideoAll();

}
