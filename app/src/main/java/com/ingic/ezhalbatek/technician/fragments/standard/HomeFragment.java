package com.ingic.ezhalbatek.technician.fragments.standard;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.fragments.customerlist.CustomerMasterMainFragment;
import com.ingic.ezhalbatek.technician.fragments.customerlist.CustomerSubscriberFragment;
import com.ingic.ezhalbatek.technician.fragments.orderhistory.OrderHistoryFragment;
import com.ingic.ezhalbatek.technician.fragments.payments.RecieptPaymentFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.AdditionalTaskFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.CalenderJobsFragment;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.GetRequestData;


public class HomeFragment extends BaseFragment {


    public static final String TAG = "HomeFragment";
    @BindView(R.id.imgProfileImage)
    ImageView imgProfileImage;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.txtEmail)
    AnyTextView txtEmail;
    @BindView(R.id.txtPhone)
    AnyTextView txtPhone;
    ImageLoader imageLoader;
    protected BroadcastReceiver broadcastReceiver;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getResString(R.string.home));
        Integer count = prefHelper.getNotificationCount();
        titleBar.showNotificationButton(count != null ? count : 0);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMainActivity().changeBackgroundResources(R.drawable.bg2);

        onNotificationReceived();
        setData();

    }

    private void setData() {

        if (prefHelper.getUser() != null) {
            if (prefHelper.getUser().getFullName() != null && !prefHelper.getUser().getFullName().equals("")) {
                txtName.setText(prefHelper.getUser().getFullName() + "");
            } else {
                txtName.setVisibility(View.GONE);
            }
            if (prefHelper.getUser().getEmail() != null && !prefHelper.getUser().getEmail().equals("")) {
                txtEmail.setText(prefHelper.getUser().getEmail() + "");
            } else {
                txtEmail.setVisibility(View.GONE);
            }

            if (prefHelper.getUser().getPhoneNo() != null && !prefHelper.getUser().getPhoneNo().equals("")) {
                if (prefHelper.getUser().getCountryCode() != null && !prefHelper.getUser().getCountryCode().equals("")) {
                    txtPhone.setText(prefHelper.getUser().getCountryCode() + "" + prefHelper.getUser().getPhoneNo() + "");
                } else {
                    txtPhone.setText(prefHelper.getUser().getPhoneNo() + "");
                }

            } else {
                txtPhone.setVisibility(View.GONE);
            }

           // Picasso.with(getDockActivity()).load(prefHelper.getUser().getProfileImage()).placeholder(R.drawable.placeholder).into(imgProfileImage);

             imageLoader.displayImage(prefHelper.getUser().getProfileImage(), imgProfileImage,getMainActivity().getImageLoaderRoundCornerTransformation(5));
        }
    }


    @OnClick({R.id.btnNewJobs, R.id.btnReceivePayments, R.id.btnHistory, R.id.btnCustomerMasterList})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNewJobs:
                // getDockActivity().replaceDockableFragment(AdditionalTaskFragment.newInstance(), AdditionalTaskFragment.TAG);
                getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), CalenderJobsFragment.TAG);
                break;
            case R.id.btnReceivePayments:
                prefHelper.setIsFromPaymentSubscriber(true);
                getDockActivity().replaceDockableFragment(RecieptPaymentFragment.newInstance(), RecieptPaymentFragment.TAG);
                break;
            case R.id.btnHistory:
                prefHelper.setIsFromHistorySubscriber(true);
                getDockActivity().replaceDockableFragment(OrderHistoryFragment.newInstance(), OrderHistoryFragment.TAG);

                break;
            case R.id.btnCustomerMasterList:
                prefHelper.setIsFromCustomerMasterSubscriber(true);
                getDockActivity().replaceDockableFragment(CustomerMasterMainFragment.newInstance(), CustomerMasterMainFragment.TAG);
                break;
        }
    }

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    System.out.println("registration complete");
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    Bundle bundle = intent.getExtras();
                    if (getTitleBar() != null)
                        getTitleBar().showNotificationButton(prefHelper.getNotificationCount());
                }
            }

        };
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().releaseDrawer();
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));
    }
}

