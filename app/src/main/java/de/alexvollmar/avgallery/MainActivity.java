package de.alexvollmar.avgallery;

import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
			
			// generate the thumbnail bitmap
			int imageID = getResources().getIdentifier(mImages[i], "drawable", getPackageName());
			InputStream inputStream = getResources().openRawResource(imageID);
			Bitmap thumbnailBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(inputStream), Constants.THUMBNAIL_SIZE, Constants.THUMBNAIL_SIZE, true);
			imageView.setImageBitmap(thumbnailBitmap);

			final int imageNumber = i;
			imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startImagePagerActivity(imageNumber);
				}
			});
			thumbnailGallery.addView(imageView);
		}

	}

	private void startImagePagerActivity(int position) {
		
		Intent intent = new Intent(this, ViewPagerActivity.class);
		intent.putExtra(Constants.IMAGES, mImages);
		intent.putExtra(Constants.IMAGE_POSITION, position);
		intent.putExtra(Constants.DESCRIPTIONS, mDescriptions);
		intent.putExtra(Constants.SOURCES, mSources);

		startActivity(intent);
	}
}