package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdditionalJob;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/10/2018.
 */

public class UserTaskHistoryBinder extends RecyclerViewBinder<UserPaymentEnt> {

    private RecyclerItemListener itemClickListener;
    private Double amount = 0.0;

    public UserTaskHistoryBinder(RecyclerItemListener itemClickListener) {
        super(R.layout.row_item_subscriber_history_task);
        this.itemClickListener = itemClickListener;

    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(UserPaymentEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;


        holder.btnDetails.setPaintFlags(holder.btnDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.txtSubscriptionId.setText(R.string.request_id);

        holder.txtCustomerNoText.setText(entity.getUserDetail().getFullName()+"");
        holder.txtSubscriptionText.setText(entity.getId()+"");


        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(entity, position, view.getId());
                }
            }
        });

        /*amount = 0.0;

        if (entity.getAdditionalJobs().size() > 0) {
            for (AdditionalJob item : entity.getAdditionalJobs()) {
                amount = amount + Double.parseDouble(item.getItem().getAmount());
            }
        }

        amount = amount + Double.parseDouble(entity.getTotal());*/

        /* holder.requestId.setText(R.string.request_id);
        holder.requestTitle.setText(R.string.request_title);

        holder.txtCustomerName.setText(entity.getUserDetail().getFullName() + "");
        holder.txtSubscriptionID.setText(entity.getId() + "");
        holder.txtServiceTitle.setText(entity.getJobTitle() + "");
        holder.txtAdditionalCost.setText("AED " + amount);
        holder.rbRating.setScore(1);*/
    }


    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.txtCustomerNo)
        AnyTextView txtCustomerNo;
        @BindView(R.id.txtCustomerNoText)
        AnyTextView txtCustomerNoText;
        @BindView(R.id.txtSubscriptionId)
        AnyTextView txtSubscriptionId;
        @BindView(R.id.txtSubscriptionText)
        AnyTextView txtSubscriptionText;
        @BindView(R.id.btnDetails)
        Button btnDetails;
        @BindView(R.id.mainframe)
        RelativeLayout mainframe;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
