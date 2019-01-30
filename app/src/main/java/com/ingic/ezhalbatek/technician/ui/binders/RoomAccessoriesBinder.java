package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AccessoriesDataEnt;
import com.ingic.ezhalbatek.technician.entities.Accessory;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/27/18.
 */
public class RoomAccessoriesBinder extends RecyclerViewBinder<Accessory> {

    private int quantity=0;

    public RoomAccessoriesBinder() {
        super(R.layout.row_item_accessory);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(Accessory entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (entity != null ) {
            holder.txtItem.setText(entity.getName() + "");

            quantity=0;
            for(AccessoriesDataEnt item:entity.getRoomAccessories()){
                quantity=quantity+Integer.parseInt(item.getQuantity());
            }
            holder.txtQuantity.setText(quantity + "");
        }
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtItem)
        AnyTextView txtItem;
        @BindView(R.id.txtQuantity)
        AnyTextView txtQuantity;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
