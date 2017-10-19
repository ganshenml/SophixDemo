package com.ganshenml.sophixdemo;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by Administrator on 2017/10/19.
 */

public class AdPopupWindow extends PopupWindow implements View.OnClickListener {
    private Context context;
    private String url;
    private ImageView contentIv, crossIv;
    private ContentLoadingProgressBar loadPb;

    public AdPopupWindow(Context context, String url) {
        super(context);
        this.context = context;
        this.url = url;
        initView(context);
    }

    private void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.ad_content, null);
        contentIv = (ImageView) view.findViewById(R.id.contentIv);
        crossIv = (ImageView) view.findViewById(R.id.crossIv);
        loadPb = (ContentLoadingProgressBar) view.findViewById(R.id.loadPb);
        contentIv.setOnClickListener(this);
        crossIv.setOnClickListener(this);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.DialogShowStyle); //设置弹出窗体动画效果
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.semi_black_transparent));
        this.setBackgroundDrawable(dw);
        this.getBackground().setAlpha(220);
        this.setContentView(view);

        Glide.with(context).load(url).crossFade().fitCenter().transform(new GlideRoundTransform(context, 8)).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                Toast.makeText(context, "加载图片完毕", Toast.LENGTH_SHORT).show();
                loadPb.setVisibility(View.GONE);
                contentIv.setImageDrawable(resource);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crossIv:
                toDismiss();
                break;
            case R.id.contentIv:
                toDismiss();
                break;
            default:
                break;
        }
    }

    public void toDismiss() {
        if (loadPb != null && loadPb.getAnimation() != null) {
            loadPb.getAnimation().cancel();
        }

        dismiss();
    }
}
