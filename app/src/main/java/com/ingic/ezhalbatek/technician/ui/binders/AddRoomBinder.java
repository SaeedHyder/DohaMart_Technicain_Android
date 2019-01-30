package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.activities.DockActivity;
import com.ingic.ezhalbatek.technician.entities.CreateRoomEnt;
import com.ingic.ezhalbatek.technician.entities.RoomStatus;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.interfaces.OnLongTap;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/25/18.
 */
public class AddRoomBinder extends RecyclerViewBinder<CreateRoomEnt> {
    private RecyclerItemListener itemListener;
    private DockActivity dockActivity;
    private OnLongTap onLongTap;
    private ArrayList<RoomStatus> roomStatus;

    public AddRoomBinder(RecyclerItemListener itemListener, DockActivity dockActivity, OnLongTap onLongTap, ArrayList<RoomStatus> data) {
        super(R.layout.row_item_room);
        this.itemListener = itemListener;
        this.dockActivity = dockActivity;
        this.onLongTap = onLongTap;
        this.roomStatus=data;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(CreateRoomEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.itemText.setText(entity.getName() + "");
        holder.itemText.setOnClickListener(v -> {
            if (itemListener != null) {
                itemListener.onItemClicked(entity, position, v.getId());
            }
        });


        if(roomStatus!=null && roomStatus.size()>0 &&  roomStatus.get(position).getStatus()==2){
            holder.itemText.setClickable(false);
            holder.itemText.setFocusable(false);
            holder.itemText.setEnabled(false);
            holder.itemText.setBackground(dockActivity.getResources().getDrawable(R.drawable.button_stock_black));
            holder.itemText.setTextColor(dockActivity.getResources().getColor(R.color.black));
            entity.setRoomSelected(true);
        }else{
            holder.itemText.setClickable(true);
            holder.itemText.setEnabled(true);
            holder.itemText.setFocusable(true);
            holder.itemText.setBackground(dockActivity.getResources().getDrawable(R.drawable.button_stroke_grey));
            holder.itemText.setTextColor(dockActivity.getResources().getColor(R.color.app_font_gray));
            entity.setRoomSelected(false);
        }
        holder.itemText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongTap.onClick(entity, position);
                return true;
            }
        });

      /*  if (entity.getStatus() != null && entity.getStatus().equals("2")) {
            //  if(entity.getStatus()!=null && entity.getStatus().equals("5")){
            holder.itemText.setClickable(false);
            holder.itemText.setFocusable(false);
            holder.itemText.setEnabled(false);
            holder.itemText.setBackground(dockActivity.getResources().getDrawable(R.drawable.button_stock_black));
            holder.itemText.setTextColor(dockActivity.getResources().getColor(R.color.black));
            entity.setRoomSelected(true);
        } else {
            holder.itemText.setClickable(true);
            holder.itemText.setEnabled(true);
            holder.itemText.setFocusable(true);
            holder.itemText.setBackground(dockActivity.getResources().getDrawable(R.drawable.button_stroke_grey));
            holder.itemText.setTextColor(dockActivity.getResources().getColor(R.color.app_font_gray));
            entity.setRoomSelected(false);
        }*/
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.itemText)
        Button itemText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
