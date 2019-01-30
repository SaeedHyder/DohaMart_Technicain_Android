package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/2/18.
 */
public class SubscriptionPendingJobsBinder extends RecyclerViewBinder<String> {
    public SubscriptionPendingJobsBinder() {
        super(R.layout.row_item_subscription_pending_jobs);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtDuration)
        AnyTextView txtDuration;
        @BindView(R.id.txtSubscriberID)
        AnyTextView txtSubscriberID;
        @BindView(R.id.txtPackageType)
        AnyTextView txtPackageType;
        @BindView(R.id.txtVisitDate)
        AnyTextView txtVisitDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
