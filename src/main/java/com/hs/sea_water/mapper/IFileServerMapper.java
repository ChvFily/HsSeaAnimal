package com.hs.sea_water.mapper;

import com.hs.sea_water.entity.FileServer;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 视频文件下载的服务器配置 Mapper 接口
 * </p>
 *
 * @author LIBO
 * @since 2020-12-31
 */
@Mapper
public interface IFileServerMapper extends BaseMapper<FileServer> {

}
