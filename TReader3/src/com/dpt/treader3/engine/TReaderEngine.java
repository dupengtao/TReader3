package com.dpt.treader3.engine;

import java.util.List;

import android.content.Context;

import com.dpt.tbase.app.base.engine.IUiBaseResultCallBack;
import com.dpt.tbase.app.base.engine.TBaseEngine;
import com.dpt.tbase.app.base.utils.LogHelper;
import com.dpt.tbase.app.net.interfaces.AbStrResultCallBack;
import com.dpt.treader3.engine.paser.TParser;
import com.dpt.treader3.engine.to.EntryTo;
import com.dpt.treader3.net.UriHelper;


public class TReaderEngine extends TBaseEngine{

    private static final String TAG=TReaderEngine.class.getSimpleName();
    private Context mContext;
    public TReaderEngine(Context context) {
        super();
        mContext=context;
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
    
}
