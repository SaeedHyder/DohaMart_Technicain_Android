package com.ingic.ezhalbatek.technician.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.ui.binders.FeatureItemBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/21/18.
 */
public class ItemPackageDetailFragment extends BaseFragment {
    public static final String TAG = "ItemPackageDetailFragment";
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.rvFeatures)
    CustomRecyclerView rvFeatures;
    @BindView(R.id.txtPrice)
    AnyTextView txtPrice;
    Unbinder unbinder;
    private ArrayList<String> featureCollection;

    public static ItemPackageDetailFragment newInstance() {
        Bundle args = new Bundle();

        ItemPackageDetailFragment fragment = new ItemPackageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_package_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindRecyclerView();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void bindRecyclerView() {
        featureCollection = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            featureCollection.add("Unlimited Features");
        }
        rvFeatures.bindRecyclerView(new FeatureItemBinder(), featureCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
    }
}