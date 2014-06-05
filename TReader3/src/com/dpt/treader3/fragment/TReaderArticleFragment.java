package com.dpt.treader3.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dpt.tbase.app.base.engine.AbUiBaseResultCallBack;
import com.dpt.tbase.app.base.interfaces.IFraCommCB;
import com.dpt.tbase.app.fragment.TBaseFragment;
import com.dpt.treader3.R;
import com.dpt.treader3.engine.TReaderEngine;
import com.dpt.treader3.engine.to.ArticleTo;
import com.dpt.treader3.net.UriHelper;
import com.dpt.treader3.widget.ArticleView;
import com.dpt.treader3.widget.SwipeRefreshLayout;
import com.dpt.treader3.widget.SwipeRefreshLayout.OnRefreshListener;

public class TReaderArticleFragment extends TBaseFragment {

    
    
    
    public interface TReaderArticleListener extends IFraCommCB {

    }

    private static final String TAG = TReaderArticleFragment.class.getSimpleName();

    private View mView;
    private WebView mWvArticle;
    private Activity mContext;
    private TReaderEngine mTReaderEngine;
    private TReaderArticleListener mCallBack;
    private AbUiBaseResultCallBack<ArticleTo> mLoadNewsArticleCb;
    private String mArticleUrl;
    private ArticleView mArticleView;
    private SwipeRefreshLayout mSwipeLayout;
    private boolean mIsNews;
    private String mArticleId;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        try {
            mCallBack = (TReaderArticleListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TReaderArticleListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_article, container, false);
        mWvArticle = (WebView) mView.findViewById(R.id.wvArticle);
        mArticleView = new ArticleView(mContext, mWvArticle);
        mSwipeLayout = (SwipeRefreshLayout) mView
                .findViewById(R.id.swipeRefresh);
        mSwipeLayout.setColorScheme(R.color.holo_red_light,
                R.color.holo_green_light, R.color.holo_blue_bright,
                R.color.holo_orange_light);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initCb();
        setEvents();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setEvents() {
        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                mArticleView.clear();
                mTReaderEngine.load2NewsArticle(mArticleUrl, mLoadNewsArticleCb);
            }
        });
    }

    private void initCb() {
        mTReaderEngine = new TReaderEngine(mContext);
        mLoadNewsArticleCb = new AbUiBaseResultCallBack<ArticleTo>(mCallBack) {
            @Override
            public void onStart() {
                mSwipeLayout.setRefreshing(true);
            }

            @Override
            public void onFinish() {
                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public void noNetworkEnvironment() {
                super.noNetworkEnvironment();
            }

            @Override
            public void onSuccessResult(ArticleTo datas, int statusCode, String[] otherMsg) {
                datas2ui(datas);
            }

        };
    }

    protected void datas2ui(ArticleTo datas) {
        mArticleView.setArticleTitle(datas.getTitle());
        String context = fromatContext(datas.getContent());
        mArticleView.setArticleContext(context);
        mArticleView.setArticlePrompt(datas.getSourceName() + " 发布于 " + datas.getSubmitDate());
    }

    private String fromatContext(String content) {
        return content.replaceAll("\r\n", "");
    }

    @Override
    public void onViewCreatedLoad(int state) {
        load(mArticleId, mIsNews);
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

    public void load(String articleId, boolean isNews) {
        mArticleId = articleId;
        mIsNews = isNews;
        if (mContext != null) {
            mArticleUrl = UriHelper.getNewsItemBody(articleId);
            mArticleView.scroll2Top();
            mArticleView.clear();
            mTReaderEngine.load2NewsArticle(mArticleUrl, mLoadNewsArticleCb);

        }
    }
    
    public void load(int articleId) {
        load(String.valueOf(articleId), true);
    }

    @Override
    public String getKey() {
        return TAG;
    }

}
