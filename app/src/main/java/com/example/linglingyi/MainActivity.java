package com.example.linglingyi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.linglingyi.MyScrollView.OnScrollListener;

/**
 * @author xiaanming
 * @blog http://blog.csdn.net/xiaanming
 */
public class MainActivity extends Activity implements OnScrollListener {
    /**
     * 自定义的MyScrollView
     */
    private MyScrollView myScrollView;
    /**
     * ��MyScrollView在MyScrollView里面的购买布局
     */
    private LinearLayout mBuyLayout;
    /**
     * 位于顶部的购买布局
     */
    private LinearLayout mTopBuyLayout;
    private RelativeLayout RelativeLayout_top;
    public int start_y = 0;
    public boolean is_start = true;
    public boolean is_show = true;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myScrollView = (MyScrollView) findViewById(R.id.scrollView);
        RelativeLayout_top = (RelativeLayout) findViewById(R.id.RelativeLayout_top);

        myScrollView.setOnScrollListener(this);
        //当布局的状态或者控件的可见性发生改变回调的接口
        findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的购买布局和下面的购买布局重合
                onScroll(myScrollView.getScrollY());
            }
        });
    }

    @Override
    public void onScroll(int scrollY) {
        if (is_start == true) {
            is_start = false;
            start_y = scrollY;
        }
        final TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(200);
        final TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(200);

        if (start_y < scrollY) {
            if ((scrollY - start_y) > 50) {
                is_start = true;
                if (is_show == true) {
                    is_show = false;
                    RelativeLayout_top.startAnimation(mHiddenAction);
                    RelativeLayout_top.setVisibility(View.GONE);
                }
            }
        }

        if (start_y > scrollY) {
            if (((start_y - scrollY) > 50) || (scrollY <= 90)) {
                is_start = true;
                if (is_show == false) {
                    is_show = true;
                    RelativeLayout_top.startAnimation(mShowAction);
                    RelativeLayout_top.setVisibility(View.VISIBLE);
                }
            }
        }


        //mTopBuyLayout.layout(0, mBuyLayout2ParentTop, mTopBuyLayout.getWidth(), mBuyLayout2ParentTop + mTopBuyLayout.getHeight());
    }


}
