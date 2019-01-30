package com.ingic.ezhalbatek.technician.ui.binders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.NotificationsEnt;
import com.ingic.ezhalbatek.technician.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.technician.interfaces.OnLongTap;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.ViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by khan_muhammad on 9/15/2017.
 */

public class BinderNotification extends RecyclerViewBinder<NotificationsEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper preferenceHelper;
    private RecyclerItemListener itemClickListener;
    private OnLongTap onLongTap;

    public BinderNotification(Context context, BasePreferenceHelper prefHelper, RecyclerItemListener itemClickListener, OnLongTap onLongTap) {
        super(R.layout.row_item_notification_item);
        this.context = context;
        this.preferenceHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
        this.itemClickListener = itemClickListener;
        this.onLongTap=onLongTap;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }



    @Override
    public void bindView(NotificationsEnt entity, int position, Object holder, Context context) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txtJobNotification.setText(entity.getMessage());

        if (entity.getState()!=null && entity.getState().equals("0") || entity.getState().equals("1")) {
            viewHolder.mainFrameLayout.setBackground(context.getResources().getDrawable(R.drawable.unread_background));
            viewHolder.txtJobNotification.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            viewHolder.mainFrameLayout.setBackground(context.getResources().getDrawable(R.color.transparent));
        }

        viewHolder.llMainFrame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongTap.onClick(entity, position);
                return true;
            }
        });
        viewHolder.llMainFrame.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(entity, position, v.getId());
            }
        });
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_Notificationlogo)
        ImageView ivNotificationlogo;
        @BindView(R.id.txt_jobNotification)
        TextView txtJobNotification;
        @BindView(R.id.iv_next)
        ImageView ivNext;
        @BindView(R.id.ll_mainFrame)
        LinearLayout llMainFrame;
        @BindView(R.id.txt_line)
        View txtLine;
        @BindView(R.id.mainFrameLayout)
        LinearLayout mainFrameLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}