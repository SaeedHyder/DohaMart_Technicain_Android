package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.RequestNewJobsBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getRequestJobs;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getSubscriptionJobs;

/**
 * Created on 6/22/18.
 */
public class RegisteredUserNewJobsFragment extends BaseFragment {
    public static final String TAG = "RegisteredUserNewJobsFragment";
    @BindView(R.id.rvRegisteredJobs)
    CustomRecyclerView rvRegisteredJobs;
    Unbinder unbinder;


    private static String requestJobsKey = "requestJobsKey";
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private ArrayList<UserPaymentEnt> requestJobs = new ArrayList<>();
    private static String selectedDate = "";


    public static RegisteredUserNewJobsFragment newInstance() {
        Bundle args = new Bundle();

        RegisteredUserNewJobsFragment fragment = new RegisteredUserNewJobsFragment();
        fragment.setArguments(args);
        return fragment;
    }

   /* public static RegisteredUserNewJobsFragment newInstance(ArrayList<UserPaymentEnt> requestJobs) {
        Bundle args = new Bundle();
        args.putSerializable(requestJobsKey, requestJobs);
        RegisteredUserNewJobsFragment fragment = new RegisteredUserNewJobsFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    public static RegisteredUserNewJobsFragment newInstance(String dateKey) {
        Bundle args = new Bundle();
        selectedDate = dateKey;
        RegisteredUserNewJobsFragment fragment = new RegisteredUserNewJobsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //    requestJobs = (ArrayList<UserPaymentEnt>) getArguments().getSerializable(requestJobsKey);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registered_user_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.getNewrRquestJobs(prefHelper.getUser().getId() + "", selectedDate), getRequestJobs);
        prefHelper.setIsFromRequest(true);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case getRequestJobs:
                requestJobs = (ArrayList<UserPaymentEnt>) result;

                if (requestJobs != null && requestJobs.size() > 0) {
                    rvRegisteredJobs.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    rvRegisteredJobs.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                    requestJobs = new ArrayList<>();
                }

                rvRegisteredJobs.bindRecyclerView(new RequestNewJobsBinder(ItemClicklistener), requestJobs,
                        new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

                break;

        }
    }

    private RecyclerItemListener ItemClicklistener = (ent, position, id) -> {
        switch (id) {
            case R.id.btnDetails:
                getMainActivity().getAdditionalJobsHash().clear();
                getDockActivity().replaceDockableFragment(RegisteredUserJobDetailFragment.newInstance(((UserPaymentEnt) ent).getId() + ""), RegisteredUserJobDetailFragment.TAG);
                break;
            case R.id.btnReport:
                getDockActivity().replaceDockableFragment(JobReportFragment.newInstance((UserPaymentEnt) ent), JobReportFragment.TAG);
                break;
        }
    };

}