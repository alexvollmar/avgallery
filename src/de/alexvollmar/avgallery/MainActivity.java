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

	private String[] mImages = new String[] { "chefchaouen_1", "chefchaouen_2", "essaouira_1", "essaouira_2", "marrakech", "sidi_ifni_1", "sidi_ifni_2", "tetouan", };

	private String[] mDescriptions = new String[] { "Chefchaouen 1", "Chefchaouen 2", "Essaouira 1", "Essaouira 2", "Marrakech", "Sidi Ifni 1", "Sidi Ifni 2", "Tétouan" };
	private String[] mSources = new String[] { "Own Photo, 11.11.2013", "Own Photo, 11.11.2013", "Own Photo, 15.11.2013", "Own Photo, 16.11.2013", "Own Photo, 13.11.2013", "Own Photo, 19.11.2013",
			"Own Photo, 19.11.2013", "Own Photo, 10.11.2013" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

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