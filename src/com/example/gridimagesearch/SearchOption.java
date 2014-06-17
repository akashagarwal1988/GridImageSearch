package com.example.gridimagesearch;

import java.io.Serializable;

public class SearchOption implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8974141687231795120L;
	int imageSizePos;
	int colorPos;
	int imageTypePos;
	String siteFilter;
	
	public SearchOption(int imageSize, int color, int imageType, String siteFilter){
		this.imageSizePos = imageSize;
		this.colorPos = color;
		this.imageTypePos = imageType;
		this.siteFilter = siteFilter;
	}

	public int getImageSize() {
		return imageSizePos;
	}

	public int getColor() {
		return colorPos;
	}

	public int getImageType() {
		return imageTypePos;
	}

	public String getSiteFilter() {
		return siteFilter;
	}
	
	@Override
	   public String toString() {
 	   return new StringBuffer(" imageSize : ")
 	   .append(this.imageSizePos)
 	   .append(" color : ")
 	   .append(this.colorPos).
 	   append("imageType : ").
 	   append(this.imageTypePos).
 	   append("siteFilter : ").
 	   append(this.siteFilter).toString();
	   }
	
}
