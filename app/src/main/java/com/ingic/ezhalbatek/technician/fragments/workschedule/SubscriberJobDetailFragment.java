package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
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
import com.ingic.ezhalbatek.technician.entities.CreateRoomEnt;
import com.ingic.ezhalbatek.technician.entities.RoomStatus;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.SubscriptionRoom;
import com.ingic.ezhalbatek.technician.entities.VisitDetailEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.helpers.DialogHelper;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.OnLongTap;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.AddRoomBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.DeleteRoom;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.GetRequestData;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.createRoom;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getRoomDetail;

/**
 * Created on 6/27/18.
 */
public class SubscriberJobDetailFragment extends BaseFragment implements OnLongTap {
    public static final String TAG = "SubscriberJobDetailFragment";
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
    @BindView(R.id.txtMonthly)
    AnyTextView txtMonthly;
    @BindView(R.id.rvRooms)
    CustomRecyclerView rvRooms;
    @BindView(R.id.btnViewStatus)
    Button btnViewStatus;
    @BindView(R.id.btnAdditionalTask)
    Button btnAdditionalTask;
    Unbinder unbinder;
    @BindView(R.id.btnCreateRooom)
    Button btnCreateRooom;
    @BindView(R.id.mainFrameLayout)
    LinearLayout mainFrameLayout;
    private ArrayList<CreateRoomEnt> roomAdded;
    private ArrayList<RoomStatus> roomStatus;

    private static String VisitId = "";
    private SubscriptionPaymentEnt entity;
    private String jobDonePosition;
    protected BroadcastReceiver broadcastReceiver;

    public static SubscriberJobDetailFragment newInstance() {
        Bundle args = new Bundle();

        SubscriberJobDetailFragment fragment = new SubscriberJobDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SubscriberJobDetailFragment newInstance(String id) {
        Bundle args = new Bundle();
        VisitId = id;
        SubscriberJobDetailFragment fragment = new SubscriberJobDetailFragment();
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
        titleBar.setSubHeading(getResString(R.string.new_jobs));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriber_job_detail, container, false);
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

        txtSubscriptionPackage.setSelected(true);
        mainFrameLayout.setVisibility(View.GONE);
        prefHelper.setIsFromRequest(false);
        rvRooms.setNestedScrollingEnabled(false);

        onNotificationReceived();
        serviceHelper.enqueueCall(webService.getRoomDetail(VisitId), getRoomDetail);


    }


    private void showAddRoomDialog() {
        dialogHelper.showCommonDialog(v -> {
            if(dialogHelper.getEditableText()!=null && !dialogHelper.getEditableText().trim().equals("")) {
            serviceHelper.enqueueCall(webService.createRoom(entity.getUserId() + "", dialogHelper.getEditableText(), entity.getId() + "", null), createRoom);
                dialogHelper.hideDialog();
            }else{
                dialogHelper.getEditTextView().setError(getResString(R.string.room_text_error));
            }

        }, R.string.create_room, R.string.ok, R.string.empty, false, true, false);
        dialogHelper.showDialog();
        dialogHelper.setCancelable(false);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {

            case createRoom:
                serviceHelper.enqueueCall(webService.getRoomDetail(VisitId), getRoomDetail);
               /* if (showViewStatusButton(entity)) {
                    serviceHelper.enqueueCall(webService.getRoomDetail(VisitId), getRoomDetail);
                } else {
                    CreateRoomEnt ent = (CreateRoomEnt) result;
                    txtMonthly.setVisibility(View.VISIBLE);
                    roomAdded.add(ent);
                    rvRooms.notifyDataSetChanged();
                }*/

                break;

            case getRoomDetail:

                SubscriptionPaymentEnt roomDetailEnt = (SubscriptionPaymentEnt) result;
                entity = roomDetailEnt;

                roomAdded = new ArrayList<>();
                roomStatus = new ArrayList<>();

                if (entity.getSubscriptionRooms().size() > 0) {
                    for (CreateRoomEnt item : entity.getSubscriptionRooms()) {
                        for (RoomStatus itemStatus : entity.getRoomStatus()) {
                            if (String.valueOf(item.getId()).equals(itemStatus.getRoomId() + "")) {
                                roomAdded.add(item);
                                roomStatus.add(itemStatus);
                            }
                        }
                    }
                }

                if (showViewStatusButton(roomStatus)) {
                    btnViewStatus.setVisibility(View.VISIBLE);
                    btnAdditionalTask.setVisibility(View.VISIBLE);
                } else {
                    btnViewStatus.setVisibility(View.GONE);
                    btnAdditionalTask.setVisibility(View.GONE);
                }

                if (entity.getSubscriptionRooms().size() > 0) {
                    txtMonthly.setVisibility(View.VISIBLE);
                    rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity(), this, roomStatus), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
                } else {
                    txtMonthly.setVisibility(View.GONE);
                    rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity(), this, roomStatus), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
                }

                setData();
                mainFrameLayout.setVisibility(View.VISIBLE);
                break;

            case DeleteRoom:
             /*   if (jobDonePosition != null) {
                    roomAdded.remove(Integer.parseInt(jobDonePosition));
                    rvRooms.notifyDataSetChanged();
               }*/
                serviceHelper.enqueueCall(webService.getRoomDetail(VisitId), getRoomDetail);
                break;
        }
    }

    private boolean showViewStatusButton(ArrayList<RoomStatus> data) {

        if (data.size() <= 0) {
            return false;
        }
        for (RoomStatus item : data) {
            if (item.getStatus() != 2) {
                return false;
            }
        }

        return true;
    }

    private boolean checkAdditionalJobs(SubscriptionPaymentEnt entity) {
        if (entity.getAdditionalJobs() != null && entity.getAdditionalJobs().size() > 0) {
            for (AdditionalJob item : entity.getAdditionalJobs()) {
                if (item.getStatus() == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setData() {

        txtSubscriptionID.setText(entity.getUser().getId() + "");
        txtCustomerName.setText(entity.getUser().getFullName() + "");
        txtSubscriptionPackage.setText(entity.getUser().getFullAddress() + "");
        txtMobileNo.setText(entity.getUser().getCountryCode()+entity.getUser().getPhoneNo() + "");
        txtDate.setText(DateHelper.dateFormat(entity.getCreatedAt(), "MMM dd,yyyy", "yyyy-MM-dd HH:mm:ss") + "");
    }


    @OnClick({R.id.btnViewStatus, R.id.btnCreateRooom, R.id.btnAdditionalTask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnViewStatus:
                if (checkAdditionalJobs(entity)) {
                    getDockActivity().replaceDockableFragment(ViewStatusNewFragment.newInstance(entity, true));
                } else {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.acknowledgementDialoge();
                    dialogHelper.showDialog();
                    dialogHelper.setCancelable(false);
                }
                break;
            case R.id.btnCreateRooom:
                showAddRoomDialog();
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

    private RecyclerItemListener itemClickListener = (ent, position, id) -> {
        switch (id) {
            case R.id.itemText:
                getDockActivity().replaceDockableFragment(RoomAccessoryDetailFragment.newInstance((CreateRoomEnt) ent, VisitId + ""), RoomAccessoryDetailFragment.TAG);
                break;
        }
    };


    @Override
    public void onClick(Object data, int position) {
        CreateRoomEnt entity = (CreateRoomEnt) data;
        dialogHelper.showCommonDialog(v -> {

            jobDonePosition = String.valueOf(position);
            serviceHelper.enqueueCall(webService.deleteRoom(prefHelper.getUser().getId(), entity.getId()), DeleteRoom);
            dialogHelper.hideDialog();

            dialogHelper.hideDialog();
        }, R.string.delete_room, R.string.delete_message, R.string.yes, R.string.no, true, true);
        dialogHelper.showDialog();
        dialogHelper.setCancelable(false);
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

                        if (type != null && type.equals(AppConstants.ADDITIONALJOBSUBSCRIPTION)) {
                            serviceHelper.enqueueCall(webService.getRoomDetail(VisitId), getRoomDetail);
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