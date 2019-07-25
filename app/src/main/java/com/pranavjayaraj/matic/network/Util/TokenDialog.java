package com.pranavjayaraj.matic.network.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pranavjayaraj.matic.network.R;


public class TokenDialog extends Dialog {

    private ImageView imageView;
    private  TextView tokenView;
    private int mLastChose;
    private String pathUrl;

    private PopupWindow mPopupWindow;

    public TokenDialog(final Context context) {
        super(context);
        mLastChose = -1;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.insdialog);
        setCanceledOnTouchOutside(true);

        imageView = (ImageView) findViewById(R.id.image_meizi);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tokenView = (TextView) findViewById(R.id.token);
    }

    @Override
    public void show() {
        super.show();
        int padding = (int) getContext().getResources().getDimension(R.dimen.activity_horizontal_margin);
        getWindow().getDecorView().setPadding(padding, padding, padding, padding);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        return super.onTouchEvent(event);
    }

    @Override
    public void dismiss() {
        mLastChose = -1;
        super.dismiss();
    }

    public void setImage(String url,String token) {
        pathUrl = url;
        loadImageView(pathUrl, R.drawable.agrs, imageView);
        tokenView.setText(token);
    }

    private void loadImageView(String url, int defresId, ImageView img) {
        if (null != img) {
            if (!TextUtils.isEmpty(url)) {
                Glide.with(getContext()).load(url)
                        .placeholder(defresId)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(img);
            } else {
                img.setImageResource(defresId);
            }
        }
    }
}
