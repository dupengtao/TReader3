package com.dpt.treader3.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dpt.tbase.app.fragment.TBaseFragment;
import com.dpt.treader3.R;
import com.dpt.treader3.adapter.MainListAdapter;
import com.dpt.treader3.widget.SwipeRefreshLayout;

public class CnblogsNewsFra extends TBaseFragment {

    private static final String TAG=CnblogsNewsFra.class.getSimpleName();
    private View mMainView;
    private RelativeLayout mMainTitle;
    private RelativeLayout mRlBackToTop;
    private ListView mMainListView;
    private TextView mTvMainHeadTime;
    private SwipeRefreshLayout mSwipeLayout;
    private Activity mContext;
    private MainListAdapter mMainAdapter;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_main, container, false);
        //initSlidingMenu(getLeftView(), null);
        mMainTitle = (RelativeLayout) mMainView.findViewById(R.id.llMainTitle);
        mRlBackToTop = (RelativeLayout) mMainView.findViewById(R.id.RlBackToTop);
        mMainListView = (ListView) mMainView.findViewById(R.id.lvMain);
        mTvMainHeadTime = (TextView) mMainView.findViewById(R.id.tvMainHeadTime);
        mSwipeLayout = (SwipeRefreshLayout) mMainView
                .findViewById(R.id.swipeRefresh);
        mSwipeLayout.setColorScheme(R.color.holo_red_light,
                R.color.holo_green_light, R.color.holo_blue_bright,
                R.color.holo_orange_light);
        mMainListView.addHeaderView(getHeadView(1));
        mMainListView.addHeaderView(getHeadView(2));
        initAdapter();
        return mMainView;
    }

    private View getHeadView(int id) {
        if (id == 1) {
            LayoutInflater lif = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View headerView = lif.inflate(R.layout.view_list_head_first,
                    mMainListView, false);
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) mContext).getWindowManager().getDefaultDisplay()
                    .getMetrics(dm);
            int screenH = dm.heightPixels;
            int h = screenH / 4;
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, h);
            headerView.setLayoutParams(params);
            return headerView;
        } else {
            LayoutInflater lif = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View headerView = lif.inflate(R.layout.view_main_head,
                    mMainListView, false);
            return headerView;
        }
    }

    private MainListAdapter initAdapter() {
        mMainAdapter = new MainListAdapter(mContext);
        return mMainAdapter;
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
    public boolean isAddCache() {
        return false;
    }
    
    @Override
    public String getKey() {
        return TAG;
    }

}
