package com.hs.sea_water.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("src_video")
public class Video implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type = IdType.AUTO)
	private int id;
	@TableField("v_name")
	private String vName;
	@TableField("v_path")
	private String vPath;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getvName() {
		return vName;
	}
	public void setvName(String vName) {
		this.vName = vName;
	}
	public String getvPath() {
		return vPath;
	}
	public void setvPath(String vPath) {
		this.vPath = vPath;
	}
	
	

}
