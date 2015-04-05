package de.alexvollmar.avgallery;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerActivity extends Activity {

	private ViewPager mViewPager;
	private TextView mTextViewDescription;
	private TextView mTextViewSource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);

		getActionBar().hide();

		Bundle bundle = getIntent().getExtras();
		assert bundle != null;
		
		String[] images = bundle.getStringArray(Constants.IMAGES);
		int imagePosition = bundle.getInt(Constants.IMAGE_POSITION, 0);
		String[] descriptions = bundle.getStringArray(Constants.DESCRIPTIONS);
		String[] sources = bundle.getStringArray(Constants.SOURCES);

		if (savedInstanceState != null) {
			imagePosition = savedInstanceState.getInt(Constants.SAVED_POSITION);
		}

		getLayoutInflater();

		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(images, descriptions, sources);

		mViewPager.setAdapter(imagePagerAdapter);
		mViewPager.setCurrentItem(imagePosition);
		mViewPager.setTag(imagePosition);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(Constants.SAVED_POSITION, mViewPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private String[] descriptions;
		private String[] sources;
		private LayoutInflater layoutInflater;

		ImagePagerAdapter(String[] images, String[] descriptions, String[] sources) {
			this.images = images;
			this.descriptions = descriptions;
			this.sources = sources;
			layoutInflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup viewGroup, int position, Object object) {
			viewGroup.removeView((View) object);
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object instantiateItem(ViewGroup viewGroup, int position) {
			View imageViewLayout = layoutInflater.inflate(R.layout.item_view_pager_page, viewGroup, false);

			final ImageView imageView = (ImageView) imageViewLayout.findViewById(R.id.view_pager_image);
			imageView.setImageResource(getResources().getIdentifier(images[position], "drawable", getPackageName()));

			mTextViewDescription = (TextView) imageViewLayout.findViewById(R.id.view_pager_text_description);
			mTextViewDescription.setText(descriptions[position]);

			mTextViewSource = (TextView) imageViewLayout.findViewById(R.id.view_pager_text_source);
			mTextViewSource.setText(sources[position]);
			
			// Attach a PhotoViewAttacher.
			PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
			
			attacher.setOnPhotoTapListener(new OnPhotoTapListener() {
					
				private TextView textView1;
				private TextView textView2;
				
				private OnPhotoTapListener init(TextView textView1, TextView textView2){
					this.textView1 = textView1;
					this.textView2 = textView2;
					return this;
			    }
				
				@Override
				public void onPhotoTap(View view, float x, float y) {
					System.out.println("photo touched! + mTextViewDescription.getVisibility(): " + mTextViewDescription.getVisibility());
					if (textView1.getVisibility() == View.VISIBLE) {
						textView1.setVisibility(View.GONE);
						textView2.setVisibility(View.GONE);
						System.out.println("invisible");
					} else {
						textView1.setVisibility(View.VISIBLE);
						textView2.setVisibility(View.VISIBLE);
					}
				}
				
			}.init(mTextViewDescription, mTextViewSource)); // pass the two TextViews to the Listener!
			
			viewGroup.addView(imageViewLayout, 0);

			return imageViewLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}

}