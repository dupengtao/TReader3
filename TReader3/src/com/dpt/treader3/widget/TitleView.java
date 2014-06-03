package com.dpt.treader3.widget;

import com.dpt.treader3.R;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView extends FrameLayout {

    private static final String PULL_DOWN_REFRESH = "下拉刷新";
    private static final String LONGING_DATA = "正在载入";

    private Context mContext;
    private View mView;
    private Resources mRes;
    private RelativeLayout mRlRefresh;
    private Animation mSlideInTop;
    private Animation mSlideOutTop;
    private TextView mTvRefresh;
    private TextView mTvTitleName;
    private ImageView mIvTitleLeftIcon;

    public TitleView(Context context) {
        super(context);
        mContext = context;
        mRes = context.getResources();
        initXml();
        initAnim();
    }

    private void initAnim() {
        mSlideInTop = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_top);
        mSlideInTop.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                mRlRefresh.setVisibility(View.VISIBLE);
                setRefreshTitle(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        mSlideOutTop = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top);
        mSlideOutTop.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRlRefresh.setVisibility(View.GONE);
                setRefreshTitle(null);
            }
        });
    }

    private void initXml() {
        this.setBackgroundColor(mRes.getColor(R.color.bg_title));
        mView = View.inflate(mContext, R.layout.fragment_title, this);
        mRlRefresh = (RelativeLayout) findViewById(R.id.rlRefresh);
        mTvRefresh = (TextView) findViewById(R.id.tvRefresh);
        mTvTitleName = (TextView) findViewById(R.id.tvTitleName);
        mIvTitleLeftIcon = (ImageView) findViewById(R.id.ivTitleLeftIcon);
    }

    public void slideInTop() {
        mRlRefresh.clearAnimation();
        mRlRefresh.startAnimation(mSlideInTop);
    }

    public void slideOutTop() {
        mRlRefresh.clearAnimation();
        mRlRefresh.startAnimation(mSlideOutTop);
    }

    public void setRefreshTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            mTvRefresh.setText(PULL_DOWN_REFRESH);
        } else {
            mTvRefresh.setText(title);
        }
    }

    public void setRefreshTitleLoading() {
        setRefreshTitle(LONGING_DATA);
    }

    public void setTitle(String title) {
        mTvTitleName.setText(title);
    }

    public void setCustomStyle(int leftIconRes, final Runnable clickCb) {
        mIvTitleLeftIcon.setImageResource(leftIconRes);
        mIvTitleLeftIcon.setEnabled(clickCb != null);
        if (clickCb != null) {
            mIvTitleLeftIcon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCb.run();
                }
            });
        }
    }
}
