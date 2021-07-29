package com.hs.sea_water.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hs.sea_water.entity.SrcCode;
@Mapper
public interface ISrcCodeMapper extends BaseMapper<SrcCode>{
	
	@Select("select * from src_code")
	public List<SrcCode> getSrcCodeAll();
	
	@Update("select * from src_code  where src_code = #{src_code}")
	public int selectTitle(@Param("src_code") String src_code);

}
