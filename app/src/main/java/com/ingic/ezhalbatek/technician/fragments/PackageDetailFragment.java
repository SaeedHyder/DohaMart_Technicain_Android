package com.ingic.ezhalbatek.technician.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.fragments.standard.HomeFragment;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 5/22/18.
 */
public class PackageDetailFragment extends BaseFragment {
    public static final String TAG = "PackageDetailFragment";
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.txtSubscriptionFee)
    AnyTextView txtSubscriptionFee;
    @BindView(R.id.txtCustomerName)
    AnyTextView txtCustomerName;
    @BindView(R.id.txtSubscriberID)
    AnyTextView txtSubscriberID;
    @BindView(R.id.txtAddress)
    AnyTextView txtAddress;
    @BindView(R.id.txtPhone)
    AnyTextView txtPhone;
    @BindView(R.id.txtEmail)
    AnyTextView txtEmail;
    @BindView(R.id.btnProceedPayment)
    Button btnProceedPayment;
    Unbinder unbinder;
    @BindView(R.id.txtFeatures)
    AnyTextView txtFeatures;

    public static PackageDetailFragment newInstance() {
        Bundle args = new Bundle();

        PackageDetailFragment fragment = new PackageDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_package_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.package_detail));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnProceedPayment)
    public void onViewClicked() {
        dialogHelper.showCommonDialog(v -> {
            prefHelper.setLoginStatus(true);
            dialogHelper.hideDialog();
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
        }, R.string.empty, R.string.thankyou, R.string.ok, R.string.empty, false, false);
        dialogHelper.setCancelable(false);
        dialogHelper.showDialog();

    }
}