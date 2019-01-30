package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserVisit;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/30/18.
 */
public class TechnicianVisitDetailsBinder extends RecyclerViewBinder<UserVisit> {
    private RecyclerItemListener itemListener;


    public TechnicianVisitDetailsBinder(RecyclerItemListener itemListener) {
        super(R.layout.row_item_technician_visit);
        this.itemListener = itemListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(UserVisit entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;


        holder.txtTechnicianName.setText(entity.getTechnician().getFullName()!=null?entity.getTechnician().getFullName():"-");
        holder.txtMaintenanceDate.setText(DateHelper.dateFormat(entity.getVisitDate(), "dd/MM/yy", "yyyy-MM-dd") + "  " + entity.getStartTime() + " to " + entity.getEndTime());


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
