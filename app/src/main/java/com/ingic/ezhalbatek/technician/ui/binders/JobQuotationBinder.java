package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdditionalJob;
import com.ingic.ezhalbatek.technician.entities.TaskEnt;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/28/18.
 */
public class JobQuotationBinder extends RecyclerViewBinder<TaskEnt> {
    public JobQuotationBinder() {
        super(R.layout.row_item_quotation);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(TaskEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtJobselectedtext.setText("Change "+entity.getName()+"");


     /*   if (position==2){
            holder.txtPrice.setText("QAR 500.00");
            holder.txtJobselectedtext.setText("Total");
            holder.txtPrice.setTypeface(Typeface.DEFAULT_BOLD);
            holder.txtJobselectedtext.setTextColor(context.getResources().getColor(R.color.app_font_black));
            holder.txtPrice.setTextColor(context.getResources().getColor(R.color.black));
        }*/
    }

    static class ViewHolder  extends BaseViewHolder{
        @BindView(R.id.txt_jobselectedtext)
        AnyTextView txtJobselectedtext;
        @BindView(R.id.txtPrice)
        AnyTextView txtPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
