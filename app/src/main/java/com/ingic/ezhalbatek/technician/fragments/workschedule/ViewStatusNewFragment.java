package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdditionalJob;
import com.ingic.ezhalbatek.technician.entities.AdditionalJobMarkDone;
import com.ingic.ezhalbatek.technician.entities.CreateRoomEnt;
import com.ingic.ezhalbatek.technician.entities.HashMapEnt;
import com.ingic.ezhalbatek.technician.entities.MarkJobDone;
import com.ingic.ezhalbatek.technician.entities.MarkVisitDone;
import com.ingic.ezhalbatek.technician.entities.RoomStatus;
import com.ingic.ezhalbatek.technician.entities.ServicsList;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.TaskEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.VisitDetailEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.fragments.standard.HomeFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.ui.binders.JobQuotationBinder;
import com.ingic.ezhalbatek.technician.ui.binders.JobStatusBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.DeleteAllNotificaiton;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.markVIsitDone;

/**
 * Created by saeedhyder on 7/19/2018.
 */
public class ViewStatusNewFragment extends BaseFragment {
    @BindView(R.id.txtStatusTitle)
    AnyTextView txtStatusTitle;
    @BindView(R.id.rvStatus)
    CustomRecyclerView rvStatus;
    @BindView(R.id.rvQuotations)
    CustomRecyclerView rvQuotations;
    @BindView(R.id.btnViewDetail)
    Button btnViewDetail;
    Unbinder unbinder;
    @BindView(R.id.txt_jobselectedtext)
    AnyTextView txtJobselectedtext;
    @BindView(R.id.txtPrice)
    AnyTextView txtPrice;
    @BindView(R.id.txtAdditional_task)
    AnyTextView txtAdditionalTask;

    private ArrayList<String> jobCollection;
    private boolean isSubscribeUser = false;

    private static String JobDetail = "JobDetail";
    private static String RequestJobDetail = "RequestJobDetail";
    private static String isSubscriber = "isSubscriber";
    private SubscriptionPaymentEnt entity;
    private UserPaymentEnt requestEntity;
    private ArrayList<TaskEnt> additionalJobs;
    private Double amount = 0.0;
    private ArrayList<CreateRoomEnt> roomAdded;
    private ArrayList<RoomStatus> roomStatus;


    public static ViewStatusNewFragment newInstance() {
        Bundle args = new Bundle();

        ViewStatusNewFragment fragment = new ViewStatusNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ViewStatusNewFragment newInstance(SubscriptionPaymentEnt data, boolean isSubscribeUser) {
        Bundle args = new Bundle();
        args.putString(JobDetail, new Gson().toJson(data));
        args.putBoolean(isSubscriber, isSubscribeUser);
        ViewStatusNewFragment fragment = new ViewStatusNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ViewStatusNewFragment newInstance(UserPaymentEnt data, boolean isSubscribeUser) {
        Bundle args = new Bundle();
        args.putString(RequestJobDetail, new Gson().toJson(data));
        args.putBoolean(isSubscriber, isSubscribeUser);

        ViewStatusNewFragment fragment = new ViewStatusNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(JobDetail);
            String requestJsonString = getArguments().getString(RequestJobDetail);
            isSubscribeUser = getArguments().getBoolean(isSubscriber);

            if (jsonString != null) {
                entity = new Gson().fromJson(jsonString, SubscriptionPaymentEnt.class);
            }
            if (requestJsonString != null) {
                requestEntity = new Gson().fromJson(requestJsonString, UserPaymentEnt.class);
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_status, container, false);
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

        txtStatusTitle.setText(isSubscribeUser ? getResString(R.string.status) : "Repair Status");

        if (entity != null) {

            jobCollection = new ArrayList<>();

            if (entity.getSubscriptionRooms().size() > 0) {
                for (CreateRoomEnt item : entity.getSubscriptionRooms()) {
                    for (RoomStatus itemStatus : entity.getRoomStatus()) {
                        if (String.valueOf(item.getId()).equals(itemStatus.getRoomId() + "")) {
                            jobCollection.add(item.getName() + "");
                        }
                    }
                }
            }

            rvStatus.bindRecyclerView(new JobStatusBinder(), jobCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

            additionalJobs = new ArrayList<>();
           /* for (Map.Entry<String, HashMapEnt> item : getMainActivity().getAdditionalJobsHash().entrySet()) {
                additionalJobs.add(new TaskEnt(item.getKey(), item.getValue().getName(), item.getValue().getQuantity()));
            }*/
            if (entity.getAdditionalJobs() != null && entity.getAdditionalJobs().size() > 0) {
                for (AdditionalJob item : entity.getAdditionalJobs()) {
                    if (item.getStatus() == 2) {
                        additionalJobs.add(new TaskEnt(item.getId() + "", item.getItem().getName() + "", item.getItem().getQuantity() + ""));
                    }
                }

            }
            if (additionalJobs != null && additionalJobs.size() > 0) {
                rvQuotations.bindRecyclerView(new JobQuotationBinder(), additionalJobs, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
            } else {
                txtAdditionalTask.setVisibility(View.GONE);
            }
           /* for (AdditionalJob item : entity.getAdditionalJobs()) {
                amount = amount + Double.parseDouble(item.getAmount());
            }

            txtPrice.setText(entity.getSubscription().getCurrencyCode() + " " + amount + "");*/

        } else if (requestEntity != null) {

            jobCollection = new ArrayList<>();

            for (ServicsList item : requestEntity.getServicsList()) {
                jobCollection.add(item.getServiceDetail().getTitle() + "");
            }
            rvStatus.bindRecyclerView(new JobStatusBinder(), jobCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

            additionalJobs = new ArrayList<>();

            if (requestEntity.getAdditionalJobs() != null && requestEntity.getAdditionalJobs().size() > 0) {
                for (AdditionalJob item : requestEntity.getAdditionalJobs()) {
                    if (item.getStatus() == 2) {
                        additionalJobs.add(new TaskEnt(item.getId() + "", item.getItem().getName() + "", item.getItem().getQuantity() + ""));
                    }
                }

            }

            if (additionalJobs != null && additionalJobs.size() > 0) {
                rvQuotations.bindRecyclerView(new JobQuotationBinder(), additionalJobs, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
            } else {
                txtAdditionalTask.setVisibility(View.GONE);
            }

          /*  for (AdditionalJob item : requestEntity.getAdditionalJobs()) {
                amount = amount + Double.parseDouble(item.getAmount());
            }
           for (TaskEnt item : additionalJobs) {
                amount = amount + Double.parseDouble(item.getTaskPrice());
            }

            txtPrice.setText(requestEntity.getCurrencyCode() + " " + amount + "");*/

        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(isSubscribeUser ? getResString(R.string.view_status_title) : getResString(R.string.job_status));
    }


    @OnClick(R.id.btnViewDetail)
    public void onViewClicked() {

        if (entity != null) {
            List<AdditionalJobMarkDone> additionalJobsDone = new ArrayList<>();
            if (additionalJobs.size() > 0) {
                for (TaskEnt item : additionalJobs) {
                    additionalJobsDone.add(new AdditionalJobMarkDone(item.getId(), item.getQuantity()));
                }
            }
            MarkVisitDone markVisitDone = new MarkVisitDone(prefHelper.getUser().getId() + "",
                    entity.getId() + "", additionalJobsDone);
            dialogHelper.showCommonDialog(v -> {

                serviceHelper.enqueueCall(webService.markVisitDone(markVisitDone), markVIsitDone);
                dialogHelper.hideDialog();
            }, R.string.job_done, R.string.job_done_message, R.string.yes, R.string.no, true, true);
            dialogHelper.showDialog();
            dialogHelper.setCancelable(false);

        } else {
            List<AdditionalJobMarkDone> additionalJobsDone = new ArrayList<>();
            if (additionalJobs.size() > 0) {
                for (TaskEnt item : additionalJobs) {
                    additionalJobsDone.add(new AdditionalJobMarkDone(item.getId(), item.getQuantity()));
                }
            }
            MarkJobDone markJobDone = new MarkJobDone(requestEntity.getId() + "", prefHelper.getUser().getId() + "",
                    amount + "", additionalJobsDone);
            dialogHelper.showCommonDialog(v -> {

                serviceHelper.enqueueCall(webService.markJobDone(markJobDone), markVIsitDone);
                dialogHelper.hideDialog();
            }, R.string.job_done, R.string.job_done_message, R.string.yes, R.string.no, true, true);
            dialogHelper.showDialog();
            dialogHelper.setCancelable(false);


        }

    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case markVIsitDone:
                getDockActivity().popBackStackTillEntry(0);
                UIHelper.showShortToastInCenter(getDockActivity(), "Waiting for job acknowledgment");
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                break;
        }
    }


}
