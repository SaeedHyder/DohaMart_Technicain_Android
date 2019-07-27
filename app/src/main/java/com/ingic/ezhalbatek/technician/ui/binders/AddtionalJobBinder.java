package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdditionalJob;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/19/18.
 */
public class AddtionalJobBinder extends RecyclerViewBinder<AdditionalJob> {
    private Double amount;

    public AddtionalJobBinder() {
        super(R.layout.row_item_additional_job);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(AdditionalJob entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtJobselectedtext.setText(entity.getItem().getName() + "");
        holder.txtQuantity.setText(entity.getQuantity() + "");
        amount=0.0;
        amount = Double.valueOf(entity.getItem().getAmount());
        holder.txtJobselectedAmount.setText(context.getResources().getString(R.string.qar)+" " + amount + "");
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
