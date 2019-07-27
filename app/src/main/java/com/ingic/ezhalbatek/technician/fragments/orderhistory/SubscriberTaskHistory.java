package com.ingic.ezhalbatek.technician.fragments.orderhistory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.fragments.payments.TaskDetailFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.JobReportFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.AppInterfaces;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.SubscriberTaskHistoryBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.SubscriptionHistory;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.SubscriptionPayment;

/**
 * Created on 6/2/18.
 */
public class SubscriberTaskHistory extends BaseFragment implements AppInterfaces.DateFilterListener,AppInterfaces.SearchInterface {
    public static final String TAG = "SubscriberTaskHistory";
    @BindView(R.id.rvCompleteJobs)
    CustomRecyclerView rvSubscriberTask;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;


    public static SubscriberTaskHistory newInstance() {
        Bundle args = new Bundle();

        SubscriberTaskHistory fragment = new SubscriberTaskHistory();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriber_task_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefHelper.setIsFromHistorySubscriber(true);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        rvSubscriberTask.setNestedScrollingEnabled(false);

        serviceHelper.enqueueCall(webService.subscriptionHistory(prefHelper.getUser().getId(),null, null,null), SubscriptionHistory);
        //serviceHelper.enqueueCall(webService.subscriptionHistory(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date)), SubscriptionHistory);


    }



    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case SubscriptionHistory:
                ArrayList<SubscriptionPaymentEnt> subscriptionPaymentEnt = (ArrayList<SubscriptionPaymentEnt>) result;

                if (subscriptionPaymentEnt.size() > 0) {
                    rvSubscriberTask.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    rvSubscriberTask.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }

                rvSubscriberTask.bindRecyclerView(new SubscriberTaskHistoryBinder(ItemClicklistener), subscriptionPaymentEnt, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
                break;
        }
    }

    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btnDetails:
                SubscriptionPaymentEnt subscriberData = (SubscriptionPaymentEnt) ent;
                getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(subscriberData), JobReportFragment.TAG);
                break;
        }
    });


    @Override
    public void onDateFilterChange(Date date) {
     /*   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        serviceHelper.enqueueCall(webService.subscriptionHistory(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date)), SubscriptionHistory);*/

    }

    @Override
    public void onSearchClick(Date date,Date EndDate,  int subscriptionId,int categoryId) {
        if(subscriptionId!=0) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        if(date!=null){
            serviceHelper.enqueueCall(webService.subscriptionHistory(prefHelper.getUser().getId(),dateFormat.format(date), dateFormat.format(EndDate),subscriptionId+""), SubscriptionHistory);
        }
        else{
            serviceHelper.enqueueCall(webService.subscriptionHistory(prefHelper.getUser().getId(),null, null,subscriptionId+""), SubscriptionHistory);
        }
        }
        else{
          //  UIHelper.showShortToastInCenter(getDockActivity(),"Please select subscription");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            if(date!=null){
                serviceHelper.enqueueCall(webService.subscriptionHistory(prefHelper.getUser().getId(),dateFormat.format(date), dateFormat.format(EndDate),null), SubscriptionHistory);
            }
            else{
                serviceHelper.enqueueCall(webService.subscriptionHistory(prefHelper.getUser().getId(),null, null,null), SubscriptionHistory);
            }
        }
    }
}