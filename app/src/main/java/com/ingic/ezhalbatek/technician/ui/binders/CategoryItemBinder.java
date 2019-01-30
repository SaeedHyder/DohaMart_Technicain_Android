package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.CategoryEnt;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/23/18.
 */
public class CategoryItemBinder extends RecyclerViewBinder<CategoryEnt> {

    private RecyclerItemListener itemListener;

    public CategoryItemBinder(RecyclerItemListener itemListener) {
        super(R.layout.row_item_category);
        this.itemListener=itemListener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final CategoryEnt entity, final int position, Object viewHolder, Context context) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.imgItemImage.setImageResource(entity.getResID());
        holder.txtItemTitle.setText(entity.getTitle());
        if (itemListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemListener.onItemClicked(entity, position, holder.itemView.getId());
                }
            });
        }

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.imgItemImage)
        ImageView imgItemImage;
        @BindView(R.id.txtItemTitle)
        AnyTextView txtItemTitle;
        @BindView(R.id.ll_profile)
        RelativeLayout llProfile;
        @BindView(R.id.rlParent)
        CardView rlParent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
