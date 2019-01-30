package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.TaskEnt;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/28/18.
 */
public class AddedTaskBinder extends RecyclerViewBinder<TaskEnt> {
    private RecyclerItemListener listener;

    public AddedTaskBinder(RecyclerItemListener listener) {
        super(R.layout.row_item_addtional_task);
        this.listener = listener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(TaskEnt entity, int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
       /* holder.txtAddedTask.setText(entity.getTaskTitle());
        holder.txtPrice.setText(entity.getCurrencyCode()+" "+entity.getTaskPrice());*/
        holder.imgDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClicked(entity, position, v.getId());
            }
        });
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtAddedTask)
        AnyTextView txtAddedTask;
        @BindView(R.id.txtPrice)
        AnyTextView txtPrice;
        @BindView(R.id.imgDelete)
        ImageView imgDelete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
