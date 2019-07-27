package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
import com.ingic.ezhalbatek.technician.entities.HashMapEnt;
import com.ingic.ezhalbatek.technician.entities.ServicsList;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.TaskEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.helpers.DialogHelper;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.AddRoomBinder;
import com.ingic.ezhalbatek.technician.ui.binders.AddedTaskBinder;
import com.ingic.ezhalbatek.technician.ui.binders.RegisteredUserJobDetailsBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.GetRequestData;

/**
 * Created on 6/29/18.
 */
public class RegisteredUserJobDetailFragment extends BaseFragment {
    public static final String TAG = "RegisteredUserJobDetailFragment";
    @BindView(R.id.txtItem)
    AnyTextView txtItem;
    @BindView(R.id.txtQuantity)
    AnyTextView txtQuantity;
    @BindView(R.id.rvAccessories)
    CustomRecyclerView rvAccessories;
    @BindView(R.id.edtBody)
    AnyEditTextView edtBody;
    @BindView(R.id.edtPrice)
    AnyEditTextView edtPrice;
    @BindView(R.id.task)
    AnyTextView task;
    @BindView(R.id.btnAddTask)
    LinearLayout btnAddTask;
    @BindView(R.id.txtJobs)
    AnyTextView txtJobs;
    @BindView(R.id.rvAddtionalJobs)
    CustomRecyclerView rvAddtionalJobs;
    @BindView(R.id.btnDone)
    Button btnDone;
    Unbinder unbinder;
    @BindView(R.id.ll_additional_jobs)
    LinearLayout llAdditionalJobs;
    @BindView(R.id.btnViewAccessories)
    Button btnViewAccessories;
    @BindView(R.id.btnAdditionalTask)
    Button btnAdditionalTask;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;

    @BindView(R.id.txtSubscriptionID)
    AnyTextView txtSubscriptionID;
    @BindView(R.id.txtCustomerName)
    AnyTextView txtCustomerName;
    @BindView(R.id.txtSubscriptionPackage)
    AnyTextView txtSubscriptionPackage;
    @BindView(R.id.txtMobileNo)
    AnyTextView txtMobileNo;
    @BindView(R.id.txtDate)
    AnyTextView txtDate;


    private ArrayList<ServicsList> jobsCollection;

    private static String RequestId ;
    private UserPaymentEnt entity;
    protected BroadcastReceiver broadcastReceiver;


    public static RegisteredUserJobDetailFragment newInstance() {
        Bundle args = new Bundle();
        RegisteredUserJobDetailFragment fragment = new RegisteredUserJobDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static RegisteredUserJobDetailFragment newInstance(String id) {
        Bundle args = new Bundle();
        RequestId=id;
        RegisteredUserJobDetailFragment fragment = new RegisteredUserJobDetailFragment();
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
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.twenty_seven));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registered_user_job_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefHelper.setIsFromRequest(true);
        rvAccessories.setNestedScrollingEnabled(false);
        mainFrame.setVisibility(View.GONE);
        onNotificationReceived();

        serviceHelper.enqueueCall(webService.getRequestData(prefHelper.getUser().getId(), RequestId+""), GetRequestData);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case GetRequestData:
                UserPaymentEnt response = (UserPaymentEnt) result;
                mainFrame.setVisibility(View.VISIBLE);
                setData(response);
                entity = response;
                jobsCollection = new ArrayList<>();
                jobsCollection.addAll(response.getServicsList());

                rvAccessories.bindRecyclerView(new RegisteredUserJobDetailsBinder(), jobsCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

                break;

        }
    }

    private void setData(UserPaymentEnt entityTask) {
        txtSubscriptionID.setText(entityTask.getUserDetail().getId() + "");
        txtCustomerName.setText(entityTask.getUserDetail().getFullName() + "");
        txtSubscriptionPackage.setText(entityTask.getUserDetail().getFullAddress() + "");
        txtMobileNo.setText(entityTask.getUserDetail().getPhoneNo() + "");
        txtDate.setText(DateHelper.dateFormat(entityTask.getDate(), "MMM dd,yyyy", "yyyy-MM-dd") + "");
    }

    private boolean checkAdditionalJobs(UserPaymentEnt entity) {
        if (entity.getAdditionalJobs() != null && entity.getAdditionalJobs().size() > 0) {
            for (AdditionalJob item : entity.getAdditionalJobs()) {
                if (item.getStatus() == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    @OnClick({R.id.btnDone, R.id.btnViewAccessories, R.id.btnAdditionalTask})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnDone:
                if (jobsCollection.size() > 0) {

                    if (checkAdditionalJobs(entity)) {
                        if (isChecked()) {
                            getDockActivity().replaceDockableFragment(ViewStatusNewFragment.newInstance(entity, false), "ViewStatusNewFragment");
                        } else {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.select_all_jobs));
                        }
                    } else {
                        DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                        dialogHelper.acknowledgementDialoge();
                        dialogHelper.showDialog();
                        dialogHelper.setCancelable(false);
                    }


                }
                break;
            case R.id.btnViewAccessories:

                getDockActivity().replaceDockableFragment(AddDetailsFragment.newInstance(entity), AddDetailsFragment.TAG);
                break;
            case R.id.btnAdditionalTask:
                if (checkAdditionalJobs(entity)) {
                    getDockActivity().replaceDockableFragment(AdditionalTaskFragment.newInstance(entity), "AdditionalTaskFragment");
                } else {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.acknowledgementDialoge();
                    dialogHelper.showDialog();
                    dialogHelper.setCancelable(false);
                }
                break;
        }
    }

    private boolean isChecked() {

        for (ServicsList item : jobsCollection) {
            if (!item.isChecked()) {
                return false;
            }
        }
        return true;
    }

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    System.out.println("registration complete");
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String type = bundle.getString("action_type");
                        final String actionId = bundle.getString("action_id");

                        if (type != null && type.equals(AppConstants.ADDITIONALJOBREQUEST)) {
                            serviceHelper.enqueueCall(webService.getRequestData(prefHelper.getUser().getId(), entity.getId()+""), GetRequestData);
                        }
                    }
                }
            }

        };
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));


    }


}