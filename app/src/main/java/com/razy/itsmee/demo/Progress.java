package com.razy.itsmee.demo;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class Progress extends Dialog {
    public Progress(Context context) {
    	super(context);
    }

    public Progress(Context context, int theme) {
        super(context, theme);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
		/*LinearLayout imageView = (LinearLayout) findViewById(R.id.spinnerImageView);
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        spinner.start();*/
        ;
    }

    public static Progress show(Context context, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {

        Progress dialog = new Progress(context, R.style.Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress);
        if (message == null || message.length() == 0) {
            //dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);

        dialog.show();
        return dialog;
    }
}