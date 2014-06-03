package com.dpt.treader3.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dpt.tbase.app.base.interfaces.IFraCommCB;
import com.dpt.tbase.app.base.utils.LogHelper;
import com.dpt.tbase.app.base.utils.TFragmentFactory;
import com.dpt.tbase.app.fragment.TBaseFragment;
import com.dpt.treader3.R;

public class ArticlesFra extends TBaseFragment {

	public interface ArticlesFraListener extends IFraCommCB {

	}

	private static final String TAG = ArticlesFra.class.getSimpleName();

	private View mView;
	private Activity mContext;
	private ArticlesFraListener mCallBack;
	private ViewPager mPager;
	private ArticlesFraAdapter mFraAdapter;
	private String mArticleId;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		try {
			mCallBack = (ArticlesFraListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ArticlesFraListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_articles, container, false);
		mPager = (ViewPager) mView.findViewById(R.id.pagerArticles);
		mFraAdapter = new ArticlesFraAdapter(getFragmentManager());
		mPager.setAdapter(mFraAdapter);
		return mView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		setEvents();
		super.onViewCreated(view, savedInstanceState);
	}

	private void setEvents() {

	}

	@Override
	public void onViewCreatedLoad(int state) {
	}

	@Override
	public void onActivityResumedLoad(int state) {
	}

	@Override
	public boolean isTwoPane() {
		return false;
	}

	@Override
	public void onExceptionReLoad() {
	}

	@Override
	public String getKey() {
		return TAG;
	}

	@Override
	public boolean isAddCache() {
		return false;
	}

	public void initFirst(String articleId) {
		mArticleId = articleId;
	}

	private static final String[] keys = new String[] { "1000", "1001", "1002",
			"1003", "1004", "1005", "1006", "1007", "1008", "1009" };

	class ArticlesFraAdapter extends FragmentPagerAdapter {

		private int index;

		public ArticlesFraAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// LogHelper.e(TAG, "-----------------"+arg0);
			index = position % keys.length;
			TBaseFragment tBaseFragment;
			LogHelper.e(TAG, "key======"+keys[index]);
			if (TFragmentFactory.getInstance().isAdd(keys[index])) {
				//mPager.removeViewAt(index);
				tBaseFragment = TFragmentFactory.getInstance().get(keys[index]);
				LogHelper.e(TAG, "1111111111111");
			} else {
				 TFragmentFactory.getInstance().putAndAddCache(keys[index], TReaderArticleFragment.class);
				 tBaseFragment=TFragmentFactory.getInstance().get(keys[index]);
				 LogHelper.e(TAG, "222222222222");
			}
			TReaderArticleFragment articleFragment = (TReaderArticleFragment) tBaseFragment;
			articleFragment.load(mArticleId, true);
			return articleFragment;
		}

		@Override
		public int getCount() {
			return 10000;
		}

	}

}
