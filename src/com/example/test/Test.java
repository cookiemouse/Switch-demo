package com.example.test;

import TagButton.TagButton;
import TagButton.TagButton.OnSwitchChangListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.tagbutton.R;

public class Test extends Activity {

	private TagButton tagButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tagButton = (TagButton)findViewById(R.id.mTagButton);
		
		tagButton.setChecked(true);
		
		tagButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Test.this, Boolean.toString(tagButton.getStatus()), Toast.LENGTH_SHORT).show();
			}
		});
		
		tagButton.setOnChangListener(new OnSwitchChangListener() {

			@Override
			public void onSwitchChange(TagButton tagButton, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked)
				{
					Toast.makeText(Test.this, "开", Toast.LENGTH_SHORT).show();
				}
				else {
					{
						Toast.makeText(Test.this, "关", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
}
