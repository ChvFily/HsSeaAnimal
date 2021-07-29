package com.hs.sea_water.service.Impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hs.sea_water.entity.FileServer;
import com.hs.sea_water.mapper.IFileServerMapper;
import com.hs.sea_water.service.IFileServerService;

/**
 * <p>
 * 视频文件下载的服务器配置 服务实现类
 * </p>
 *
 * @author LIBO
 * @since 2020-12-31
 */
@CacheConfig(cacheNames = "HsFileServer")
@Service
public class FileServerServiceImpl extends ServiceImpl<IFileServerMapper, FileServer> implements IFileServerService {
	
	/**
	 * 获取SRS服务器列表（可能存在多个列表）
	 * @author chvfily
	 * @since 2021-05-20
	 * */
	@Cacheable(key = "#root.methodName",unless = "#result==null")
	@Override
	public List<FileServer> list() {
		return super.list();
	}
	/**
	 * 通过id 获取 实体数据
	 * */
	@Cacheable(key="#id",unless = "#result==null")
	@Override
	public FileServer getById(Serializable id) {
		/**
		 * 根据 ID 查询数据
		 * */
		return super.getById(id);
	}
}
