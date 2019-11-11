package com.ingic.ezhalbatek.technician.fragments.payments;

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
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.ui.binders.AddtionalJobBinder;
import com.ingic.ezhalbatek.technician.ui.binders.ServicesListBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 6/6/18.
 */
public class TaskDetailFragment extends BaseFragment {
    public static final String TAG = "TaskDetailFragment";
    @BindView(R.id.txtSubscriptionID)
    AnyTextView txtSubscriptionID;
    @BindView(R.id.txtCustomerName)
    AnyTextView txtCustomerName;
    @BindView(R.id.txtSubscriptionPackage)
    AnyTextView txtSubscriptionPackage;
    @BindView(R.id.rvAddtionalJobs)
    CustomRecyclerView rvAddtionalJobs;
    @BindView(R.id.txtTotalEarning)
    AnyTextView txtTotalEarning;
    @BindView(R.id.ll_additional_jobs)
    LinearLayout llAdditionalJobs;
    @BindView(R.id.requestTitle)
    AnyTextView requestTitle;
    @BindView(R.id.requestId)
    AnyTextView requestId;


    private static String suscriberKey = "suscriberKey";
    @BindView(R.id.txt_servicejobs)
    AnyTextView txtServicejobs;
    @BindView(R.id.rvServiceJobs)
    CustomRecyclerView rvServiceJobs;
    @BindView(R.id.txtExtraEarning)
    AnyTextView txtExtraEarning;
    @BindView(R.id.ll_extra_cost)
    LinearLayout llExtraCost;
    private String suscriberJsonString;
    private static String userKey = "userKey";
    private static String isSubscriberKey = "isSubscriberKey";
    private String userJsonString;
    private boolean isSubscriber;
    private SubscriptionPaymentEnt subscriptionEntity;
    private UserPaymentEnt userDataEntity;
    private ArrayList<AdditionalJob> addedJobCollection = new ArrayList<>();
    private ArrayList<ServicsList> servicesCollection = new ArrayList<>();
    private Double amount = 0.0;
    private String currencyCode;


    public static TaskDetailFragment newInstance(boolean isSubscriber) {
        Bundle args = new Bundle();

        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        fragment.setJobCompleted(isSubscriber);
        return fragment;
    }

    public static TaskDetailFragment newInstance(SubscriptionPaymentEnt subscriberEnt, boolean isSubscriber) {
        Bundle args = new Bundle();
        args.putString(suscriberKey, new Gson().toJson(subscriberEnt));
        args.putBoolean(isSubscriberKey, isSubscriber);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TaskDetailFragment newInstance(UserPaymentEnt userDataEnt, boolean isSubscriber) {
        Bundle args = new Bundle();
        args.putString(userKey, new Gson().toJson(userDataEnt));
        args.putBoolean(isSubscriberKey, isSubscriber);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setJobCompleted(boolean issubscriber) {
        isSubscriber = issubscriber;
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
            subscriptionEntity = new Gson().fromJson(suscriberJsonString, SubscriptionPaymentEnt.class);
        }
        if (userJsonString != null && !userJsonString.equals("")) {
            userDataEntity = new Gson().fromJson(userJsonString, UserPaymentEnt.class);
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.payments));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        ButterKnife.bind(this, view);
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

        addedJobCollection = new ArrayList<>();
        servicesCollection = new ArrayList<>();
        amount = 0.0;
        if (isSubscriber && subscriptionEntity != null) {

            txtCustomerName.setText(subscriptionEntity.getUser().getFullName() + "");
            txtSubscriptionID.setText(subscriptionEntity.getSubscription().getId() + "");
            txtSubscriptionPackage.setText(subscriptionEntity.getSubscription().getTitle() + "");

            txtServicejobs.setVisibility(View.GONE);
            rvServiceJobs.setVisibility(View.GONE);

            for (AdditionalJob item : subscriptionEntity.getAdditionalJobs()) {
                if (item.getStatus() == 2) {
                    addedJobCollection.add(item);
                    amount = amount + Double.valueOf(item.getItem().getAmount()) * item.getQuantity();
                }
            }

            if (addedJobCollection.size() > 0) {
                llAdditionalJobs.setVisibility(View.VISIBLE);
            } else {
                llAdditionalJobs.setVisibility(View.GONE);
            }

            txtTotalEarning.setText(getDockActivity().getResources().getString(R.string.qar) + " " + amount);

        } else if (userDataEntity != null) {

            llExtraCost.setVisibility(View.GONE);
            requestTitle.setText(R.string.request_title);
            requestId.setText(R.string.request_id);

            txtCustomerName.setText(userDataEntity.getUserDetail().getFullName() + "");
            txtSubscriptionID.setText(userDataEntity.getId() + "");
            txtSubscriptionPackage.setText(userDataEntity.getJobTitle());

            double totalAmount = userDataEntity.getTotalAmount() + userDataEntity.getUrgentCost();
            txtTotalEarning.setText(getDockActivity().getResources().getString(R.string.qar) + " " + totalAmount + "");
            txtExtraEarning.setText(userDataEntity.getUrgentCost() != null ? getDockActivity().getResources().getString(R.string.qar) + " " + String.format("%.2f", userDataEntity.getUrgentCost()) + "" : "-");

            for (ServicsList item : userDataEntity.getServicsList()) {
                servicesCollection.add(item);
            }

            //  currencyCode=userDataEntity.getAdditionalJobs().get(0).getCurrencyCode();
            currencyCode = "QAR";
            for (AdditionalJob item : userDataEntity.getAdditionalJobs()) {
                if (item.getStatus() == 2) {
                    addedJobCollection.add(item);
                    //   amount=amount+Double.parseDouble(item.getItem().getAmount());
                    amount = amount + Double.valueOf(item.getItem().getAmount()) * item.getQuantity();
                }
            }

            if (addedJobCollection.size() > 0) {
                llAdditionalJobs.setVisibility(View.VISIBLE);
            } else {
                llAdditionalJobs.setVisibility(View.GONE);
            }
        }

        if (addedJobCollection != null && addedJobCollection.size() > 0)
            rvAddtionalJobs.bindRecyclerView(new AddtionalJobBinder(), addedJobCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        if (servicesCollection != null && servicesCollection.size() > 0)
            rvServiceJobs.bindRecyclerView(new ServicesListBinder(), servicesCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());


        // txtTotalEarning.setText(currencyCode + " " + amount + "");

    }


}