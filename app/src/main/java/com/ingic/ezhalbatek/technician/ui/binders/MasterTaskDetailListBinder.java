package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.MasterTaskEnt;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MasterTaskDetailListBinder extends RecyclerViewBinder<MasterTaskEnt> {
    private RecyclerItemListener itemListener;
    private String visitType;

    public MasterTaskDetailListBinder(RecyclerItemListener itemListener, String visitType) {
        super(R.layout.row_item_master_list);
        this.itemListener = itemListener;
        this.visitType = visitType;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(MasterTaskEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (entity != null) {
            holder.txtCustomerNo.setText(entity.getId() != null ? entity.getId() + "" : "-");
            holder.txtCustomerName.setText(entity.getFullName() != null ? entity.getFullName() : "-");
            if(entity.getFullAddress() != null && !entity.getFullAddress().equals("")){
                holder.txtAddress.setText(entity.getFullAddress());
            }else{
                holder.txtAddress.setText("  -  ");
            }

            String date=entity.getUserRequests().get(entity.getUserRequests().size()-1).getDate();
            holder.txtVisitType.setText(DateHelper.dateFormat(date, "dd MMM,yyyy", "yyyy-MM-dd"));
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
