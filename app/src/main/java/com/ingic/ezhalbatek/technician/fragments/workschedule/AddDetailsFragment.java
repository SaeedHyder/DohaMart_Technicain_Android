package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.CreateRoomEnt;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.WebServiceConstants;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.helpers.DialogHelper;
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

/**
 * Created on 6/25/18.
 */
public class AddDetailsFragment extends BaseFragment implements OnLongTap {
    public static final String TAG = "AddDetailsFragment";
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
    @BindView(R.id.rvRooms)
    CustomRecyclerView rvRooms;
    @BindView(R.id.btnMoreRoom)
    Button btnMoreRoom;
    @BindView(R.id.btnCreate)
    Button btnCreate;
    @BindView(R.id.btnDone)
    Button btnDone;
    Unbinder unbinder;
    @BindView(R.id.txtMonthly)
    AnyTextView txtMonthly;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;

    private ArrayList<CreateRoomEnt> roomAdded;
    private static String AddDetailKey = "AddDetailKey";
    private static String AddDetailTaskKey = "AddDetailTaskKey";
    private SubscriptionPaymentEnt entity;
    private UserPaymentEnt entityTask;
    private String jobDonePosition;


    public static AddDetailsFragment newInstance() {
        Bundle args = new Bundle();

        AddDetailsFragment fragment = new AddDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddDetailsFragment newInstance(SubscriptionPaymentEnt data) {
        Bundle args = new Bundle();
        args.putString(AddDetailKey, new Gson().toJson(data));
        AddDetailsFragment fragment = new AddDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddDetailsFragment newInstance(UserPaymentEnt data) {
        Bundle args = new Bundle();
        args.putString(AddDetailTaskKey, new Gson().toJson(data));
        AddDetailsFragment fragment = new AddDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(AddDetailKey);
            String jsonTaskString = getArguments().getString(AddDetailTaskKey);
            if (jsonString != null) {
                entity = new Gson().fromJson(jsonString, SubscriptionPaymentEnt.class);
            }
            if (jsonTaskString != null) {
                entityTask = new Gson().fromJson(jsonTaskString, UserPaymentEnt.class);
            }
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.details));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_details, container, false);
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

        prefHelper.setIsFromRequest(false);
        setData();
        btnMoreRoom.setVisibility(View.VISIBLE);
        btnDone.setVisibility(View.VISIBLE);
        btnCreate.setVisibility(View.GONE);

        rvRooms.setNestedScrollingEnabled(false);
        roomAdded = new ArrayList<>();

        if (entity != null && entity.getSubscriptionRooms().size() > 0) {
            txtMonthly.setVisibility(View.VISIBLE);
            roomAdded.addAll(entity.getSubscriptionRooms());
            rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity(), this,null), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        } else if (entityTask != null) {
            mainFrame.setVisibility(View.GONE);
            serviceHelper.enqueueCall(webService.getRequestData(prefHelper.getUser().getId(), entityTask.getId()+""), GetRequestData);
        } else {
            txtMonthly.setVisibility(View.GONE);
            rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity(), this,null), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        }

       /* if (entity != null && entity.getSubscriptionRooms().size() > 0) {
            txtMonthly.setVisibility(View.VISIBLE);
            roomAdded.addAll(entity.getSubscriptionRooms());
            rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity()), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        } else if (entityTask != null && entityTask.getSubscriptionRooms().size() > 0) {
            txtMonthly.setVisibility(View.VISIBLE);
            roomAdded.addAll(entityTask.getSubscriptionRooms());
            rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity()), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        } else {
            txtMonthly.setVisibility(View.GONE);
            rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity()), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        }*/
    }

    private void setData() {

        if (entity != null && entity.getUser() != null) {
            txtSubscriptionID.setText(entity.getUser().getId() + "");
            txtCustomerName.setText(entity.getUser().getFullName() + "");
            txtSubscriptionPackage.setText(entity.getUser().getFullAddress() + "");
            txtMobileNo.setText(entity.getUser().getCountryCode()+entity.getUser().getPhoneNo() + "");
            txtDate.setText(DateHelper.dateFormat(entity.getVisitDate(), "MMM dd,yyyy", "yyyy-MM-dd") + "");
        } else if (entityTask != null && entityTask.getUserDetail() != null) {
            txtSubscriptionID.setText(entityTask.getUserDetail().getId() + "");
            txtCustomerName.setText(entityTask.getUserDetail().getFullName() + "");
            txtSubscriptionPackage.setText(entityTask.getUserDetail().getFullAddress() + "");
            txtMobileNo.setText(entityTask.getUserDetail().getPhoneNo() + "");
            txtDate.setText(DateHelper.dateFormat(entityTask.getDate(), "MMM dd,yyyy", "yyyy-MM-dd") + "");
        }
    }


    @OnClick({R.id.btnMoreRoom, R.id.btnCreate, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMoreRoom:
                showAddRoomDialog();
                break;
            case R.id.btnCreate:
                showAddRoomDialog();
                break;
            case R.id.btnDone:
                getDockActivity().popFragment();
                break;
        }
    }

    private void showAddRoomDialog() {
        dialogHelper.showCommonDialog(v -> {

            if (entity != null && entity.getUserId() != null) {
                if(dialogHelper.getEditableText()!=null && !dialogHelper.getEditableText().trim().equals("")) {
                    serviceHelper.enqueueCall(webService.createRoom(entity.getUserId() + "", dialogHelper.getEditableText(), entity.getId() + "", null), createRoom);
                    dialogHelper.hideDialog();
                }else{
                    dialogHelper.getEditTextView().setError(getResString(R.string.room_text_error));
                }
            }
            if (entityTask != null && entityTask.getUserId() != null) {
                if(dialogHelper.getEditableText()!=null && !dialogHelper.getEditableText().trim().equals("")) {
                serviceHelper.enqueueCall(webService.createRoom(entityTask.getUserId() + "", dialogHelper.getEditableText(),"",entityTask.getId()+""), createRoom);
                    dialogHelper.hideDialog();
                }else{
                    dialogHelper.getEditTextView().setError(getResString(R.string.room_text_error));
                }
            }
            //   serviceHelper.enqueueCall(webService.createRoom(dialogHelper.getEditableText(), entity.getSubscriptionId(), entity.getId() + ""), createRoom);


        }, R.string.create_room, R.string.ok, R.string.empty, false, true, false);
        dialogHelper.showDialog();
        dialogHelper.setCancelable(false);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {

            case createRoom:
                CreateRoomEnt ent = (CreateRoomEnt) result;
                txtMonthly.setVisibility(View.VISIBLE);
                roomAdded.add(ent);
                rvRooms.notifyDataSetChanged();
                btnMoreRoom.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
                btnCreate.setVisibility(View.GONE);

                if (entityTask != null)
                    entityTask.getSubscriptionRooms().add(ent);

                break;

            case GetRequestData:
                UserPaymentEnt response = (UserPaymentEnt) result;
                mainFrame.setVisibility(View.VISIBLE);
                roomAdded = new ArrayList<>();
                if (response.getSubscriptionRooms().size() > 0) {
                    txtMonthly.setVisibility(View.VISIBLE);
                    roomAdded.addAll(response.getSubscriptionRooms());
                    rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity(), this,null), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
                } else {
                    txtMonthly.setVisibility(View.GONE);
                    rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity(), this,null), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
                }

                break;

            case DeleteRoom:
                if (jobDonePosition != null) {
                    roomAdded.remove(Integer.parseInt(jobDonePosition));
                    rvRooms.bindRecyclerView(new AddRoomBinder(itemClickListener, getDockActivity(), this,null), roomAdded, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
                }
                break;

        }
    }

    private RecyclerItemListener itemClickListener = (ent, position, id) -> {
        switch (id) {
            case R.id.itemText:
                getDockActivity().replaceDockableFragment(RoomAccessoryDetailFragment.newInstance((CreateRoomEnt) ent), RoomAccessoryDetailFragment.TAG);

             //   getDockActivity().replaceDockableFragment(RoomDetailsFragment.newInstance((CreateRoomEnt) ent,((CreateRoomEnt)ent).getName()+""), RoomDetailsFragment.TAG);
                break;
        }
    };

    @Override
    public void onClick(Object data, int position) {
        CreateRoomEnt entity=(CreateRoomEnt)data;
        dialogHelper.showCommonDialog(v -> {
            jobDonePosition = String.valueOf(position);
            serviceHelper.enqueueCall(webService.deleteRoom(prefHelper.getUser().getId(),entity.getId()), DeleteRoom);
            dialogHelper.hideDialog();
        }, R.string.delete_room, R.string.delete_message, R.string.yes, R.string.no, true, true);
        dialogHelper.showDialog();
        dialogHelper.setCancelable(false);
    }
}