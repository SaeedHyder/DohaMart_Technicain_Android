package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdditionalJobMarkDone;
import com.ingic.ezhalbatek.technician.entities.AdditionalJobsEnt;
import com.ingic.ezhalbatek.technician.entities.HashMapEnt;
import com.ingic.ezhalbatek.technician.entities.Items;
import com.ingic.ezhalbatek.technician.entities.SendSubAdditionalJobs;
import com.ingic.ezhalbatek.technician.entities.SendTaskAdditionalJobs;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.ui.adapters.ArrayListExpandableAdapter;
import com.ingic.ezhalbatek.technician.ui.binders.AdditionalTaskBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.AdditionalJob;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.SendAdditionalJobs;

public class AdditionalTaskFragment extends BaseFragment {
    public static final String TAG = "AdditionalTaskFragment";
    @BindView(R.id.lv_additional_tasks)
    ExpandableListView lvAdditionalTasks;
    Unbinder unbinder;
    @BindView(R.id.btnDone)
    Button btnDone;

    private static String SubJobDetail = "SubJobDetail";
    private static String RequestJobDetail = "RequestJobDetail";
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private SubscriptionPaymentEnt subEntity;
    private UserPaymentEnt requestEntity;

    private ArrayListExpandableAdapter<String, ArrayList<Items>> adapter;
    private ArrayList<String> collectionGroup;
    private ArrayList<ArrayList<Items>> collectionChild;

    private HashMap<String, ArrayList<ArrayList<Items>>> listDataChild;

    public static AdditionalTaskFragment newInstance() {
        Bundle args = new Bundle();

        AdditionalTaskFragment fragment = new AdditionalTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AdditionalTaskFragment newInstance(SubscriptionPaymentEnt subEntitiy) {
        Bundle args = new Bundle();
        args.putString(SubJobDetail, new Gson().toJson(subEntitiy));
        AdditionalTaskFragment fragment = new AdditionalTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public static AdditionalTaskFragment newInstance(UserPaymentEnt taskEntity) {
        Bundle args = new Bundle();
        args.putString(RequestJobDetail, new Gson().toJson(taskEntity));
        AdditionalTaskFragment fragment = new AdditionalTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(SubJobDetail);
            String requestJsonString = getArguments().getString(RequestJobDetail);

            if (jsonString != null) {
                subEntity = new Gson().fromJson(jsonString, SubscriptionPaymentEnt.class);
            }
            if (requestJsonString != null) {
                requestEntity = new Gson().fromJson(requestJsonString, UserPaymentEnt.class);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additional_task, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        serviceHelper.enqueueCall(webService.getAdditionalJobs(prefHelper.getUser().getId() + ""), AdditionalJob);

    }


    private void setInProgressJobsData(ArrayList<AdditionalJobsEnt> result) {


        collectionGroup = new ArrayList<>();
        collectionChild = new ArrayList<>();

        listDataChild = new HashMap<>();


        for (AdditionalJobsEnt item : result) {
            collectionGroup.add(item.getName());

            collectionChild.add(item.getItems());
            listDataChild.put(item.getName(), collectionChild);
            collectionChild = new ArrayList<>();


        }

        if (collectionGroup.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            lvAdditionalTasks.setVisibility(View.VISIBLE);
            adapter = new ArrayListExpandableAdapter<>(getDockActivity(), collectionGroup, listDataChild, new AdditionalTaskBinder(getDockActivity(), prefHelper, getMainActivity()));
            lvAdditionalTasks.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            lvAdditionalTasks.setVisibility(View.GONE);
        }

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResString(R.string.additional_tasks));
        titleBar.showBackButton();
    }


    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        List<AdditionalJobMarkDone> additionalJobsDone = new ArrayList<>();
        if (getMainActivity().getAdditionalJobsHash().entrySet().size() > 0) {
            for (Map.Entry<String, HashMapEnt> item : getMainActivity().getAdditionalJobsHash().entrySet()) {
                additionalJobsDone.add(new AdditionalJobMarkDone(item.getKey(), item.getValue().getQuantity()));
            }

            dialogHelper.showCommonDialog(v -> {

                if (subEntity != null) {
                    SendSubAdditionalJobs subAdditionalJobs = new SendSubAdditionalJobs(subEntity.getId() + "", prefHelper.getUser().getId() + "", additionalJobsDone);
                    serviceHelper.enqueueCall(webService.sendSubAdditionalJobs(subAdditionalJobs), SendAdditionalJobs);
                } else if (requestEntity != null) {
                    SendTaskAdditionalJobs taskAdditionalJobs = new SendTaskAdditionalJobs(requestEntity.getId() + "", prefHelper.getUser().getId() + "", additionalJobsDone);
                    serviceHelper.enqueueCall(webService.sendTaskAdditionalJobs(taskAdditionalJobs), SendAdditionalJobs);

                }
                dialogHelper.hideDialog();
            }, R.string.additional_job, R.string.add_additional_job, R.string.yes, R.string.no, true, true);
            dialogHelper.showDialog();
            dialogHelper.setCancelable(false);

        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.select_additional_job));
        }
    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case AdditionalJob:
                ArrayList<AdditionalJobsEnt> response = (ArrayList<AdditionalJobsEnt>) result;
                setInProgressJobsData(response);
                break;

            case SendAdditionalJobs:
                getDockActivity().popFragment();
                getMainActivity().getAdditionalJobsHash().clear();
                break;

        }
    }

}
