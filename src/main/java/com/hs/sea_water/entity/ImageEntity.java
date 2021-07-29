package com.hs.sea_water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("image")
public class ImageEntity {
	
	private static final long serialVersionUID = 1L;

	@TableId(value="id",type = IdType.AUTO)
	private int id;
	
	@TableField("i_title")
	private String iTitle;
	
	@TableField("i_img1")
	private String iImg1;
	
	@TableField("i_img2")
	private String iImg2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getiTitle() {
		return iTitle;
	}

	public void setiTitle(String iTitle) {
		this.iTitle = iTitle;
	}

	public String getiImg1() {
		return iImg1;
	}

	public void setiImg1(String iImg1) {
		this.iImg1 = iImg1;
	}

	public String getiImg2() {
		return iImg2;
	}

	public void setiImg2(String iImg2) {
		this.iImg2 = iImg2;
	}
	

}
