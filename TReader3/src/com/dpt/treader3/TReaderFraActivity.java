package com.dpt.treader3;

import android.os.Bundle;

import com.dpt.tbase.app.base.TBaseFraActivity;
import com.dpt.tbase.app.fragment.AbCompatibleFragment;
import com.dpt.tbase.app.fragment.TBaseFragment;
import com.dpt.treader3.fragment.ArticlesFra;
import com.dpt.treader3.fragment.ArticlesFra.ArticlesFraListener;
import com.dpt.treader3.fragment.CnblogsNewsFra;
import com.dpt.treader3.fragment.CnblogsNewsFra.CnblogsNewsFraListener;
import com.dpt.treader3.fragment.TReaderArticleFragment;
import com.dpt.treader3.fragment.TReaderArticleFragment.TReaderArticleListener;
import com.dpt.treader3.fragment.TReaderTitleFragment;
import com.dpt.treader3.fragment.TReaderTitleFragment.TReaderTitleListener;
import com.dpt.treader3.net.Constants;

public class TReaderFraActivity extends TBaseFraActivity implements
CnblogsNewsFraListener,TReaderTitleListener,TReaderArticleListener,ArticlesFraListener{

    private CnblogsNewsFra mNewsFra;
    private TReaderTitleFragment mTitleFragment;
    private TReaderArticleFragment mArticleFragment;
    private ArticlesFra mArticlesFra;

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
        gotoArticlesFragment(articleId, isNews);
        changeTitle(Constants.ARTICLE_STYLE);
    }

    private void changeTitle(int style) {
        mTitleFragment.changeStyle(style);
    }

    private void gotoArticlesFragment(String articleId, boolean isNews) {
        initArticlesFragment();
        mArticlesFra.initFirst(articleId);
        switchContent(mCur, mArticlesFra);
    }
    private ArticlesFra initArticlesFragment() {
        if (mArticlesFra == null) {
            mArticlesFra = new ArticlesFra();
        }
        return mArticlesFra;
    }
    
    private TReaderArticleFragment initArticleFragment() {
        if (mArticleFragment == null) {
            mArticleFragment = new TReaderArticleFragment();
        }
        return mArticleFragment;
    }
    
    @Override
    public void clickLeftIcon() {
        onBackPressed();
    }
    
    @Override
    public void onBackPressed() {
        if (mCur == mArticleFragment) {
            toMainFragment();
            return;
        }
        super.onBackPressed();
    }
    
    private void toMainFragment() {
        if(switchContent(mCur, mNewsFra)){
            changeTitle(Constants.LIST_STYLE);
        }
    }

}
