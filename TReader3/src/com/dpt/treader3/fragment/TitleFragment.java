package com.dpt.treader3.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dpt.tbase.app.fragment.TBaseFragment;
import com.dpt.treader3.R;
import com.dpt.treader3.net.Constants;
import com.dpt.treader3.widget.TitleView;

public class TitleFragment extends TBaseFragment {

    private static final String TAG=TitleFragment.class.getSimpleName();
    
    public interface TReaderTitleListener {
        void clickLeftIcon();
    }

    private Activity mContext;
    private TitleView mTitleView;
    private Runnable mArticleTitleLeftCb;
    private TReaderTitleListener mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        try {
            mCallBack = (TReaderTitleListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TReaderMainFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mTitleView = new TitleView(mContext);
        return mTitleView;
    }

    @Override
    public void onViewCreatedLoad(int state) {
        initCallBack();
    }

    private void initCallBack() {
        mArticleTitleLeftCb = new Runnable() {
            @Override
            public void run() {
                mCallBack.clickLeftIcon();
            }
        };
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

    public void titleChange(int state) {
        switch (state) {
            case 0: {
                mTitleView.slideInTop();
            }
                break;
            case 1: {
                mTitleView.slideOutTop();
            }
                break;
            case 2: {
                mTitleView.setRefreshTitleLoading();
            }
                break;
        }
    }

    public void setTitleFragmentName(String title) {
        mTitleView.setTitle(title);
    }

    public void changeStyle(int style) {
        if (Constants.ARTICLE_STYLE == style) {
            mTitleView.setCustomStyle(R.drawable.nav_back, mArticleTitleLeftCb);
        } else {
            mTitleView.setCustomStyle(R.drawable.index_nav_columns, null);
        }
    }

    @Override
    public String getKey() {
        return TAG;
    }
    
    @Override
    public boolean isAddCache() {
        return false;
    }
}
