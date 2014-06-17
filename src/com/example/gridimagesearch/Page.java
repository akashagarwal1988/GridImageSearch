package com.example.gridimagesearch;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Page {
	int start;
	int label;

	public Page(JSONObject jsonObject) {
		try {
			this.start = jsonObject.getInt("start");
			this.label = jsonObject.getInt("label");
		} catch (JSONException e) {
			this.start = 0;
			this.label = 0;
		}

	}

	public int getStart() {
		return start;
	}

	public int getLabel() {
		return label;
	}


	public static ArrayList<Page> fromJSONArray(JSONArray array) {
		ArrayList<Page> results = new ArrayList<Page>();
		for (int x = 0; x < array.length(); x++) {
			try {
				results.add(new Page(array.getJSONObject(x)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
