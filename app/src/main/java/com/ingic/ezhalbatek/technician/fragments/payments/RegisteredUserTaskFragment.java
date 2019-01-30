package com.ingic.ezhalbatek.technician.fragments.payments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.AppInterfaces;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.RegisteredUserTaskBinder;
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

/**
 * Created on 6/6/18.
 */
public class RegisteredUserTaskFragment extends BaseFragment implements AppInterfaces.DateFilterListener, AppInterfaces.SearchInterface {
    public static final String TAG = "RegisteredUserTaskFragment";
    @BindView(R.id.rvPendingJobs)
    CustomRecyclerView rvPendingJobs;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;


    public static RegisteredUserTaskFragment newInstance() {
        Bundle args = new Bundle();

        RegisteredUserTaskFragment fragment = new RegisteredUserTaskFragment();
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
        View view = inflater.inflate(R.layout.fragment_registered_user_task, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        serviceHelper.enqueueCall(webService.userPayment(prefHelper.getUser().getId(),null,null, null), UserPayment);
        rvPendingJobs.setNestedScrollingEnabled(false);
        prefHelper.setIsFromPaymentSubscriber(false);

        //   serviceHelper.enqueueCall(webService.userPayment(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date)), UserPayment);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case UserPayment:
                ArrayList<UserPaymentEnt> UserPaymentEnt = (ArrayList<UserPaymentEnt>) result;

                if (UserPaymentEnt.size() > 0) {
                    rvPendingJobs.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    rvPendingJobs.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }

                rvPendingJobs.bindRecyclerView(new RegisteredUserTaskBinder(ItemClicklistener), UserPaymentEnt, new LinearLayoutManager(getDockActivity(),
                        LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
                break;
        }
    }

    @Override
    public void onDateFilterChange(Date date) {
       /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        serviceHelper.enqueueCall(webService.userPayment(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date)), UserPayment);*/
    }

    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btnViewDetail:
                UserPaymentEnt taskData = (UserPaymentEnt) ent;
                getDockActivity().replaceDockableFragment(TaskDetailFragment.newInstance(taskData, false), TaskDetailFragment.TAG);
                break;
        }
    });


    @Override
    public void onSearchClick(Date date, int subscriptionId, int categoryId) {

        if (categoryId != 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            if (date != null) {
                serviceHelper.enqueueCall(webService.userPayment(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date), categoryId+""), UserPayment);
            } else {
                serviceHelper.enqueueCall(webService.userPayment(prefHelper.getUser().getId(),null,null, categoryId+""), UserPayment);
            }
        } else {
           // UIHelper.showShortToastInCenter(getDockActivity(), "Please select category");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            if (date != null) {
                serviceHelper.enqueueCall(webService.userPayment(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date), null), UserPayment);
            } else {
                serviceHelper.enqueueCall(webService.userPayment(prefHelper.getUser().getId(),null,null, null), UserPayment);
            }
        }

    }
}