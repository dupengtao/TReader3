package com.dpt.treader3.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
    private String mFirstArticleId;

    protected int mCurPosition;

    public boolean isFirstLoad = true;

    protected boolean mIsMoveToRight = true;

    private int mCurArticleId;


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

        mPager.setOnPageChangeListener(new OnPageChangeListener() {
            private int lastPage;

            @Override
            public void onPageSelected(int arg0) {
                if (lastPage > arg0) {// User Move to left
                    LogHelper.e(TAG, "lllllllllll");
                    mIsMoveToRight = false;
                }
                else if (lastPage < arg0) {// User Move to right
                    LogHelper.e(TAG, "rrrrrrrrrrr");
                    mIsMoveToRight = true;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });

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
        mFirstArticleId = articleId;
        mCurArticleId = Integer.parseInt(mFirstArticleId);
    }

    private static final String[] keys = new String[] {
        "1000", "1001", "1002", "1003","1004"
    };

    class ArticlesFraAdapter extends FragmentStatePagerAdapter {

        private int index;

        public ArticlesFraAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            index = position % keys.length;
            TBaseFragment tBaseFragment;
            LogHelper.e(TAG, "position" + position);
            LogHelper.e(TAG, "key======" + keys[index]);
            if (TFragmentFactory.getInstance().isAdd(keys[index])) {
                tBaseFragment = TFragmentFactory.getInstance().get(keys[index]);
            } else {
                tBaseFragment = TFragmentFactory.getInstance().putAndAddCache(keys[index],
                        TReaderArticleFragment.class);
            }
            TReaderArticleFragment articleFragment = (TReaderArticleFragment) tBaseFragment;
            if (mIsMoveToRight) {
                articleFragment.load(mCurArticleId);
                mCurArticleId--;
            } else {
                articleFragment.load(mCurArticleId);
                mCurArticleId++;
            }

            LogHelper.e(TAG, "mCurArticleId----------------" + mCurArticleId);
            return articleFragment;
        }

        @Override
        public int getCount() {
            return 10000;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
        
        

    }
   
}
