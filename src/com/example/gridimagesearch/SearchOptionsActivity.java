package com.example.gridimagesearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchOptionsActivity extends Activity {
	Spinner spinnerImgSize;
	Spinner spinnerColorFilter;
	Spinner spinnerImageType;
	EditText etSiteFilter;
	
	int imageSizePos;
	int colorPos;
	int imageTypePos;
	String siteFilter;
	
	Map<Integer, String> imageSize = new HashMap<Integer, String>();
	ArrayList<String> color = new ArrayList<String>(Arrays.asList("black", "blue", "brown", "gray", "green", "red"));
	ArrayList<String> imageType = new ArrayList<String>(Arrays.asList("face", "photo", "clipart", "lineart"));
	
	SearchOption searchOption;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		
		imageSize.put(0,"icon");
		imageSize.put(1,"small|medium|large|xlarge");
		imageSize.put(2,"xxlarge");
		imageSize.put(3,"huge");
		
		
		setViews();
		
						
				spinnerImgSize.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						imageSizePos = position; 
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});
				
				spinnerColorFilter.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						colorPos = position; 
					//	Toast.makeText(getApplicationContext(),"color : " + colorPos, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});
				
				spinnerImageType.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						imageTypePos = position; 
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
				
	}
	

	private void setViews() {
		
		getSearchOptions();
		spinnerImgSize = (Spinner) findViewById(R.id.spinImgSearch);		
		 spinnerColorFilter = (Spinner) findViewById(R.id.spinColFilter);
		 spinnerImageType = (Spinner) findViewById(R.id.spinImgType);
		 etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		 
		// Create an ArrayAdapter using the string array and a default spinner layout
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			        R.array.image_size, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinnerImgSize.setAdapter(adapter);
		
			
			ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
			        R.array.color_filter, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinnerColorFilter.setAdapter(adapter2);
				
			
			ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
			        R.array.image_type, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinnerImageType.setAdapter(adapter3);

		 
		 if(searchOption!= null)
				spinnerImgSize.setSelection(searchOption.imageSizePos);
			
			if(searchOption!= null)
				spinnerColorFilter.setSelection(searchOption.colorPos);
			
			if(searchOption!= null)
				spinnerImageType.setSelection(searchOption.imageTypePos);		
			
			if(searchOption!= null)
			etSiteFilter.setText(searchOption.siteFilter);
	}
	
	public void onSave(View v){
		siteFilter = etSiteFilter.getText().toString();
		searchOption = new SearchOption(imageSizePos, colorPos, imageTypePos, siteFilter);
		saveSearchOptions();
		Intent intent = new Intent();
		intent.putExtra("imageSize", imageSize.get(imageSizePos));
		intent.putExtra("color", color.get(colorPos));
		intent.putExtra("imageType",imageType.get(imageTypePos));
		intent.putExtra("siteFilter", siteFilter);
		
		setResult(RESULT_OK, intent);
		finish();
	}
	
	public void saveSearchOptions() {
		File filesDir = getFilesDir();
		File searchOptionsFile = new File(filesDir, "searchOption.txt");	
		try{
			
			FileOutputStream fout = new FileOutputStream(searchOptionsFile);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(searchOption);
			oos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	private void getSearchOptions(){
		File filesDir = getFilesDir();
		File searchOptionsFile = new File(filesDir, "searchOption.txt");
		try{
			FileInputStream fin = new FileInputStream(searchOptionsFile);
			ObjectInputStream ois = new ObjectInputStream(fin);
			searchOption = (SearchOption) ois.readObject();
			ois.close();
		}
		catch(IOException e){
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
