package com.ingic.ezhalbatek.technician.fragments.customerlist;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.MasterSubscriptionEnt;
import com.ingic.ezhalbatek.technician.entities.MasterTaskEnt;
import com.ingic.ezhalbatek.technician.entities.UserRequest;
import com.ingic.ezhalbatek.technician.entities.UserVisit;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.TechnicianVisitDetailsBinder;
import com.ingic.ezhalbatek.technician.ui.binders.TechnicianVisitUserBinder;
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
public class TechnicianVisitDetailsFragment extends BaseFragment {
    public static final String TAG = "TechnicianVisitDetailsFragment";
    @BindView(R.id.rvTechnicianDetails)
    CustomRecyclerView rvTechnicianDetails;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    private static String SubscriptionKey = "SubscriptionKey";
    private static String TaskKey = "TaskKey";
    private static boolean isSubscriber = false;
    private MasterSubscriptionEnt subEnt;
    private MasterTaskEnt taskEnt;
    private ArrayList<UserRequest> taskCollection;


    public static TechnicianVisitDetailsFragment newInstance() {
        Bundle args = new Bundle();

        TechnicianVisitDetailsFragment fragment = new TechnicianVisitDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static TechnicianVisitDetailsFragment newInstance(MasterSubscriptionEnt data, boolean subscriber) {
        Bundle args = new Bundle();
        args.putString(SubscriptionKey, new Gson().toJson(data));
        isSubscriber = subscriber;
        TechnicianVisitDetailsFragment fragment = new TechnicianVisitDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TechnicianVisitDetailsFragment newInstance(MasterTaskEnt taskData,boolean subscriber) {
        Bundle args = new Bundle();
        args.putString(TaskKey, new Gson().toJson(taskData));
        isSubscriber = subscriber;
        TechnicianVisitDetailsFragment fragment = new TechnicianVisitDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(SubscriptionKey);
            String taskJsonString = getArguments().getString(TaskKey);
            if (jsonString != null) {
                subEnt = new Gson().fromJson(jsonString, MasterSubscriptionEnt.class);
            }
            if (taskJsonString != null) {
                taskEnt = new Gson().fromJson(taskJsonString, MasterTaskEnt.class);
            }
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.technician_visits));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_technician_visit_detail, container, false);
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
        setData();
    }

    private void setData() {

        if (subEnt != null && subEnt.getUserSubscription() != null &&
                subEnt.getUserSubscription().getSubscription() != null &&
                subEnt.getUserVisits().size() > 0) {

            rvTechnicianDetails.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            rvTechnicianDetails.bindRecyclerView(new TechnicianVisitDetailsBinder(itemClickListener), subEnt.getUserVisits(), new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

        } else if (taskEnt != null && taskEnt.getUserRequests() != null &&
                taskEnt.getUserRequests().size() > 0) {
            taskCollection=new ArrayList<>();

            for(UserRequest item: taskEnt.getUserRequests()){
                if(item.getAssignTechnician()!=null){
                    taskCollection.add(item);
                }
            }
            rvTechnicianDetails.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            rvTechnicianDetails.bindRecyclerView(new TechnicianVisitUserBinder(itemClickListener), taskCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

        } else {
            rvTechnicianDetails.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }


    private RecyclerItemListener itemClickListener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btnViewReport:
                if (isSubscriber) {
                    UserVisit subscriberData = (UserVisit) ent;
                    getDockActivity().replaceDockableFragment(TechnicianDetailReportFragment.newInstance(subscriberData, true, subEnt.getFullName(), subEnt.getUserSubscription().getSubscription().getTitle() + ""), TechnicianDetailReportFragment.TAG);
                } else {
                    UserRequest userrData = (UserRequest) ent;
                    getDockActivity().replaceDockableFragment(TechnicianDetailReportFragment.newInstance(userrData, false, taskEnt.getFullName()), TechnicianDetailReportFragment.TAG);
                }
                break;
        }
    });


}