package com.ingic.ezhalbatek.technician.fragments.customerlist;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.MasterSubscriptionEnt;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPackagesEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.MasterSubscriberDetailListBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.AllPackages;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getMasterReports;

/**
 * Created on 6/30/18.
 */
public class CustomerSubscriberFragment extends BaseFragment {
    public static final String TAG = "CustomerSubscriberFragment";
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.txtMonthly)
    AnyTextView txtMonthly;
    Unbinder unbinder;
    @BindView(R.id.rvCustomers)
    CustomRecyclerView rvCustomers;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.sp_sucscription)
    Spinner spSubscription;
    private ArrayList<SubscriptionPackagesEnt> allPackagesCollection;

    private ArrayList<MasterSubscriptionEnt> report;


    public static CustomerSubscriberFragment newInstance() {
        Bundle args = new Bundle();

        CustomerSubscriberFragment fragment = new CustomerSubscriberFragment();
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
        titleBar.hideTitleBar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_subscriber, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefHelper.setIsFromCustomerMasterSubscriber(true);
        serviceHelper.enqueueCall(webService.getAllPackages(), AllPackages);
        if (report == null) {
            serviceHelper.enqueueCall(webService.getMasterReportSubscriber(edtName.getText().toString(), null), getMasterReports);
        }
        rvCustomers.setNestedScrollingEnabled(false);

    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        //if (isValidite()) {
        if (spSubscription.getSelectedItemPosition() != 0 && allPackagesCollection != null && allPackagesCollection.size() > 0) {
            serviceHelper.enqueueCall(webService.getMasterReportSubscriber(edtName.getText().toString(), allPackagesCollection.get(spSubscription.getSelectedItemPosition()).getId() + ""), getMasterReports);
        } else {
            serviceHelper.enqueueCall(webService.getMasterReportSubscriber(edtName.getText().toString(), null), getMasterReports);

        }
        //   serviceHelper.enqueueCall(webService.getMasterReport(edtName.getText().toString()), getMasterReports);
        // }
    }

    @Override
    public void onResume() {
        super.onResume();
      /*  if (report!=null && report.size() > 0) {
            rvCustomers.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            txtMonthly.setVisibility(View.VISIBLE);
            rvCustomers.bindRecyclerView(new MasterSubscriberDetailListBinder(itemClickListener, getResString(R.string.monthly_maintaince)), report, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

        } else {
            rvCustomers.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
            txtMonthly.setVisibility(View.GONE);
        }*/

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case getMasterReports:
                report = (ArrayList<MasterSubscriptionEnt>) result;

                UIHelper.hideSoftKeyboard(getDockActivity(), edtName);

                if (report.size() > 0) {
                    rvCustomers.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                    txtMonthly.setVisibility(View.VISIBLE);
                    rvCustomers.bindRecyclerView(new MasterSubscriberDetailListBinder(itemClickListener, getResString(R.string.monthly_maintaince)), report, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

                } else {
                    rvCustomers.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                    txtMonthly.setVisibility(View.GONE);
                }

                break;

            case AllPackages:
                allPackagesCollection = new ArrayList<>();
                SubscriptionPackagesEnt subscriptionPackagesEnt = new SubscriptionPackagesEnt();
                subscriptionPackagesEnt.setName(getResString(R.string.select_subscription_pakage));
                subscriptionPackagesEnt.setId(0);
                allPackagesCollection.add(subscriptionPackagesEnt);
                allPackagesCollection.addAll((ArrayList<SubscriptionPackagesEnt>) result);

                ArrayList subscriptionList = new ArrayList();
                for (SubscriptionPackagesEnt item : allPackagesCollection) {
                    subscriptionList.add(item.getName());
                }

                ArrayAdapter<String> subscriptionAdapter;
                subscriptionAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, subscriptionList);
                subscriptionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spSubscription.setAdapter(subscriptionAdapter);
                subscriptionAdapter.notifyDataSetChanged();
                break;
        }

    }

    private boolean isValidite() {
        if (edtName.getText().toString() == null || edtName.getText().toString().isEmpty()) {
            edtName.setError("Enter name");
            return false;
        } else {
            return true;
        }
    }

    private RecyclerItemListener itemClickListener = (ent, position, id) -> {
        switch (id) {
            case R.id.mainframe:
                MasterSubscriptionEnt userData = (MasterSubscriptionEnt) ent;
                getDockActivity().replaceDockableFragment(TechnicianVisitDetailsFragment.newInstance(userData, true), TechnicianVisitDetailsFragment.TAG);
                break;
        }
    };


}