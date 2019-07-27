package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.activities.DockActivity;
import com.ingic.ezhalbatek.technician.activities.MainActivity;
import com.ingic.ezhalbatek.technician.entities.HashMapEnt;
import com.ingic.ezhalbatek.technician.entities.Items;
import com.ingic.ezhalbatek.technician.entities.MasterUserEnt;
import com.ingic.ezhalbatek.technician.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdditionalTaskChildBinder extends RecyclerViewBinder<Items> {
    private RecyclerItemListener itemListener;
    private DockActivity context;
    private BasePreferenceHelper preferenceHelper;
    private MainActivity mainActivity;

    public AdditionalTaskChildBinder(DockActivity context, BasePreferenceHelper preferenceHelper, MainActivity mainActivity) {
        super(R.layout.row_item_additional_task);
        this.context = context;
        this.preferenceHelper = preferenceHelper;
        this.mainActivity = mainActivity;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(Items entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;

        holder.txtInventry.setText(entity.getItemMapping().getQuantity() + "");
        holder.chkStatus.setText(entity.getName() + "");

        ArrayList<String> quanitityList = new ArrayList();
        ArrayAdapter<String> quanitityAdapter;

        for (int i = 1; i <= entity.getItemMapping().getQuantity(); i++) {
            quanitityList.add(i + "");
        }

        quanitityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, quanitityList);
        quanitityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spQuantity.setAdapter(quanitityAdapter);
        quanitityAdapter.notifyDataSetChanged();


        for (Map.Entry<String, HashMapEnt> item : mainActivity.getAdditionalJobsHash().entrySet()) {
            if (item.getKey().equals(entity.getId() + "")) {
                holder.chkStatus.setChecked(true);
                holder.spQuantity.setSelection(Integer.parseInt(item.getValue().getQuantity()) - 1);
            }

        }


        holder.spQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (holder.chkStatus.isChecked()) {
                    mainActivity.getAdditionalJobsHash().put(entity.getId() + "", new HashMapEnt(entity.getName(), quanitityList.get(holder.spQuantity.getSelectedItemPosition()), holder.chkStatus.isChecked()));
                } else {
                    mainActivity.getAdditionalJobsHash().remove(entity.getId() + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.chkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (quanitityList != null && quanitityList.size() > 0) {
                    if (isChecked) {
                        mainActivity.getAdditionalJobsHash().put(entity.getId() + "", new HashMapEnt(entity.getName(), quanitityList.get(holder.spQuantity.getSelectedItemPosition()), isChecked));
                    } else {
                        mainActivity.getAdditionalJobsHash().remove(entity.getId() + "");
                    }
                }

            }
        });


    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.chkStatus)
        CheckBox chkStatus;
        @BindView(R.id.sp_quantity)
        Spinner spQuantity;
        @BindView(R.id.txt_inventry)
        TextView txtInventry;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


