package com.dpt.treader3.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dpt.tbase.app.base.engine.AbUiBaseResultCallBack;
import com.dpt.tbase.app.base.interfaces.IFraCommCB;
import com.dpt.tbase.app.fragment.TBaseFragment;
import com.dpt.treader3.R;
import com.dpt.treader3.adapter.MainListAdapter;
import com.dpt.treader3.engine.TReaderEngine;
import com.dpt.treader3.engine.to.EntryTo;
import com.dpt.treader3.widget.SwipeRefreshLayout;
import com.dpt.treader3.widget.SwipeRefreshLayout.OnRefreshListener;
import com.dpt.treader3.widget.SwipeRefreshLayout.OnTriggerListener;

public class CnblogsNewsFra extends TBaseFragment {

    private static final String TAG = CnblogsNewsFra.class.getSimpleName();
    private static final int SWIPE_MIN_DISTANCE = 100;
    private View mMainView;
    private RelativeLayout mMainTitle;
    private RelativeLayout mRlBackToTop;
    private ListView mMainListView;
    private TextView mTvMainHeadTime;
    private SwipeRefreshLayout mSwipeLayout;
    private Activity mContext;
    private MainListAdapter mMainAdapter;
    private boolean mIsVisible, misLoading, mIsFirstTigger = true;
    private int mCurPaperNum = 1;
    protected String mCurTime = "今日新闻", mFirstTime = "";
    private CnblogsNewsFraListener mCallBack;
    private Runnable mBackToTopCb;
    private SwipeDetectorCallBack mSwipeDetectorCallBack;
    private AbUiBaseResultCallBack<List<EntryTo>> mRecentNewsCB;
    private TReaderEngine mTEngine;
    private AbUiBaseResultCallBack<List<EntryTo>> mNextPageCB;

    public interface CnblogsNewsFraListener extends IFraCommCB {
        /**
         * 下拉刷新
         * 
         * @param state
         *            0=下拉未完成 1=下拉完成 2=数据加载结束
         */
        void onSwipeRefresh(int state);

        /**
         * 更改频道名称
         */
        void onChangeChannel(String channelName);

        /**
         * 新闻条目点击
         */
        void onItemClick(String articleId, boolean isNews);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        try {
            mCallBack = (CnblogsNewsFraListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CnblogsNewsFraListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_main, container, false);
        return mMainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView();
        setEvents();
        initCallBack();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
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
    }

    protected boolean setTopBarVisible(boolean isVisible) {
        if (mIsVisible == isVisible) {
            return false;
        }
        int i = isVisible ? View.VISIBLE : View.GONE;
        mMainTitle.setVisibility(i);
        mIsVisible = isVisible;
        return true;
    }

    private void loadRecentNewsNextPage() {
        loadRecentNews(mNextPageCB, ++mCurPaperNum, 20);
    }

    private void loadRecentNews(int pageNum) {
        loadRecentNews(mRecentNewsCB, pageNum, 20);
    }

    private void loadRecentNews(AbUiBaseResultCallBack<List<EntryTo>> callBack, int pageIndex, int pageSize) {
        mTEngine.loadRecentNews(pageIndex, pageSize, callBack);
    }

    private void setEvents() {
        mMainListView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                if (setTopBarVisible(firstVisibleItem > 0)) {

                } else {
                    if (misLoading) {
                        if ((firstVisibleItem + 10) > totalItemCount) {
                            misLoading = false;
                            loadRecentNewsNextPage();
                        }
                    }
                }
                if (firstVisibleItem > 2) {
                    EntryTo entry = mMainAdapter.getList().get(firstVisibleItem - 2);
                    if (!mCurTime.equals(entry.getSimpleTime())) {
                        mCurTime = entry.getSimpleTime();
                        if (mFirstTime.equals(mCurTime)) {
                            mTvMainHeadTime.setText("今日新闻");
                        } else {
                            mTvMainHeadTime.setText(entry.getSimpleTime());
                        }
                    } else {
                    }
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });

        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                mCallBack.onSwipeRefresh(2);
                loadRecentNews(1);
            }
        });

        mSwipeLayout.setOnTriggerListener(new OnTriggerListener() {

            @Override
            public void onTrigger(float percent) {
                if (percent > 0 && mIsFirstTigger) {
                    mIsFirstTigger = false;
                    mCallBack.onSwipeRefresh(0);
                } else if (percent == 0) {
                    mIsFirstTigger = true;
                    mCallBack.onSwipeRefresh(1);
                }
            }
        });

        final Animation alpha = AnimationUtils.loadAnimation(mContext,
                R.anim.alpha);
        alpha.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRlBackToTop.setVisibility(View.GONE);
            }
        });
        mBackToTopCb = new Runnable() {
            public void run() {
                mRlBackToTop.clearAnimation();
                mRlBackToTop.startAnimation(alpha);
            }
        };
        mSwipeDetectorCallBack = new SwipeDetectorCallBack() {
            @Override
            public void previous() {
                // LogHelper.e("demo", "previous");
                if (View.GONE == mRlBackToTop.getVisibility()) {
                    mRlBackToTop.setVisibility(View.VISIBLE);
                    mRlBackToTop.postDelayed(mBackToTopCb, 2000);
                }
            }

            @Override
            public void next() {
                // LogHelper.e("demo", "next");
            }
        };
        final GestureDetector detector = new GestureDetector(mContext,
                new SwipeDetector(mSwipeDetectorCallBack));
        mMainListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });

        mRlBackToTop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mMainListView.smoothScrollToPosition(0);
            }
        });

        mMainListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntryTo entry = mMainAdapter.getList().get(position - 2);
                mCallBack.onItemClick(entry.id, true);
            }
        });
    }

    private void firstLoading(boolean isloading) {
        mSwipeLayout.setRefreshing(isloading);
    }

    private void initCallBack() {

        mTEngine = new TReaderEngine(mContext);
        final Runnable onFinishCb = new Runnable() {
            @Override
            public void run() {
                firstLoading(false);
            }
        };

        mRecentNewsCB = new AbUiBaseResultCallBack<List<EntryTo>>(mCallBack) {

            @Override
            public void onStart() {
                mSwipeLayout.setEnabled(false);
                firstLoading(true);
            }

            @Override
            public void onFinish() {
                mSwipeLayout.setEnabled(true);
                mSwipeLayout.postDelayed(onFinishCb, 1000);
            }

            @Override
            public void noNetworkEnvironment() {
                super.noNetworkEnvironment();
            }

            @Override
            public void onFailureResult(Throwable e, String content) {
                // super.onFailureResult(e, content);
            }

            @Override
            public void onSuccessResult(List<EntryTo> datas, int statusCode, String[] otherMsg) {
                misLoading = true;
                datas2Ui(datas);
            }
        };

        mNextPageCB = new AbUiBaseResultCallBack<List<EntryTo>>(mCallBack) {

            @Override
            public void onFailureResult(Throwable e, String content) {
                // super.onFailureResult(e, content);
            }

            @Override
            public void noNetworkEnvironment() {
                super.noNetworkEnvironment();
            }

            @Override
            public void onSuccessResult(List<EntryTo> datas, int statusCode, String[] otherMsg) {
                misLoading = true;
                nextPage2Ui(datas);
            }

        };
    }

    protected void nextPage2Ui(List<EntryTo> datas) {
        mMainAdapter.getList().addAll(datas);
        mMainAdapter.notifyDataSetChanged();
    }

    protected void datas2Ui(List<EntryTo> datas) {
        if (mMainListView.getAdapter() == null) {
            EntryTo entry = datas.get(0);
            mFirstTime = mCurTime = entry.getSimpleTime();
            mMainAdapter.getList().addAll(datas);
            mMainListView.setAdapter(mMainAdapter);
        } else {
            EntryTo entry = mMainAdapter.getList().get(0);
            EntryTo entry2 = datas.get(0);
            if (entry.equals(entry2)) {
                Toast.makeText(mContext, "已经是最新的了", Toast.LENGTH_SHORT).show();
            } else {
                mMainAdapter.getList().clear();
                mMainAdapter.getList().addAll(datas);
                mMainAdapter.notifyDataSetChanged();
                mMainListView.smoothScrollToPosition(0);
            }
        }
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
        loadRecentNews(1);
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

    interface SwipeDetectorCallBack {
        void previous();

        void next();
    }

    class SwipeDetector extends SimpleOnGestureListener {
        SwipeDetectorCallBack swipeDetectorCallBack;

        public SwipeDetector(SwipeDetectorCallBack swipeDetectorCallBack) {
            this.swipeDetectorCallBack = swipeDetectorCallBack;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {
            try {
                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                    swipeDetectorCallBack.next();
                }
                if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                    swipeDetectorCallBack.previous();
                }
            } catch (Exception e) {
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

}
