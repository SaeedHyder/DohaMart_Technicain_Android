package com.ingic.ezhalbatek.technician.ui.binders;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.activities.DockActivity;
import com.ingic.ezhalbatek.technician.entities.AccessoriesDataEnt;
import com.ingic.ezhalbatek.technician.entities.Accessory;
import com.ingic.ezhalbatek.technician.entities.AccessoryItemDetailCustom;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.ExpandableListViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/28/18.
 */
public class AccessoriesExpendableBinder extends ExpandableListViewBinder<Accessory, AccessoryItemDetailCustom> {

    private int quantity = 0;
    private DockActivity dockActivity;
    private String visitId;

    public AccessoriesExpendableBinder(DockActivity dockActivity,String visitId) {
        super(R.layout.row_item_room_accessory_parent, R.layout.row_item_room_accessory_child);
        this.dockActivity=dockActivity;
        this.visitId=visitId;
    }

    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {
        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHolder createChildViewHolder(View view) {
        return new ChildViewHolder(view);
    }


    @Override
    public void bindGroupView(Accessory entity, int position, int grpPosition, View view, Activity activity, boolean isExpended) {
        ParentViewHolder holder = (ParentViewHolder) view.getTag();

        if(visitId.equals("")){
            holder.chkStatus.setVisibility(View.GONE);
        }

        if (isExpended) {
            holder.imgGroupIndicator.setRotation(180);
            holder.ll_mainFrame.setBackground(dockActivity.getResources().getDrawable(R.drawable.parent_item_border_2));
        } else {
            holder.imgGroupIndicator.setRotation(360);
            holder.ll_mainFrame.setBackground(dockActivity.getResources().getDrawable(R.drawable.additional_parent_item_border));
        }

        if (entity != null) {
            holder.txtItem.setText(entity.getName() + "");

            quantity=0;

            for (AccessoriesDataEnt item : entity.getRoomAccessories()) {
                quantity = quantity + Integer.parseInt(item.getQuantity());
            }
            holder.txtQuantity.setText(quantity + "");
        }

        holder.chkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                entity.setChecked(b);
            }
        });

    }

    @Override
    public void bindChildView(AccessoryItemDetailCustom entity, int position, int grpPosition, View view, Activity activity) {

        ChildViewHolder holder = (ChildViewHolder) view.getTag();

        if (entity != null) {
            holder.txtBrandName.setText(entity.getBrandName() + "");
            holder.txtType.setText(entity.getType() + "");
            holder.txtSubscriptionPackage.setText(entity.getModel() + "");
            holder.txtMobileNo.setText(entity.getQuantity() + "");
        }

    }

    static class ParentViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.txtItem)
        AnyTextView txtItem;
        @BindView(R.id.txtQuantity)
        AnyTextView txtQuantity;
        @BindView(R.id.chkStatus)
        CheckBox chkStatus;
        @BindView(R.id.imgGroupIndicator)
        ImageView imgGroupIndicator;
        @BindView(R.id.ll_mainFrame)
        LinearLayout ll_mainFrame;



        ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder extends BaseChildViewHolder {
        @BindView(R.id.txtBrandName)
        AnyTextView txtBrandName;
        @BindView(R.id.txtType)
        AnyTextView txtType;
        @BindView(R.id.txtSubscriptionPackage)
        AnyTextView txtSubscriptionPackage;
        @BindView(R.id.txtMobileNo)
        AnyTextView txtMobileNo;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
