package com.hs.sea_water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("src_code")
public class SrcCode {
		
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id",type = IdType.AUTO)
	private int id;
	
	@TableField("src_code")
	private String srcCode;
	
	@TableField("src_title")
	private String srcTitle;

	public String getSrcCode() {
		return srcCode;
	}

	public void setSrcCode(String srcCode) {
		this.srcCode = srcCode;
	}

	public String getSrcTitle() {
		return srcTitle;
	}

	public void setSrcTitle(String srcTitle) {
		this.srcTitle = srcTitle;
	}
	
	
	
	
	
	

}
