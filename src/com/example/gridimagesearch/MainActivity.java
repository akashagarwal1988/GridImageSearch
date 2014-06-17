package com.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {

	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	CursorResult cursorResult;
	
	String imageSize = "";
	String imageColor = "";
	String imageType = "";
	String siteFilter = "";
	
	String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&v=1.0&q=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
				
			}
		});
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
	        	Toast.makeText(getApplicationContext(), "Inside OnScroll Listener", Toast.LENGTH_LONG).show();
	        	customLoadMoreDataFromApi(page); 
	        	
	            	                // or customLoadMoreDataFromApi(totalItemsCount); 
	        }

			private void customLoadMoreDataFromApi(int page) {
				String query = etQuery.getText().toString();
//				Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT)
//						.show();
				AsyncHttpClient client = new AsyncHttpClient();
//				Toast.makeText(getApplicationContext(), "Image Color is " + imageColor, Toast.LENGTH_SHORT).show();
//				Toast.makeText(getApplicationContext(), "Image size is " + imageSize, Toast.LENGTH_SHORT).show();
//				Toast.makeText(getApplicationContext(), "Image Type is " + imageType, Toast.LENGTH_SHORT).show();
//				Toast.makeText(getApplicationContext(), "SiteFilter is " + siteFilter, Toast.LENGTH_SHORT).show();
				url = cursorResult.getMoreResultsUrl();
				Toast.makeText(getApplicationContext(), "cursor Result is" + cursorResult, Toast.LENGTH_SHORT).show();
				client.get(
					url + Uri.encode(query) + "&imgcolor=" + imageColor + "&imgsz=" + imageSize + "&imgtype=" + imageType + "&as_sitesearch=" + siteFilter,
				//	"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&v=1.0&q=" + Uri.encode(query) + "&imgcolor=" + "red" + "&imgsz=" + "icon" + "&imgtype=" + "face" + "&as_sitesearch=" + "",

					new JsonHttpResponseHandler() {
							
							@Override
							public void onSuccess(JSONObject response) {
								JSONArray imageJsonResults = null;
								
								try {
									imageJsonResults = response.getJSONObject(
											"responseData").getJSONArray("results");
									imageResults.clear();
									imageAdapter.addAll(ImageResult
											.fromJSONArray(imageJsonResults));
									
									 cursorResult = CursorResult.fromJSONObject(response.getJSONObject("responseData").getJSONObject("cursor"));

								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});
				
			}
	        });
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
	
	public void onSettings(MenuItem mi){
		Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(getApplicationContext(), SearchOptionsActivity.class);
		startActivityForResult(i,1);
	}
	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button) findViewById(R.id.btnSave);
		gvResults = (GridView) findViewById(R.id.gvResults);
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT)
				.show();
		AsyncHttpClient client = new AsyncHttpClient();
//		Toast.makeText(getApplicationContext(), "Image Color is " + imageColor, Toast.LENGTH_SHORT).show();
//		Toast.makeText(getApplicationContext(), "Image size is " + imageSize, Toast.LENGTH_SHORT).show();
//		Toast.makeText(getApplicationContext(), "Image Type is " + imageType, Toast.LENGTH_SHORT).show();
//		Toast.makeText(getApplicationContext(), "SiteFilter is " + siteFilter, Toast.LENGTH_SHORT).show();


		client.get(
			url + Uri.encode(query) + "&imgcolor=" + imageColor + "&imgsz=" + imageSize + "&imgtype=" + imageType + "&as_sitesearch=" + siteFilter,
		//	"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&v=1.0&q=" + Uri.encode(query) + "&imgcolor=" + "red" + "&imgsz=" + "icon" + "&imgtype=" + "face" + "&as_sitesearch=" + "",

			new JsonHttpResponseHandler() {
					
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						
						try {
							imageJsonResults = response.getJSONObject(
									"responseData").getJSONArray("results");
							imageResults.clear();
							imageAdapter.addAll(ImageResult
									.fromJSONArray(imageJsonResults));
							
							 cursorResult = CursorResult.fromJSONObject(response.getJSONObject("responseData").getJSONObject("cursor"));
							 
						
							Log.v("verbose", imageResults.toString());
							Log.d("DEBUG", imageResults.toString() + "#########something");
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == 1) {
	     // Extract name value from result extras
	     imageSize = data.getExtras().getString("imageSize");
	     imageColor = data.getExtras().getString("color");
	     imageType = data.getExtras().getString("imageType");
	     siteFilter = data.getExtras().getString("siteFilter");
	  }
	} 
}
