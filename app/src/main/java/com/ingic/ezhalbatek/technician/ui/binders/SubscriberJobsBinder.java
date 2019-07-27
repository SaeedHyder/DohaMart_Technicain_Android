package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.SubscriberEnt;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/25/18.
 */
public class SubscriberJobsBinder extends RecyclerViewBinder<SubscriptionPaymentEnt> implements View.OnClickListener {
    private RecyclerItemListener listener;

    public SubscriberJobsBinder(RecyclerItemListener listener) {
        super(R.layout.row_item_subscriber_jobs);
        this.listener = listener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SubscriptionPaymentEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.btnDetails.setTag(R.integer.key_object, entity);
        holder.btnDetails.setTag(R.integer.key_position, position);
        holder.btnDetails.setOnClickListener(this);
        holder.btnAddDetail.setTag(R.integer.key_object, entity);
        holder.btnAddDetail.setTag(R.integer.key_position, position);
        holder.btnAddDetail.setOnClickListener(this);
        holder.btnReport.setTag(R.integer.key_object, entity);
        holder.btnReport.setTag(R.integer.key_position, position);
        holder.btnReport.setOnClickListener(this);

        holder.btnDetails.setPaintFlags(holder.btnDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.btnAddDetail.setPaintFlags(holder.btnAddDetail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.btnReport.setVisibility(View.GONE);

        if (entity.getSubscriptionRooms().size() <= 0) {
            holder.btnDetails.setVisibility(View.GONE);
            holder.btnAddDetail.setVisibility(View.VISIBLE);
        } else {
            holder.btnDetails.setVisibility(View.VISIBLE);
            holder.btnAddDetail.setVisibility(View.GONE);
        }

       /* if(entity.getSubscriptionRooms().size()<=0){
            holder.btnDetails.setVisibility(View.GONE);
            holder.btnReport.setVisibility(View.GONE);
            holder.btnSeperatorLine.setVisibility(View.GONE);
            holder.btnAddDetail.setVisibility(View.VISIBLE);
        }else if(entity.getStatus().equals("1")){
            holder.btnDetails.setVisibility(View.VISIBLE);
            holder.btnReport.setVisibility(View.VISIBLE);
            holder.btnSeperatorLine.setVisibility(View.VISIBLE);
            holder.btnDetails.setBackground(getDrawable(R.drawable.button_red, context));
            holder.btnReport.setBackground(getDrawable(R.drawable.button_gray, context));
            holder.btnAddDetail.setVisibility(View.GONE);
            holder.btnReport.setEnabled(false);
        }else {
            holder.btnDetails.setVisibility(View.VISIBLE);
            holder.btnReport.setVisibility(View.VISIBLE);
            holder.btnSeperatorLine.setVisibility(View.VISIBLE);
            holder.btnDetails.setBackground(getDrawable(R.drawable.button_gray, context));
            holder.btnReport.setBackground(getDrawable(R.drawable.button_red, context));
            holder.btnDetails.setEnabled(false);
            holder.btnAddDetail.setVisibility(View.GONE);
        }*/

        holder.txtCustomerNoText.setText(entity.getUser().getId() + "");
        holder.txtDateTimeText.setText(DateHelper.dateFormat(entity.getVisitDate(), "MMM dd,yyyy", "yyyy-MM-dd") + "  " + entity.getStartTime() + " to " + entity.getEndTime());


       /* switch (entity.getType()) {
            case AppConstants.ADD_DETAIL:
                holder.btnDetails.setVisibility(View.GONE);
                holder.btnReport.setVisibility(View.GONE);
                holder.btnSeperatorLine.setVisibility(View.GONE);
                holder.btnAddDetail.setVisibility(View.VISIBLE);
                break;
            case AppConstants.DETAILS:
                holder.btnDetails.setVisibility(View.VISIBLE);
                holder.btnReport.setVisibility(View.VISIBLE);
                holder.btnSeperatorLine.setVisibility(View.VISIBLE);
                holder.btnDetails.setBackground(getDrawable(R.drawable.button_red, context));
                holder.btnReport.setBackground(getDrawable(R.drawable.button_gray, context));
                holder.btnAddDetail.setVisibility(View.GONE);
                holder.btnReport.setEnabled(false);
                break;
            case AppConstants.REPORT:
                holder.btnDetails.setVisibility(View.VISIBLE);
                holder.btnReport.setVisibility(View.VISIBLE);
                holder.btnSeperatorLine.setVisibility(View.VISIBLE);
                holder.btnDetails.setBackground(getDrawable(R.drawable.button_gray, context));
                holder.btnReport.setBackground(getDrawable(R.drawable.button_red, context));
                holder.btnDetails.setEnabled(false);
                holder.btnAddDetail.setVisibility(View.GONE);
                break;
        }*/
    }

    private Drawable getDrawable(int resID, Context context) {
        return context.getResources().getDrawable(resID);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClicked(v.getTag(R.integer.key_object), (int) v.getTag(R.integer.key_position), v.getId());
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtCustomerNoText)
        AnyTextView txtCustomerNoText;
        @BindView(R.id.txtDate)
        AnyTextView txtDateTimeText;
        @BindView(R.id.btnAddDetail)
        Button btnAddDetail;
        @BindView(R.id.btnReport)
        Button btnReport;
        @BindView(R.id.btnDetails)
        Button btnDetails;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
