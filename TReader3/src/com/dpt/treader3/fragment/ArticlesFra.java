package com.dpt.treader3.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
    private int firstArticleId, mCurArticleId, articleId;
    public boolean isFirstInit, mIsMoveToRight = true;
    private int lastPage;
    private boolean mIsFirstMoveToRight = true, preChange = true, isPageChange;

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

            @Override
            public void onPageSelected(int arg0) {
                isFirstInit = false;
                ArticleFragment articleFragment = null;
                if (lastPage > arg0) {// User Move to left
                    LogHelper.e(TAG, "leftleftleftleft");
                    mIsMoveToRight = false;
                    articleFragment = (ArticleFragment) mFraAdapter
                            .getItem(mPager.getCurrentItem() - 1);
                    isPageChange = true;
                }
                else if (lastPage < arg0) {// User Move to right
                    LogHelper.e(TAG, "rightrightrightright");
                    mIsMoveToRight = true;
                    articleFragment = (ArticleFragment) mFraAdapter
                            .getItem(mPager.getCurrentItem() + 1);
                    isPageChange = true;
                } else {
                    isPageChange = false;
                }
                lastPage = arg0;

                if (isPageChange) {

                    if (preChange != mIsMoveToRight) {
                        System.out.println("preChange!=mIsMoveToRight");
                        if (mIsMoveToRight) {
                            System.out.println("chanage right");
                            mCurArticleId--;
                            mCurArticleId--;
                        } else {
                            System.out.println("chanage left");
                            mCurArticleId++;
                            mCurArticleId++;
                        }
                        if (articleFragment != null) {
                            preLoadNews(articleFragment);
                        }
                        mCurArticleId = articleId;
                    } else {
                        if (articleFragment != null) {
                            preLoadNews(articleFragment);
                        }
                        mCurArticleId = articleId;
                    }
                    preChange = mIsMoveToRight;
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

    protected void preLoadNews(ArticleFragment articleFragment) {

        if (mIsMoveToRight) {
            if (mIsFirstMoveToRight) {
                mCurArticleId++;
                mIsFirstMoveToRight = false;
            }
            articleId = mCurArticleId - 1;
            articleFragment.load(articleId);
        } else {
            articleId = mCurArticleId + 1;
            if (firstArticleId >= articleId) {
                articleFragment.load(articleId);
            }
        }
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
        firstArticleId = mCurArticleId = Integer.parseInt(mFirstArticleId);
        resetParams();
        if (mContext != null) {
            mFraAdapter = new ArticlesFraAdapter(getFragmentManager());
            mPager.setAdapter(mFraAdapter);
        }
    }

    public void resetParams() {
        isFirstInit=true;
        mIsMoveToRight = true;
        mIsFirstMoveToRight = preChange = true;
        isPageChange=false;
    }

    private static final String[] keys = new String[] {
            "1000", "1001", "1002", "1003"
    };

    class ArticlesFraAdapter extends FragmentStatePagerAdapter {

        private int index;

        public ArticlesFraAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LogHelper.e(TAG, "position=====   " + position);
            index = position % keys.length;
            if (index < 0) {
                index = index * -1;
            }
            TBaseFragment tBaseFragment;
            LogHelper.e(TAG, "key======   " + keys[index]);
            if (TFragmentFactory.getInstance().isAdd(keys[index])) {
                tBaseFragment = TFragmentFactory.getInstance().get(keys[index]);
            } else {
                tBaseFragment = TFragmentFactory.getInstance().putAndAddCache(keys[index],
                        ArticleFragment.class);
            }
            ArticleFragment articleFragment = (ArticleFragment) tBaseFragment;
            if (isFirstInit) {
                articleFragment.load(mCurArticleId);
                mCurArticleId--;
            }
            return articleFragment;
        }

        @Override
        public int getCount() {
            return 10000;
        }

        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            return super.instantiateItem(arg0, arg1);
        }

    }

}
