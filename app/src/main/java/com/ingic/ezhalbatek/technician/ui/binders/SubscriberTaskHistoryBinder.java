package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/2/18.
 */
public class SubscriberTaskHistoryBinder extends RecyclerViewBinder<SubscriptionPaymentEnt> {

    private Double amount = 0.0;
    private String currencyCode;
    private RecyclerItemListener itemClickListener;

    public SubscriberTaskHistoryBinder(RecyclerItemListener itemClickListener) {
        super(R.layout.row_item_subscriber_history_task);
        this.itemClickListener = itemClickListener;

    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SubscriptionPaymentEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.btnDetails.setPaintFlags(holder.btnDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.txtCustomerNoText.setText(entity.getUser().getFullName()+"");
        holder.txtSubscriptionText.setText(entity.getSubscription().getId()+"");

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(entity, position, view.getId());
                }
            }
        });

          /* amount=0.0;
        if(entity.getAdditionalJobs().size()>0){
            for(AdditionalJob item: entity.getAdditionalJobs()){
                amount=amount+Double.parseDouble(item.getItem().getAmount());
            }
        }
        currencyCode="QAR";
        holder.txtCustomerName.setText(entity.getUser().getFullName()+"");
        holder.txtSubscriptionID.setText(entity.getSubscriptionId()+"");
        holder.txtServiceTitle.setText(entity.getSubscription().getTitle()+"");
        holder.txtAdditionalCost.setText(currencyCode+" "+String.valueOf(amount));
        holder.rbRating.setScore(1);
*/


    }

    static class ViewHolder extends BaseViewHolder {
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


  /*  static class ViewHolder  extends BaseViewHolder{
        @BindView(R.id.txtCustomerName)
        AnyTextView txtCustomerName;
        @BindView(R.id.txtSubscriptionID)
        AnyTextView txtSubscriptionID;
        @BindView(R.id.txtServiceTitle)
        AnyTextView txtServiceTitle;
        @BindView(R.id.txtAdditionalCost)
        AnyTextView txtAdditionalCost;
        @BindView(R.id.rbRating)
        CustomRatingBar rbRating;
        @BindView(R.id.mainFrame)
        LinearLayout mainframe;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }*/
}
