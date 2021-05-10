package com.hs.sea_water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;



@TableName("info")
public class Info {
	
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type = IdType.AUTO)
	private int id;
	@TableField("introduce")
	private String introduce; // 简介内容
	@TableField("src_name")
	private String srcName; //视频显示名称 中文
	@TableField("src_code")
	private String srcCode;
	@TableField("v_id")
	private int vId; //视频对应 的 id
	@TableField("i_id")
	private int iId; //图片对应的 id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}
	public String getSrcTypeCode() {
		return srcCode;
	}
	public void setSrcTypeCode(String srcTypeCode) {
		this.srcCode = srcTypeCode;
	}
	public int getvId() {
		return vId;
	}
	public void setvId(int vId) {
		this.vId = vId;
	}
	public int getiId() {
		return iId;
	}
	public void setiId(int iId) {
		this.iId = iId;
	}
	
	

}
