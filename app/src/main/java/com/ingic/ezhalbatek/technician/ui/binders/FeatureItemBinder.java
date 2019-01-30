package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/21/18.
 */
public class FeatureItemBinder extends RecyclerViewBinder<String>{
    public FeatureItemBinder() {
        super(R.layout.row_item_feature);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(String entity, int position, Object viewHolder, Context context) {
        ((ViewHolder) viewHolder).txtItemName.setText(entity);
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtItemName)
        AnyTextView txtItemName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
