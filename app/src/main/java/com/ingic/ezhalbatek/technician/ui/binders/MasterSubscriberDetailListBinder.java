package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.MasterSubscriptionEnt;
import com.ingic.ezhalbatek.technician.entities.MasterTaskEnt;
import com.ingic.ezhalbatek.technician.entities.MasterUserEnt;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/30/18.
 */
public class MasterSubscriberDetailListBinder extends RecyclerViewBinder<MasterSubscriptionEnt> {
    private RecyclerItemListener itemListener;
    private String visitType;

    public MasterSubscriberDetailListBinder(RecyclerItemListener itemListener, String visitType) {
        super(R.layout.row_item_master_list);
        this.itemListener = itemListener;
        this.visitType = visitType;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(MasterSubscriptionEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (entity != null) {
            holder.txtCustomerNo.setText(entity.getId() != null ? entity.getId() + "" : "-");
            holder.txtCustomerName.setText(entity.getFullName() != null ? entity.getFullName() : "-");
            if (entity.getFullAddress() != null && !entity.getFullAddress().equals("")) {
                holder.txtAddress.setText(entity.getFullAddress() + "");
            } else {
                holder.txtAddress.setText("  -  ");
            }

            String Date = entity.getUserVisits().get(entity.getUserVisits().size() - 1).getVisitDate();
            holder.txtVisitType.setText(DateHelper.dateFormat(Date, "dd MMM,yyyy", "yyyy-MM-dd"));
            holder.txtAddress.setSelected(true);

        }

        holder.mainframe.setOnClickListener(v -> {
            if (itemListener != null) {
                itemListener.onItemClicked(entity, position, v.getId());
            }
        });
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtCustomerNo)
        AnyTextView txtCustomerNo;
        @BindView(R.id.btnViewDetail)
        AnyTextView btnViewDetail;
        @BindView(R.id.txtCustomerName)
        AnyTextView txtCustomerName;
        @BindView(R.id.txtAddress)
        AnyTextView txtAddress;
        @BindView(R.id.txtVisitType)
        AnyTextView txtVisitType;
        @BindView(R.id.mainframe)
        LinearLayout mainframe;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
