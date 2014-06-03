package com.dpt.treader3.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dpt.tbase.app.base.interfaces.IFraCommCB;
import com.dpt.tbase.app.fragment.TBaseFragment;
import com.dpt.treader3.R;
import com.dpt.treader3.adapter.ArticlesFraAdapter;

public class ArticlesFra extends TBaseFragment {

    
    
    
    public interface ArticlesFraListener extends IFraCommCB {

    }

    private static final String TAG = ArticlesFra.class.getSimpleName();

    private View mView;
    private Activity mContext;
    private ArticlesFraListener mCallBack;

    private ViewPager mPager;

    private ArticlesFraAdapter mFraAdapter;

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
        mPager = (ViewPager)mView.findViewById(R.id.pagerArticles);
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

}
