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
import com.ingic.ezhalbatek.technician.entities.AllCategoriesEnt;
import com.ingic.ezhalbatek.technician.entities.MasterTaskEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.MasterSubscriberDetailListBinder;
import com.ingic.ezhalbatek.technician.ui.binders.MasterTaskDetailListBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.AllCategories;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getMasterReports;


public class CustomerTaskFragment extends BaseFragment {
    public static final String TAG = "CustomerTaskFragment";
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.txtMonthly)
    AnyTextView txtMonthly;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.rvCustomers)
    CustomRecyclerView rvCustomers;
    Unbinder unbinder;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    private ArrayList<AllCategoriesEnt> allCategoriesCollection;

    private ArrayList<MasterTaskEnt> report;


    public static CustomerTaskFragment newInstance() {
        Bundle args = new Bundle();

        CustomerTaskFragment fragment = new CustomerTaskFragment();
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
        View view = inflater.inflate(R.layout.fragment_customer_tasks, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefHelper.setIsFromCustomerMasterSubscriber(false);


        serviceHelper.enqueueCall(webService.getAllCategories(), AllCategories);
        if (report == null) {
            serviceHelper.enqueueCall(webService.getMasterReportTask(edtName.getText().toString(), null), getMasterReports);
        }
        rvCustomers.setNestedScrollingEnabled(false);

    }


    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        // if (isValidite()) {
        if (spCategory.getSelectedItemPosition() != 0 && allCategoriesCollection != null && allCategoriesCollection.size() > 0) {
            serviceHelper.enqueueCall(webService.getMasterReportTask(edtName.getText().toString(), allCategoriesCollection.get(spCategory.getSelectedItemPosition()).getId() + ""), getMasterReports);
        } else {
            //   UIHelper.showShortToastInCenter(getDockActivity(),"Please select category");
            serviceHelper.enqueueCall(webService.getMasterReportTask(edtName.getText().toString(), null), getMasterReports);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //   if (isValidite()) {
        //     serviceHelper.enqueueCall(webService.getMasterReport(edtName.getText().toString()), getMasterReports);
        //  }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case getMasterReports:
                report = (ArrayList<MasterTaskEnt>) result;

                UIHelper.hideSoftKeyboard(getDockActivity(), edtName);

                if (report.size() > 0) {
                    rvCustomers.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                    txtMonthly.setVisibility(View.VISIBLE);

                    rvCustomers.bindRecyclerView(new MasterTaskDetailListBinder(itemClickListener, getResString(R.string.twenty_seven)), report, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

                } else {
                    rvCustomers.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                    txtMonthly.setVisibility(View.GONE);
                }

                break;

            case AllCategories:
                allCategoriesCollection = new ArrayList<>();
                AllCategoriesEnt allCategoriesEnt = new AllCategoriesEnt();
                allCategoriesEnt.setTitle(getResString(R.string.select_category));
                allCategoriesEnt.setArTitle(getResString(R.string.select_category));
                allCategoriesEnt.setId(0);
                allCategoriesCollection.add(allCategoriesEnt);
                allCategoriesCollection.addAll((ArrayList<AllCategoriesEnt>) result);

                ArrayList categoryList = new ArrayList();
                for (AllCategoriesEnt item : allCategoriesCollection) {
                    categoryList.add(item.getTitle());
                }

                ArrayAdapter<String> categoryAdapter;
                categoryAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, categoryList);

                categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spCategory.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
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
                MasterTaskEnt userData = (MasterTaskEnt) ent;
                getDockActivity().replaceDockableFragment(TechnicianVisitDetailsFragment.newInstance(userData,false), TechnicianVisitDetailsFragment.TAG);
                break;
        }
    };


}

