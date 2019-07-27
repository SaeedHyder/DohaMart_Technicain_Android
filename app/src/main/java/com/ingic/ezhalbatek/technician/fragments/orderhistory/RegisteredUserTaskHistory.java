package com.ingic.ezhalbatek.technician.fragments.orderhistory;

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
import com.ingic.ezhalbatek.technician.fragments.workschedule.JobReportFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.AppInterfaces;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.RegisteredUserTaskBinder;
import com.ingic.ezhalbatek.technician.ui.binders.SubscriberTaskHistoryBinder;
import com.ingic.ezhalbatek.technician.ui.binders.UserTaskHistoryBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.UserPayment;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.UserTaskHistory;

/**
 * Created on 6/2/18.
 */
public class RegisteredUserTaskHistory extends BaseFragment implements AppInterfaces.DateFilterListener, AppInterfaces.SearchInterface {
    public static final String TAG = "RegisteredUserTaskHistory";
    @BindView(R.id.rvPendingJobs)
    CustomRecyclerView rvRegisteredUserTask;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    public static RegisteredUserTaskHistory newInstance() {
        Bundle args = new Bundle();

        RegisteredUserTaskHistory fragment = new RegisteredUserTaskHistory();
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
        View view = inflater.inflate(R.layout.fragment_registered_user_task_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        rvRegisteredUserTask.setNestedScrollingEnabled(false);
        prefHelper.setIsFromHistorySubscriber(false);

        serviceHelper.enqueueCall(webService.userTaskHistory(prefHelper.getUser().getId(),null,null, null), UserTaskHistory);
        // serviceHelper.enqueueCall(webService.userTaskHistory(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date)), UserTaskHistory);



    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case UserTaskHistory:
                ArrayList<UserPaymentEnt> UserHistory = (ArrayList<UserPaymentEnt>) result;

                if (UserHistory.size() > 0) {
                    rvRegisteredUserTask.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    rvRegisteredUserTask.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }

                rvRegisteredUserTask.bindRecyclerView(new UserTaskHistoryBinder(ItemClicklistener), UserHistory, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
                break;
        }
    }

    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btnDetails:
                UserPaymentEnt subscriberData = (UserPaymentEnt) ent;
                getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(subscriberData), JobReportFragment.TAG);
                break;
        }
    });



    @Override
    public void onDateFilterChange(Date date) {
       /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        serviceHelper.enqueueCall(webService.userTaskHistory(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date)), UserTaskHistory);*/
    }

    @Override
    public void onSearchClick(Date date,Date EndDate, int subscriptionId, int categoryId) {

        if (categoryId != 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            if (date != null) {
                serviceHelper.enqueueCall(webService.userTaskHistory(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(EndDate), categoryId+""), UserTaskHistory);
            } else {
                serviceHelper.enqueueCall(webService.userTaskHistory(prefHelper.getUser().getId(),null,null, categoryId+""), UserTaskHistory);
            }

        } else {
            //UIHelper.showShortToastInCenter(getDockActivity(), "Please select category");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            if (date != null) {
                serviceHelper.enqueueCall(webService.userTaskHistory(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(EndDate), null), UserTaskHistory);
            } else {
                serviceHelper.enqueueCall(webService.userTaskHistory(prefHelper.getUser().getId(),null,null, null), UserTaskHistory);
            }
        }
    }
}