package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserRequest;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/12/2018.
 */


public class TechnicianVisitUserBinder extends RecyclerViewBinder<UserRequest> {
    private RecyclerItemListener itemListener;

    public TechnicianVisitUserBinder(RecyclerItemListener itemListener) {
        super(R.layout.row_item_technician_visit);
        this.itemListener = itemListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(UserRequest entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (entity.getAssignTechnician() != null && entity.getAssignTechnician().getTechnicianDetails() != null &&
                entity.getAssignTechnician().getTechnicianDetails().getFirstName() != null && !entity.getAssignTechnician().getTechnicianDetails().getFirstName().equals("")) {
            holder.txtTechnicianName.setText(entity.getAssignTechnician().getTechnicianDetails().getFullName());
        } else {
            holder.txtTechnicianName.setText("-");
        }

        holder.txtMaintenanceDate.setText(DateHelper.dateFormat(entity.getDate(), "dd/MM/yy", "yyyy-MM-dd") + "  " + DateHelper.getLocalTime(entity.getDate()));

        holder.btnViewReport.setOnClickListener(view -> {
            if (itemListener != null) {
                itemListener.onItemClicked(entity, position, view.getId());
            }
        });
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtTechnicianName)
        AnyTextView txtTechnicianName;
        @BindView(R.id.txtMaintenanceDate)
        AnyTextView txtMaintenanceDate;
        @BindView(R.id.btnViewReport)
        Button btnViewReport;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


