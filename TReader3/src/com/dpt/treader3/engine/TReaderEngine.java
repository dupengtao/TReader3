package com.dpt.treader3.engine;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;

import com.dpt.tbase.app.base.engine.IUiBaseResultCallBack;
import com.dpt.tbase.app.base.engine.TBaseEngine;
import com.dpt.tbase.app.base.utils.LogHelper;
import com.dpt.tbase.app.net.TBaseNetClent2;
import com.dpt.tbase.app.net.interfaces.AbJsonResultCallBack;
import com.dpt.tbase.app.net.interfaces.AbStrResultCallBack;
import com.dpt.treader3.engine.paser.TParser;
import com.dpt.treader3.engine.to.ArticleTo;
import com.dpt.treader3.engine.to.EntryTo;
import com.dpt.treader3.net.TReaderRequestFractory;
import com.dpt.treader3.net.UriHelper;


public class TReaderEngine extends TBaseEngine{

    private static final String TAG=TReaderEngine.class.getSimpleName();
    private Context mContext;
    public TReaderEngine(Context context) {
        super();
        mContext=context;
        TBaseNetClent2.getInstance(new TReaderRequestFractory());
    }

    public void loadRecentNews(int pageIndex,int pageSize, final IUiBaseResultCallBack<List<EntryTo>> uiCb) {
        AbStrResultCallBack strResultCallBack = new AbStrResultCallBack(uiCb) {
            @Override
            public void onSuccessCallBack(int statusCode, String content, String[] otherMsg) {
                LogHelper.e(TAG, content);
                try {
                    List<EntryTo> datas = TParser.parse(content);
                    uiCb.onSuccessResult(datas, statusCode, otherMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                    uiCb.onFailureResult(e, content);
                }
            }
        };
        excuteStr(mContext, UriHelper.getRecentNews(pageIndex, pageSize), uiCb, strResultCallBack);
    }
    
    public void load2NewsArticle(String url, final IUiBaseResultCallBack<ArticleTo> uiCb) {
        AbJsonResultCallBack jsonResultCallBack = new AbJsonResultCallBack(uiCb) {
            
            @Override
            public void onSuccessCallBack(JSONObject jsonObject) {
                try {
                    ArticleTo article = parserJson4Article(jsonObject);
                    uiCb.onSuccessResult(article, 200, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    uiCb.onFailureResult(e, jsonObject.toString());
                }
            }
        };
        excute(mContext, url, true, uiCb, jsonResultCallBack);
    }

    protected ArticleTo parserJson4Article(JSONObject response) {
        String submitDate = response.optString("SubmitDate", "");
        String sourceName = response.optString("SourceName", "");
        String prevNews = response.optString("PrevNews", "");
        String nextNews = response.optString("NextNews", "");
        String imageUrl = response.optString("ImageUrl", "");
        String content = response.optString("Content", "");
        String title = response.optString("Title", "");
        ArticleTo news = new ArticleTo(submitDate, sourceName, prevNews, nextNews, imageUrl, content,title);
        return news;
    }
}
