package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/6/18.
 */
public class SubscriberTaskBinder extends RecyclerViewBinder<SubscriptionPaymentEnt> {
    private RecyclerItemListener itemClickListener;

    public SubscriberTaskBinder(RecyclerItemListener itemClickListener) {
        super(R.layout.row_item_subscriber_task);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SubscriptionPaymentEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtSubscriptionID.setText(entity.getSubscriptionId());
        holder.txtCustomerName.setText(entity.getUser().getFullName());
        holder.txtSubscriptionPackage.setText(entity.getSubscription().getTitle());
        holder.txtSubscriptionPackage.setSelected(true);


        holder.btnViewDetail.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(entity, position, v.getId());
            }
        });
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtSubscriptionID)
        AnyTextView txtSubscriptionID;
        @BindView(R.id.txtCustomerName)
        AnyTextView txtCustomerName;
        @BindView(R.id.txtSubscriptionPackage)
        AnyTextView txtSubscriptionPackage;
        @BindView(R.id.btnViewDetail)
        Button btnViewDetail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
