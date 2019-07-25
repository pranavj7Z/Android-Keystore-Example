package com.pranavjayaraj.matic.network.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.pranavjayaraj.matic.network.R;


public class HashDialog extends Dialog {

    private  TextView tokenView;
    private int mLastChose;
    private Button ok;

    private PopupWindow mPopupWindow;

    public HashDialog(final Context context) {
        super(context);
        mLastChose = -1;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hashdialog);
        setCanceledOnTouchOutside(true);

        tokenView = (TextView) findViewById(R.id.token);
        ok =(Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        int padding = (int) getContext().getResources().getDimension(R.dimen.activity_horizontal_margin_hash);
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

    public void setToken(String token) {
        tokenView.setText(token);
    }

}
