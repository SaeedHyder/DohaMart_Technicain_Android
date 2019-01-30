package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdditionalJob;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.ui.binders.AddtionalJobBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by saeedhyder on 7/20/2018.
 */

/**
 * Created on 6/4/18.
 */
public class RequestJobReportFragment extends BaseFragment {
    public static final String TAG = "JobReportFragment";

    Unbinder unbinder;
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.txtServiceID)
    AnyTextView txtServiceID;
    @BindView(R.id.txtDate)
    AnyTextView txtDate;
    @BindView(R.id.txtVisitDate)
    AnyTextView txtVisitDate;
    @BindView(R.id.rvAddtionalJobs)
    CustomRecyclerView rvAddtionalJobs;
    @BindView(R.id.txtTechnicianName)
    AnyTextView txtTechnicianName;
    @BindView(R.id.txtTechnicianNumber)
    AnyTextView txtTechnicianNumber;
    @BindView(R.id.txtCustomerNo)
    AnyTextView txtCustomerNo;
    @BindView(R.id.txtCost)
    AnyTextView txtCost;
    @BindView(R.id.rbRating)
    CustomRatingBar rbRating;
    @BindView(R.id.btnDone)
    Button btnDone;

    private static String JobDetail = "JobDetail";
    private static String UserJobDetail = "JobDetail";
    private SubscriptionPaymentEnt entity;
    private UserPaymentEnt userEntity;
    private Double amount = 0.0;

    public static RequestJobReportFragment newInstance() {
        Bundle args = new Bundle();

        RequestJobReportFragment fragment = new RequestJobReportFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static RequestJobReportFragment newInstance(UserPaymentEnt userData) {
        Bundle args = new Bundle();
        args.putString(UserJobDetail, new Gson().toJson(userData));
        RequestJobReportFragment fragment = new RequestJobReportFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(JobDetail);
            String userJsonString = getArguments().getString(UserJobDetail);
            if (jsonString != null) {
                entity = new Gson().fromJson(jsonString, SubscriptionPaymentEnt.class);
            } else {
                entity = null;
            }
            if (userJsonString != null) {
                userEntity = new Gson().fromJson(jsonString, UserPaymentEnt.class);
            } else {
                userEntity = null;
            }
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.view_report));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<AdditionalJob> jobs = new ArrayList<>();
        jobs.addAll(entity.getAdditionalJobs());

        rvAddtionalJobs.bindRecyclerView(new AddtionalJobBinder(), jobs, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        if (entity != null) {
            for (AdditionalJob item : entity.getAdditionalJobs()) {
                amount = amount + Double.parseDouble(item.getItem().getAmount());
            }
        } else if (userEntity != null) {
            for (AdditionalJob item : userEntity.getAdditionalJobs()) {
                amount = amount + Double.parseDouble(item.getItem().getAmount());
            }
        }

        setData();

    }

    private void setData() {

        if (entity != null) {
            txtDuration.setText(DateHelper.dateFormat(entity.getVisitDate(), "MMMM yyyy", "yyyy-MM-dd HH:mm:ss"));
            txtServiceID.setText(DateHelper.dateFormat(entity.getVisitDate(), "dd/MM/yy", "yyyy-MM-dd HH:mm:ss"));
            txtTechnicianName.setText(entity.getUser().getFullAddress() + "");
            txtTechnicianNumber.setText(entity.getUser().getFullName() + "");
            txtCustomerNo.setText(entity.getUser().getPhoneNo() + "");
            txtCost.setText(entity.getSubscription().getCurrencyCode() + " " + amount + "");
            rbRating.setScore(1);
        } else if (userEntity != null) {
            txtDuration.setText(DateHelper.dateFormat(userEntity.getCreatedAt(), "MMMM yyyy", "yyyy-MM-dd HH:mm:ss"));
            txtServiceID.setText(DateHelper.dateFormat(userEntity.getCreatedAt(), "dd/MM/yy", "yyyy-MM-dd HH:mm:ss"));
            txtTechnicianName.setText(userEntity.getUserDetail().getFullAddress() + "");
            txtTechnicianNumber.setText(userEntity.getUserDetail().getFullName() + "");
            txtCustomerNo.setText(userEntity.getUserDetail().getPhoneNo() + "");
            txtCost.setText(userEntity.getCurrencyCode() + " " + amount + "");
            rbRating.setScore(1);
        }

    }


    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        getDockActivity().popFragment();
    }
}

