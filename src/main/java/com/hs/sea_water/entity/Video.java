package com.hs.sea_water.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("video")
public class Video implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type = IdType.AUTO)
	private int id;
	// 中文名称
	@TableField("v_title")
	private String vTitle;
	// 视频保存路径 在用之前需要添加 http://ip:port/file/+v_path 
	@TableField("v_path")
	private String vPath;
	@TableField("v_save_time")
	private String v_SaveTime;
	@TableField("v_live_start_time")
	private String vLiveStartTime;
	@TableField("v_name")
	private String vName;
	
	public String getvName() {
		return vName;
	}
	public void setvName(String vName) {
		this.vName = vName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getvTitle() {
		return vTitle;
	}
	public void setvTitle(String vTitle) {
		this.vTitle = vTitle;
	}
	public String getvPath() {
		return vPath;
	}
	public void setvPath(String vPath) {
		this.vPath = vPath;
	}
	public String getV_SaveTime() {
		return v_SaveTime;
	}
	public void setV_SaveTime(String v_SaveTime) {
		this.v_SaveTime = v_SaveTime;
	}
	public String getvLiveStartTime() {
		return vLiveStartTime;
	}
	public void setvLiveStartTime(String vLiveStartTime) {
		this.vLiveStartTime = vLiveStartTime;
	}
	
	

}
