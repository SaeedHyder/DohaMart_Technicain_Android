package com.ingic.ezhalbatek.technician.fragments.customerlist;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdditionalJob;
import com.ingic.ezhalbatek.technician.entities.ServicsList;
import com.ingic.ezhalbatek.technician.entities.UserRequest;
import com.ingic.ezhalbatek.technician.entities.UserVisit;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.ui.binders.ReportAdditionalTaskBinder;
import com.ingic.ezhalbatek.technician.ui.binders.ServiceListMasterBinder;
import com.ingic.ezhalbatek.technician.ui.binders.ServicesListBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 6/30/18.
 */
public class TechnicianDetailReportFragment extends BaseFragment {
    public static final String TAG = "TechnicianDetailReportFragment";
    @BindView(R.id.txtCustomerName)
    AnyTextView txtCustomerName;

    @BindView(R.id.ll_additional_jobs)
    LinearLayout llAdditionalJobs;

    @BindView(R.id.txtDate)
    AnyTextView txtDate;

    @BindView(R.id.txtTime)
    AnyTextView txtTime;

    @BindView(R.id.txtSubscriptionPackage)
    AnyTextView txtSubscriptionPackage;
    @BindView(R.id.rvAddtionalJobs)
    CustomRecyclerView rvAddtionalJobs;
    @BindView(R.id.view)
    View viewLine;

    Unbinder unbinder;
    @BindView(R.id.rvJobs)
    CustomRecyclerView rvJobs;
    @BindView(R.id.ll_jobs)
    LinearLayout llJobs;

    private ArrayList<AdditionalJob> addedJobCollection;
    private static String suscriberKey = "suscriberKey";
    private String suscriberJsonString;
    private static String userKey = "userKey";
    private static String isSubscriberKey = "isSubscriberKey";
    private static String SubUserName;
    private static String SubTitle;
    private static String TaskUserName;
    private String userJsonString;
    private boolean isSubscriber;
    private UserVisit subscriptionEntity;
    private UserRequest userDataEntity;
    private ArrayList<ServicsList> servicesCollection ;

    public static TechnicianDetailReportFragment newInstance() {
        Bundle args = new Bundle();

        TechnicianDetailReportFragment fragment = new TechnicianDetailReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TechnicianDetailReportFragment newInstance(UserVisit subscriberEnt, boolean isSubscriber, String name, String Title) {
        Bundle args = new Bundle();
        args.putString(suscriberKey, new Gson().toJson(subscriberEnt));
        args.putBoolean(isSubscriberKey, isSubscriber);
        SubUserName = name;
        SubTitle = Title;
        TechnicianDetailReportFragment fragment = new TechnicianDetailReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TechnicianDetailReportFragment newInstance(UserRequest userDataEnt, boolean isSubscriber, String username) {
        Bundle args = new Bundle();
        args.putString(userKey, new Gson().toJson(userDataEnt));
        args.putBoolean(isSubscriberKey, isSubscriber);
        TaskUserName = username;
        TechnicianDetailReportFragment fragment = new TechnicianDetailReportFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            suscriberJsonString = getArguments().getString(suscriberKey);
            userJsonString = getArguments().getString(userKey);
            isSubscriber = getArguments().getBoolean(isSubscriberKey);
        }
        if (suscriberJsonString != null && !suscriberJsonString.equals("")) {
            subscriptionEntity = new Gson().fromJson(suscriberJsonString, UserVisit.class);
        }
        if (userJsonString != null && !userJsonString.equals("")) {
            userDataEntity = new Gson().fromJson(userJsonString, UserRequest.class);
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
        View view = inflater.inflate(R.layout.fragment_technician_visit_detail_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        addedJobCollection = new ArrayList<>();
        if (isSubscriber && subscriptionEntity != null) {
            llJobs.setVisibility(View.GONE);
            txtCustomerName.setText(SubUserName != null ? SubUserName : "-");
            txtSubscriptionPackage.setText(SubTitle != null ? SubTitle : "-");
            txtDate.setText(DateHelper.dateFormat(subscriptionEntity.getVisitDate(), "MMMM yyyy", "yyyy-MM-dd"));
            txtTime.setText(subscriptionEntity.getStartTime());
           // txtTime.setText(DateHelper.dateFormat(subscriptionEntity.getVisitDate(), "hh:mm a", "yyyy-MM-dd"));

            if (subscriptionEntity.getAdditionalJobs().size() > 0) {
                llAdditionalJobs.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);

                for (AdditionalJob item : subscriptionEntity.getAdditionalJobs()) {
                    if (item.getStatus() == 2) {
                        addedJobCollection.add(item);
                    }
                }

            }

            if (addedJobCollection.size() <= 0) {
                llAdditionalJobs.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
            }
            rvAddtionalJobs.bindRecyclerView(new ReportAdditionalTaskBinder(), addedJobCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

        } else if (userDataEntity != null) {
            llJobs.setVisibility(View.VISIBLE);
            servicesCollection=new ArrayList<>();
            txtCustomerName.setText(TaskUserName != null ? TaskUserName : "-");
            txtDate.setText(DateHelper.dateFormat(userDataEntity.getDate(), "MMMM yyyy", "yyyy-MM-dd"));
            txtTime.setText(DateHelper.dateFormat(userDataEntity.getCreatedAt(), "hh:mm a", "yyyy-MM-dd hh:mm:ss"));
            txtSubscriptionPackage.setText(R.string.task_title);

            if (userDataEntity.getAdditionalJobs().size() > 0) {
                llAdditionalJobs.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);

                for (AdditionalJob item : userDataEntity.getAdditionalJobs()) {
                    if (item.getStatus() == 2) {
                        addedJobCollection.add(item);
                    }
                }
            }
            if (addedJobCollection.size() <= 0) {
                llAdditionalJobs.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
            }
            for (ServicsList item : userDataEntity.getServicsList()) {
                servicesCollection.add(item);
            }
            rvAddtionalJobs.bindRecyclerView(new ReportAdditionalTaskBinder(), addedJobCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
            rvJobs.bindRecyclerView(new ServiceListMasterBinder(), servicesCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        }

    }


}