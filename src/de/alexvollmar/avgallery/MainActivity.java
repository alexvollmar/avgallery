package de.alexvollmar.avgallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private String[] mImages;
	private String[] mDescriptions;
	private String[] mSources;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
        LinearLayout thumbnailGallery = (LinearLayout) findViewById(R.id.thumbnail_gallery);    
		
		// read image information from images.xml
		mImages = getResources().getStringArray(R.array.images);
		mDescriptions = getResources().getStringArray(R.array.descriptions);
		mSources = getResources().getStringArray(R.array.sources);

		for (int i = 0; i < mImages.length; i++) {

		    ImageView imageView = new ImageView(getApplicationContext());
		    imageView.setImageResource(getResources().getIdentifier(mImages[i] + Strings.THUMBNAIL_SUFFIX, "drawable", getPackageName()));
		    final int counter = i;
		    imageView.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View view) {
		            startImagePagerActivity(counter);
		        }
		    });
		    thumbnailGallery.addView(imageView);
		}
		
	}

	private void startImagePagerActivity(int position) {	
		Intent intent = new Intent(this, ViewPagerActivity.class);
		intent.putExtra(Strings.IMAGES, mImages);
		intent.putExtra(Strings.IMAGE_POSITION, position);
		intent.putExtra(Strings.DESCRIPTIONS, mDescriptions);
		intent.putExtra(Strings.SOURCES, mSources);

		startActivity(intent);
	}
}