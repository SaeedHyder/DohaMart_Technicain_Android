package com.ingic.ezhalbatek.technician.ui.binders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.technician.interfaces.onDeleteImage;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/23/2017.
 */

public class SelectedJobBinder extends RecyclerViewBinder<String> {
    BasePreferenceHelper preferenceHelper;
    private onDeleteImage onDeleteImage;

    public SelectedJobBinder(onDeleteImage onDeleteImage, BasePreferenceHelper preferenceHelper) {
        super(R.layout.row_item_selectedjobs);
        this.onDeleteImage = onDeleteImage;
        this.preferenceHelper = preferenceHelper;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new SelectedJobsViewHolder(view);
    }

    @Override
    public void bindView(String entity, final int position, Object viewHolder, Context context) {
        SelectedJobsViewHolder holder = (SelectedJobsViewHolder) viewHolder;
        holder.txtJobselectedtext.setText(entity);

        holder.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteImage.OnDeleteJobs(position);
            }
        });
    }


    public static class SelectedJobsViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_jobselectedtext)
        AnyTextView txtJobselectedtext;
        @BindView(R.id.delete_text)
        ImageView deleteText;

        SelectedJobsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
