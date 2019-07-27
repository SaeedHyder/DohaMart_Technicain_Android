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
 * Created on 6/30/18.
 */
public class ReportAdditionalTaskBinder extends RecyclerViewBinder<AdditionalJob> {


    public ReportAdditionalTaskBinder() {
        super(R.layout.row_item_report_additional_task);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(AdditionalJob entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.txtTaskDetail.setText(entity.getItem().getName()+"");
        holder.txtQuantity.setText(context.getResources().getString(R.string.qty)+" "+entity.getQuantity()+"");
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtTaskDetail)
        AnyTextView txtTaskDetail;
        @BindView(R.id.txt_Quantity)
        AnyTextView txtQuantity;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
