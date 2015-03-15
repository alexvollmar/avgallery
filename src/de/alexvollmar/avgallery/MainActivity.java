package de.alexvollmar.avgallery;

import de.alexvollmar.avgallery.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private String[] mImages;
	private String[] mDescriptions;
	private String[] mSources;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// read image information from images.xml
		mImages = getResources().getStringArray(R.array.images);
		mDescriptions = getResources().getStringArray(R.array.descriptions);
		mSources = getResources().getStringArray(R.array.sources);
		
		Gallery thumbnailGallery = (Gallery) findViewById(R.id.gallery);
		thumbnailGallery.setAdapter(new GalleryAdapter());

		thumbnailGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startImagePagerActivity(position);
			}
		});
	}

	private void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ViewPagerActivity.class);
		intent.putExtra(Strings.IMAGES, mImages);
		intent.putExtra(Strings.IMAGE_POSITION, position);
		intent.putExtra(Strings.DESCRIPTIONS, mDescriptions);
		intent.putExtra(Strings.SOURCES, mSources);

		startActivity(intent);
	}

	private class GalleryAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_thumbnail_image, parent, false);
			}
			imageView.setImageResource(getResources().getIdentifier(mImages[position] + Strings.THUMBNAIL_SUFFIX, "drawable", getPackageName()));
			return imageView;
		}

		@Override
		public int getCount() {
			return mImages.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}
}