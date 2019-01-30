package com.ingic.ezhalbatek.technician.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import java.util.Objects;


/**
 * Created on 5/24/2017.
 */

public class DialogHelper {

    private Dialog dialog;
    private Activity context;

    public DialogHelper(Activity context) {
        this.context = context;

    }

    private void textApprend(String text, int color) {
        SpannableString str1 = new SpannableString(text);
        str1.setSpan(new ForegroundColorSpan(color), 0, str1.length(), 0);

    }

    public void setTextViewText(int ID, String Text) {
        AnyTextView textView = (AnyTextView) dialog.findViewById(ID);
        textView.setText(Text);
    }


    public void showCommonDialog(View.OnClickListener onokclicklistener, int title, int bodyText, int buttonOkText, int buttonCancelText, boolean isShowCancel,
                                 boolean isShowCloseButton) {
        this.dialog = null;
        this.dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialog_common);

        AnyTextView heading = (AnyTextView) dialog.findViewById(R.id.title_white);
        heading.setText(title);
        heading.setVisibility(isShowCloseButton ? View.VISIBLE : View.GONE);

        AnyTextView body = (AnyTextView) dialog.findViewById(R.id.txt_description);
        body.setText(bodyText);

        FrameLayout cancelLayout = (FrameLayout) dialog.findViewById(R.id.cancel_container);
        cancelLayout.setVisibility(isShowCancel ? View.VISIBLE : View.GONE);

        ImageView btnClose = (ImageView) dialog.findViewById(R.id.btn_close);
        btnClose.setVisibility(isShowCloseButton ? View.VISIBLE : View.GONE);
        btnClose.setOnClickListener(view -> dialog.dismiss());

        View line = (View) dialog.findViewById(R.id.lineView);
        line.setVisibility(isShowCloseButton ? View.VISIBLE : View.GONE);


        Button okbutton = (Button) dialog.findViewById(R.id.btn_ok);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(okbutton.getLayoutParams());

        okbutton.setText(buttonOkText);
        okbutton.setLayoutParams(layoutParams);
        okbutton.setOnClickListener(onokclicklistener);

        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_cancel);
        cancelbutton.setText(buttonCancelText);
        cancelbutton.setOnClickListener(view -> dialog.dismiss());


    }

    public void showCommonDialog(View.OnClickListener onokclicklistener, int title, int buttonOkText, int buttonCancelText, boolean isShowCancel,
                                 boolean isShowCloseButton, boolean isShowLogo) {
        this.dialog = null;
        this.dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialog_common);

        AnyTextView heading = (AnyTextView) dialog.findViewById(R.id.title_white);
        heading.setText(title);
        heading.setVisibility(isShowCloseButton ? View.VISIBLE : View.GONE);

        AnyTextView body = (AnyTextView) dialog.findViewById(R.id.txt_description);
        body.setVisibility(View.GONE);

        FrameLayout cancelLayout = (FrameLayout) dialog.findViewById(R.id.cancel_container);
        cancelLayout.setVisibility(isShowCancel ? View.VISIBLE : View.GONE);

        ImageView btnClose = (ImageView) dialog.findViewById(R.id.btn_close);
        btnClose.setVisibility(isShowCloseButton ? View.VISIBLE : View.GONE);
        btnClose.setOnClickListener(view -> dialog.dismiss());

        View line = (View) dialog.findViewById(R.id.lineView);
        line.setVisibility(isShowCloseButton ? View.VISIBLE : View.GONE);


        Button okbutton = (Button) dialog.findViewById(R.id.btn_ok);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(okbutton.getLayoutParams());

        okbutton.setText(buttonOkText);
        okbutton.setLayoutParams(layoutParams);
        okbutton.setOnClickListener(onokclicklistener);

        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_cancel);
        cancelbutton.setText(buttonCancelText);
        cancelbutton.setOnClickListener(view -> dialog.dismiss());

        AnyEditTextView edtBody = (AnyEditTextView) dialog.findViewById(R.id.edtBody);
        edtBody.setVisibility(View.VISIBLE);

        ImageView imgLogo = (ImageView) dialog.findViewById(R.id.imgLogo);
        imgLogo.setVisibility(isShowLogo ? View.VISIBLE : View.GONE);


    }

    public void BlockAccountDialoge(View.OnClickListener listner) {
        this.dialog = null;
        this.dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialoge_block);

        Button okbutton = (Button) dialog.findViewById(R.id.btn_signup);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(okbutton.getLayoutParams());

        ImageView btnClose = (ImageView) dialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(view -> dialog.dismiss());

        okbutton.setLayoutParams(layoutParams);
        okbutton.setOnClickListener(listner);


    }

    public void acknowledgementDialoge() {
        this.dialog = null;
        this.dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialoge_acknowlegment);

        Button okbutton = (Button) dialog.findViewById(R.id.btn_ok);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(okbutton.getLayoutParams());

        okbutton.setLayoutParams(layoutParams);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void jobCompletedDialoge() {
        this.dialog = null;
        this.dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialoge_job_completed);

        Button okbutton = (Button) dialog.findViewById(R.id.btn_ok);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(okbutton.getLayoutParams());

        okbutton.setLayoutParams(layoutParams);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public String getEditableText() {
        return ((AnyEditTextView) dialog.findViewById(R.id.edtBody)).getText().toString();
    }

    public AnyEditTextView getEditTextView() {
        return ((AnyEditTextView) dialog.findViewById(R.id.edtBody));
    }


    public void showDialog() {
        if (!((Activity) context).isFinishing()) {
            //show dialog
            dialog.show();
        }

    }

    public void setCancelable(boolean isCancelable) {
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
    }

    public void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
