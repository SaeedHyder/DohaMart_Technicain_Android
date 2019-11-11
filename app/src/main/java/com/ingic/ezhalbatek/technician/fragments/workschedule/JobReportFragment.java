package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdditionalJob;
import com.ingic.ezhalbatek.technician.entities.ServicsList;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.global.WebServiceConstants;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.ui.binders.AddtionalJobBinder;
import com.ingic.ezhalbatek.technician.ui.binders.ServicesListBinder;
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
 * Created on 6/4/18.
 */
public class JobReportFragment extends BaseFragment {
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
    @BindView(R.id.ll_additional_job)
    LinearLayout llAdditionalJob;
    @BindView(R.id.txtTotalEarning)
    AnyTextView txtTotalEarning;
    @BindView(R.id.ll_total_amount)
    LinearLayout llTotalAmount;
    @BindView(R.id.view_total)
    View viewTotal;
    @BindView(R.id.txtStatus)
    AnyTextView txtStatus;
    @BindView(R.id.txtExtraEarning)
    AnyTextView txtExtraEarning;
    @BindView(R.id.txtTitleCost)
    AnyTextView txtTitleCost;
    @BindView(R.id.ll_extra_cost)
    LinearLayout llExtraCost;
    @BindView(R.id.rvJobs)
    CustomRecyclerView rvJobs;
    @BindView(R.id.ll_job)
    LinearLayout llJob;
    @BindView(R.id.ll_mainFrame)
    LinearLayout llMainFrame;
    private SubscriptionPaymentEnt entityVisit;
    private UserPaymentEnt requestEntity;
    private Double amount = 0.0;
    private static String JobDetail = "JobDetail";
    private static String UserJobDetail = "UserJobDetail";
    private static String ActionId;
    private static String ActionType;
    private ArrayList<ServicsList> servicesCollection = new ArrayList<>();

    public static JobReportFragment newInstance() {
        Bundle args = new Bundle();
        JobReportFragment fragment = new JobReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JobReportFragment newInstance(String id, String type) {
        Bundle args = new Bundle();
        ActionId = id;
        ActionType = type;
        JobReportFragment fragment = new JobReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JobReportFragment newInstance(SubscriptionPaymentEnt data) {
        Bundle args = new Bundle();
        args.putString(JobDetail, new Gson().toJson(data));
        JobReportFragment fragment = new JobReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JobReportFragment newInstance(UserPaymentEnt userData) {
        Bundle args = new Bundle();
        args.putString(UserJobDetail, new Gson().toJson(userData));
        JobReportFragment fragment = new JobReportFragment();
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
                entityVisit = new Gson().fromJson(jsonString, SubscriptionPaymentEnt.class);
            } else {
                entityVisit = null;
            }
            if (userJsonString != null) {
                requestEntity = new Gson().fromJson(userJsonString, UserPaymentEnt.class);
            } else {
                requestEntity = null;
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


        if (prefHelper.isLanguageArabian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (entityVisit != null) {
            setVisitData(entityVisit);

        } else if (requestEntity != null) {
            setRequestData(requestEntity);
        }

        if (ActionId != null && ActionType != null) {
            llMainFrame.setVisibility(View.GONE);
            if (ActionType.equals(AppConstants.JobAcknowledge)) {
                serviceHelper.enqueueCall(webService.getRequestData(prefHelper.getUser().getId(), ActionId + ""), WebServiceConstants.RequestJobDetail);
            } else {
                serviceHelper.enqueueCall(webService.getRoomDetail(ActionId + ""), WebServiceConstants.VisitDetail);
            }
        }

    }

    private void setRequestData(UserPaymentEnt userEntity) {

        llJob.setVisibility(View.VISIBLE);
        ArrayList<AdditionalJob> jobs;
        amount = 0.0;

        jobs = new ArrayList<>();
        servicesCollection = new ArrayList<>();

        for (ServicsList item : userEntity.getServicsList()) {
            servicesCollection.add(item);
        }

        if (userEntity.getAdditionalJobs().size() > 0) {
            for (AdditionalJob item : userEntity.getAdditionalJobs()) {
                if (item.getStatus() == 2) {
                    jobs.add(item);
                    amount = amount + Double.valueOf(item.getItem().getAmount()) * item.getQuantity();
                }
            }
        }
        if (jobs.size() > 0) {
            llAdditionalJob.setVisibility(View.VISIBLE);
            viewTotal.setVisibility(View.VISIBLE);
            rvAddtionalJobs.bindRecyclerView(new AddtionalJobBinder(), jobs, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            llAdditionalJob.setVisibility(View.GONE);
            llTotalAmount.setVisibility(View.GONE);
            viewTotal.setVisibility(View.GONE);
        }

        if (servicesCollection.size() > 0) {
            viewTotal.setVisibility(View.VISIBLE);
            rvJobs.bindRecyclerView(new ServicesListBinder(), servicesCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
        } else {
            llJob.setVisibility(View.GONE);
        }


        if (userEntity.getStatus().equals("1") || userEntity.getStatus().equals("2")) {
            txtStatus.setText(AppConstants.Technician_Assigned);
        } else if (userEntity.getStatus().equals("0")) {
            txtStatus.setText(AppConstants.Technician_Not_Assigned);
        } else if (userEntity.getStatus().equals("4")) {
            txtStatus.setText(AppConstants.Cancelled);
        } else {
            txtStatus.setText(AppConstants.Completed);
        }

        setRequestViewsData(userEntity);

    }

    private void setRequestViewsData(UserPaymentEnt userEntity) {

        llExtraCost.setVisibility(View.GONE);
        txtDate.setText(userEntity.getJobTitle() + "");
        txtVisitDate.setText(getDockActivity().getResources().getString(R.string.task_title));
        txtDuration.setText(DateHelper.dateFormat(userEntity.getDate(), "MMMM yyyy", "yyyy-MM-dd"));
        txtServiceID.setText(DateHelper.dateFormat(userEntity.getDate(), "dd MMM,yyyy", "yyyy-MM-dd"));
        txtTechnicianName.setText(userEntity.getUserDetail().getFullAddress() + "");
        txtTechnicianNumber.setText(userEntity.getUserDetail().getFullName() + "");
        txtCustomerNo.setText(userEntity.getUserDetail().getId() + "");
        //  txtCost.setText("QAR " + amount + "");
        //  txtTotalEarning.setText("QAR" + " " + amount + "");

        double totalAmount = userEntity.getTotalAmount() + userEntity.getUrgentCost();
        txtTotalEarning.setText(getDockActivity().getResources().getString(R.string.qar)+" " + totalAmount + "");
        txtCost.setText(getDockActivity().getResources().getString(R.string.qar)+" " + totalAmount + "");
        txtExtraEarning.setText(userEntity.getUrgentCost() != null ? getDockActivity().getResources().getString(R.string.qar)+" " + userEntity.getUrgentCost() + "" : "-");

        if (userEntity.getFeedback() != null) {
            rbRating.setScore(Float.parseFloat(userEntity.getFeedback().getRate()));
        } else {
            rbRating.setScore(0);
        }
    }

    private void setVisitData(SubscriptionPaymentEnt entity) {

        llJob.setVisibility(View.GONE);
        ArrayList<AdditionalJob> jobs;
        amount = 0.0;

        jobs = new ArrayList<>();
        if (entity.getAdditionalJobs().size() > 0) {
            for (AdditionalJob item : entity.getAdditionalJobs()) {
                if (item.getStatus() == 2) {
                    jobs.add(item);
                    amount = amount + Double.valueOf(item.getItem().getAmount()) * item.getQuantity();
                }
            }

        }

        if (jobs.size() > 0) {
            llAdditionalJob.setVisibility(View.VISIBLE);
            viewTotal.setVisibility(View.VISIBLE);
            rvAddtionalJobs.bindRecyclerView(new AddtionalJobBinder(), jobs, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            llAdditionalJob.setVisibility(View.GONE);
            llTotalAmount.setVisibility(View.GONE);
            viewTotal.setVisibility(View.GONE);
        }

        if (entity.getStatus().equals("1") || entity.getStatus().equals("2")) {
            txtStatus.setText(AppConstants.Technician_Assigned);
        } else if (entity.getStatus().equals("0")) {
            txtStatus.setText(AppConstants.Technician_Not_Assigned);
        } else if (entity.getStatus().equals("4")) {
            txtStatus.setText(AppConstants.Cancelled);
        } else {
            txtStatus.setText(AppConstants.Completed);
        }

        setVisitViewsData(entity);
    }

    private void setVisitViewsData(SubscriptionPaymentEnt entity) {

        txtDuration.setText(DateHelper.dateFormat(entity.getVisitDate(), "MMMM yyyy", "yyyy-MM-dd"));
        txtServiceID.setText(DateHelper.dateFormat(entity.getVisitDate(), "dd MMM,yyyy", "yyyy-MM-dd"));
        txtTechnicianName.setText(entity.getUser().getFullAddress() + "");
        txtTechnicianNumber.setText(entity.getUser().getFullName() + "");
        txtCustomerNo.setText(entity.getUser().getId() + "");
        txtTitleCost.setText(getResString(R.string.additional_cost_1));
        txtCost.setText(getDockActivity().getResources().getString(R.string.qar)+" " + amount + "");
        txtTotalEarning.setText(getDockActivity().getResources().getString(R.string.qar)+" "+ amount + "");
        if (entity.getFeedback() != null) {
            rbRating.setScore(Float.parseFloat(entity.getFeedback().getRate()));
        } else {
            rbRating.setScore(0);
        }

    }


    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        getDockActivity().popFragment();
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case WebServiceConstants.RequestJobDetail:
                llMainFrame.setVisibility(View.VISIBLE);
                UserPaymentEnt response = (UserPaymentEnt) result;
                setRequestData(response);
                break;

            case WebServiceConstants.VisitDetail:
                llMainFrame.setVisibility(View.VISIBLE);
                SubscriptionPaymentEnt roomDetailEnt = (SubscriptionPaymentEnt) result;
                setVisitData(roomDetailEnt);

                break;
        }
    }



}