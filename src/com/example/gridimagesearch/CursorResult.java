package com.example.gridimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CursorResult implements Serializable {
	ArrayList<Page> pages;
	int estimatedResultCount;
	int currentPageIndex;
	String moreResultsUrl;

	public CursorResult(JSONObject json) {
		try {
			JSONArray pagesJson = json.getJSONArray("cursor");
			this.pages.clear();
			this.pages.addAll(Page.fromJSONArray(pagesJson));
			this.estimatedResultCount = json.getInt("estimatedResultCount");
			this.currentPageIndex = json.getInt("currentPageIndex");
			this.moreResultsUrl = json.getString("moreResultsUrl");
		} catch (JSONException e) {
			this.estimatedResultCount = 0;
			this.currentPageIndex = 0;
			this.moreResultsUrl = null;
		}
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

	public int getEstimatedResultCount() {
		return estimatedResultCount;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public String getMoreResultsUrl() {
		return moreResultsUrl;
	}

	public static CursorResult fromJSONObject(
			JSONObject object) {
		CursorResult cursorResult = null;
		cursorResult  =  new CursorResult(object);			
		return cursorResult;
		}
		
	
}
