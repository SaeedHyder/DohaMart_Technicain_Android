package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/6/18.
 */
public class RegisteredUserTaskBinder extends RecyclerViewBinder<UserPaymentEnt> {
    private RecyclerItemListener itemClickListener;

    public RegisteredUserTaskBinder(RecyclerItemListener itemClickListener) {
        super(R.layout.row_item_registered_user_task);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(UserPaymentEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtRequestID.setText(entity.getId()+"");
        holder.txtCustomerName.setText(entity.getUserDetail().getFullName()+"");
        holder.txtDateTime.setText(DateHelper.dateFormat(entity.getDate(), "dd MMM,yyyy", "yyyy-MM-dd"));



        holder.btnViewDetail.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(entity, position, v.getId());
            }
        });
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtRequestID)
        AnyTextView txtRequestID;
        @BindView(R.id.txtCustomerName)
        AnyTextView txtCustomerName;
        @BindView(R.id.txtDate)
        AnyTextView txtDateTime;
        @BindView(R.id.btnViewDetail)
        Button btnViewDetail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
