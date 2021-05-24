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
	// 简介
	@TableField("i_introduce")
	private String iIntroduce; // 简介内容
	// 资源的英文称
	@TableField("i_name")
	private String iName; 
	// 资源编码 1-000  动物 or 植物  - 类别名称
	@TableField("i_code")
	private String iCode;
	//视频对应的id
	@TableField("v_id")
	private int vId; //视频对应 的 id
	//图片对应的id
	@TableField("i_id")
	private int iId; //图片对应的 id
	//资源类别名称
	@TableField("i_code_title")
	private String iCodeTitle;
	//中文名称
	@TableField("i_title")
	private String iTitle;
	//资源的类别 0.视频 1.图片
	@TableField("i_src_type")
	private String iSrcType;
	
	@TableField("i_icon_path")
	private String iIconPath;
	
	public String getiIconPath() {
		return iIconPath;
	}
	public void setiIconPath(String iIconPath) {
		this.iIconPath = iIconPath;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getiIntroduce() {
		return iIntroduce;
	}
	public void setiIntroduce(String iIntroduce) {
		this.iIntroduce = iIntroduce;
	}
	public String getiName() {
		return iName;
	}
	public void setiName(String iName) {
		this.iName = iName;
	}
	public String getiCode() {
		return iCode;
	}
	public void setiCode(String iCode) {
		this.iCode = iCode;
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
	public String getiCodeTitle() {
		return iCodeTitle;
	}
	public void setiCodeTitle(String iCodeTitle) {
		this.iCodeTitle = iCodeTitle;
	}
	public String getiTitle() {
		return iTitle;
	}
	public void setiTitle(String iTitle) {
		this.iTitle = iTitle;
	}
	public String getiSrcType() {
		return iSrcType;
	}
	public void setiSrcType(String iSrcType) {
		this.iSrcType = iSrcType;
	}
	
	
}
