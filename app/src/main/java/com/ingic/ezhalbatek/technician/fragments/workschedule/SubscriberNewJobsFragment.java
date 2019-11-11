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
import com.ingic.ezhalbatek.technician.ui.binders.SubscriberJobsBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getRequestJobs;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getSubscriptionJobs;

/**
 * Created on 6/22/18.
 */
public class SubscriberNewJobsFragment extends BaseFragment {
    public static final String TAG = "SubscriberNewJobsFragment";

    @BindView(R.id.rvSubscriberJobs)
    CustomRecyclerView rvSubscriberJobs;
    Unbinder unbinder;
    private static String selectedDate = "";

    private static String subscriptionJobsKey = "subscriptionJobsKey";
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private ArrayList<SubscriptionPaymentEnt> subscriptionJobs = new ArrayList<>();


   /* public static SubscriberNewJobsFragment newInstance(ArrayList<SubscriptionPaymentEnt> subscriptionJobs) {
        Bundle args = new Bundle();
        args.putSerializable(subscriptionJobsKey, subscriptionJobs);
        SubscriberNewJobsFragment fragment = new SubscriberNewJobsFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    public static SubscriberNewJobsFragment newInstance(String dateKey) {
        Bundle args = new Bundle();
        selectedDate = dateKey;
        SubscriberNewJobsFragment fragment = new SubscriberNewJobsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //    subscriptionJobs = (ArrayList<SubscriptionPaymentEnt>) getArguments().getSerializable(subscriptionJobsKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriber_new_jobs, container, false);
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

        serviceHelper.enqueueCall(webService.getNewSubscriptionJobs(prefHelper.getUser().getId() + "", selectedDate), getSubscriptionJobs);
        prefHelper.setIsFromRequest(false);


    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {

            case getSubscriptionJobs:

                subscriptionJobs = (ArrayList<SubscriptionPaymentEnt>) result;
                if (subscriptionJobs != null && subscriptionJobs.size() > 0) {
                    rvSubscriberJobs.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    rvSubscriberJobs.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                    subscriptionJobs = new ArrayList<>();
                }

                rvSubscriberJobs.bindRecyclerView(new SubscriberJobsBinder(ItemClicklistener), subscriptionJobs,
                        new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

                break;
        }
    }


    private RecyclerItemListener ItemClicklistener = (ent, position, id) -> {
        switch (id) {
            case R.id.btnDetails:
                getMainActivity().getAdditionalJobsHash().clear();
                getDockActivity().replaceDockableFragment(SubscriberJobDetailFragment.newInstance(((SubscriptionPaymentEnt) ent).getId() + ""), SubscriberJobDetailFragment.TAG);
                break;
            case R.id.btnAddDetail:
             /*   SubscriptionPaymentEnt subscriptionPaymentEnt = (SubscriptionPaymentEnt) ent;
                getDockActivity().replaceDockableFragment(AddDetailsFragment.newInstance(subscriptionPaymentEnt), AddDetailsFragment.TAG);*/
                getMainActivity().getAdditionalJobsHash().clear();
                getDockActivity().replaceDockableFragment(SubscriberJobDetailFragment.newInstance(((SubscriptionPaymentEnt) ent).getId() + ""), SubscriberJobDetailFragment.TAG);
                break;
            case R.id.btnReport:
                getDockActivity().replaceDockableFragment(JobReportFragment.newInstance((SubscriptionPaymentEnt) ent), JobReportFragment.TAG);
                break;
        }
    };

}