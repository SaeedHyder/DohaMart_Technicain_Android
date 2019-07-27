package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.ServicsList;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ServicesListBinder extends RecyclerViewBinder<ServicsList> {
    private Double amount;

    public ServicesListBinder() {
        super(R.layout.row_item_service_job);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(ServicsList entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtJobselectedtext.setText(entity.getServiceDetail().getTitle()+ "");
        holder.txtQuantity.setText(context.getResources().getString(R.string.qty)+": " + entity.getQuantity() + "");

        holder.txtJobselectedAmount.setVisibility(View.GONE);
       /* amount = 0.0;
        amount = Double.valueOf(entity.getServiceDetail().getAmount());
        holder.txtJobselectedAmount.setText(context.getResources().getString(R.string.qar)+" " + amount + "");*/
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_jobselectedtext)
        AnyTextView txtJobselectedtext;
        @BindView(R.id.txt_Quantity)
        AnyTextView txtQuantity;
        @BindView(R.id.txt_jobselectedAmount)
        AnyTextView txtJobselectedAmount;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

