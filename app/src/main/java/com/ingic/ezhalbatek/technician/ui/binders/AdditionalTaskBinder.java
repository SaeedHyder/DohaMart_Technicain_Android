package com.ingic.ezhalbatek.technician.ui.binders;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.activities.DockActivity;
import com.ingic.ezhalbatek.technician.activities.MainActivity;
import com.ingic.ezhalbatek.technician.entities.ItemMapping;
import com.ingic.ezhalbatek.technician.entities.Items;
import com.ingic.ezhalbatek.technician.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.technician.ui.viewbinders.abstracts.ExpandableListViewBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 6/6/2017.
 */

public class AdditionalTaskBinder extends ExpandableListViewBinder<String, ArrayList<Items>> {

    DockActivity context;
    private BasePreferenceHelper preferenceHelper;
    private MainActivity mainActivity;

    public AdditionalTaskBinder(DockActivity context, BasePreferenceHelper prefHelper, MainActivity mainActivity) {

        super(R.layout.additional_task_parent, R.layout.additional_task_child);
        this.context = context;
        this.preferenceHelper = prefHelper;
        this.mainActivity = mainActivity;

    }


    @Override
    public BaseGroupViewHolder createGroupViewHolder(View view) {
        return new parentViewHolder(view);
    }

    @Override
    public childViewHolder createChildViewHolder(View view) {

        return new childViewHolder(view);
    }


    @Override
    public void bindGroupView(String entity, int position, int grpPosition, View view, Activity activity, boolean isExpended) {

        parentViewHolder parentViewHolder = (parentViewHolder) view.getTag();

        if (isExpended) {
           // parentViewHolder.imgGroupIndicator.setRotation(180);
            parentViewHolder.txtHeader.setBackground(context.getResources().getDrawable(R.drawable.parent_item_border_2));
        } else {
           // parentViewHolder.imgGroupIndicator.setRotation(360);
            parentViewHolder.txtHeader.setBackground(context.getResources().getDrawable(R.drawable.additional_parent_item_border));
        }

        parentViewHolder.txtHeader.setText(entity);

    }


    @Override
    public void bindChildView(ArrayList<Items> entity, int position, int grpPosition, View view, Activity activity) {

        childViewHolder childViewHolder = (childViewHolder) view.getTag();

        childViewHolder.lvChild.bindRecyclerView(new AdditionalTaskChildBinder(context, preferenceHelper, mainActivity), entity, new LinearLayoutManager(context), new DefaultItemAnimator());


    }


    static class parentViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.txt_header)
        AnyTextView txtHeader;
        @BindView(R.id.imgGroupIndicator)
        ImageView imgGroupIndicator;

        parentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public static class childViewHolder extends BaseGroupViewHolder {
        @BindView(R.id.lv_child)
        CustomRecyclerView lvChild;

        childViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
