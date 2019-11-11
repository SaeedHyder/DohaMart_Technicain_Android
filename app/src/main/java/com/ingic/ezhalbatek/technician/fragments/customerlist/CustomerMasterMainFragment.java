package com.ingic.ezhalbatek.technician.fragments.customerlist;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CustomerMasterMainFragment extends BaseFragment {
    public static final String TAG = "CustomerMasterMainFragment";
    @BindView(R.id.btnSubscriber)
    AnyTextView btnSubscriber;
    @BindView(R.id.btnRegisted)
    AnyTextView btnRegisted;
    @BindView(R.id.fragmentContainer)
    LinearLayout fragmentContainer;
    Unbinder unbinder;

    public static CustomerMasterMainFragment newInstance() {
        Bundle args = new Bundle();

        CustomerMasterMainFragment fragment = new CustomerMasterMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_customermaster_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (prefHelper.isLanguageArabian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (prefHelper.isFromCustomerMasterSub()) {
            changeColorToSelected(btnSubscriber);
            changeColorToUnSelected(btnRegisted);
            replaceFragmentOnTab(CustomerSubscriberFragment.newInstance());

        } else {
            changeColorToSelected(btnRegisted);
            changeColorToUnSelected(btnSubscriber);
            replaceFragmentOnTab(CustomerTaskFragment.newInstance());

        }

    }

    private void changeColorToSelected(AnyTextView txtTitle) {
        txtTitle.setTextColor(getDockActivity().getResources().getColor(R.color.app_red));
    }

    private void changeColorToUnSelected(AnyTextView txtTitle) {
        txtTitle.setTextColor(getDockActivity().getResources().getColor(R.color.app_font_black));
    }

    private void replaceFragmentOnTab(BaseFragment frag) {
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.fragmentContainer, frag);
        transaction.commit();

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResString(R.string.customer_master_list));
        titleBar.showBackButton();
    }


    @OnClick({R.id.btnSubscriber, R.id.btnRegisted})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubscriber:
                changeColorToSelected(btnSubscriber);
                changeColorToUnSelected(btnRegisted);
                replaceFragmentOnTab(CustomerSubscriberFragment.newInstance());
                break;
            case R.id.btnRegisted:
                changeColorToSelected(btnRegisted);
                changeColorToUnSelected(btnSubscriber);
                replaceFragmentOnTab(CustomerTaskFragment.newInstance());
                break;
        }
    }
}
