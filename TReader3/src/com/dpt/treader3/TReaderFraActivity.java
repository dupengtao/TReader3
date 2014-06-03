package com.dpt.treader3;

import android.os.Bundle;

import com.dpt.tbase.app.base.TBaseFraActivity;
import com.dpt.tbase.app.fragment.AbCompatibleFragment;
import com.dpt.tbase.app.fragment.TBaseFragment;
import com.dpt.treader3.fragment.CnblogsNewsFra;
import com.dpt.treader3.fragment.CnblogsNewsFra.CnblogsNewsFraListener;
import com.dpt.treader3.fragment.TReaderTitleFragment;
import com.dpt.treader3.fragment.TReaderTitleFragment.TReaderTitleListener;

public class TReaderFraActivity extends TBaseFraActivity implements
CnblogsNewsFraListener,TReaderTitleListener{

    private CnblogsNewsFra mNewsFra;
    private TReaderTitleFragment mTitleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitleFragment = (TReaderTitleFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fAccTitleBar);
    }
    
    @Override
    public TBaseFragment getInitContent() {
        mNewsFra = new CnblogsNewsFra(); 
        return mNewsFra;
    }

    @Override
    public int getReplaceLayoutResId() {
        return R.id.llReleaceId;
    }

    @Override
    public AbCompatibleFragment getCompatibleFragment() {
        return null;
    }

    @Override
    public void onSwipeRefresh(int state) {
        mTitleFragment.titleChange(state);
    }

    @Override
    public void onChangeChannel(String channelName) {
        mTitleFragment.setTitleFragmentName(channelName);
    }

    @Override
    public int[] getCustomAnimationsResId() {
        int[] animations = new int[2];
        animations[0] = R.anim.silde_in_alpha;
        animations[1] = R.anim.silde_out_alpha;
        return animations;
    }

    @Override
    public void onItemClick(String articleId, boolean isNews) {
        //gotoArticleFragment(articleId, isNews);
        //changeTitle(Constants.ARTICLE_STYLE);
    }

    @Override
    public void clickLeftIcon() {
        
    }

}
