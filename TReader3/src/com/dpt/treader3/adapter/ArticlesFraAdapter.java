package com.dpt.treader3.adapter;

import com.dpt.treader3.fragment.TReaderArticleFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ArticlesFraAdapter extends FragmentPagerAdapter {

    public ArticlesFraAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        TReaderArticleFragment articleFragment = new TReaderArticleFragment(){
            @Override
            public String getKey() {
                return super.getKey();
            }
        };
        return articleFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
