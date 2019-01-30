package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.ServicsList;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/29/18.
 */
public class RegisteredUserJobDetailsBinder extends RecyclerViewBinder<ServicsList> {
    public RegisteredUserJobDetailsBinder() {
        super(R.layout.row_item_registered_user_job_details);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(ServicsList entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.txtItem.setText(entity.getServiceDetail().getTitle()+"");

        holder.chkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                entity.setChecked(b);
            }
        });
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtItem)
        AnyTextView txtItem;
        @BindView(R.id.chkStatus)
        CheckBox chkStatus;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
