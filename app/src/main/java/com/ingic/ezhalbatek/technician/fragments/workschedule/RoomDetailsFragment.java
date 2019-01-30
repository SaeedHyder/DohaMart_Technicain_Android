package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AccessoriesDataEnt;
import com.ingic.ezhalbatek.technician.entities.Accessory;
import com.ingic.ezhalbatek.technician.entities.CreateRoomEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.SharedDataModel;
import com.ingic.ezhalbatek.technician.ui.binders.RoomAccessoriesBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.GetRoomAccessories;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.markDoneRoom;

/**
 * Created on 6/27/18.
 */
public class RoomDetailsFragment extends BaseFragment {
    public static final String TAG = "RoomDetailsFragment";
    @BindView(R.id.txtItem)
    AnyTextView txtItem;
    @BindView(R.id.txtQuantity)
    AnyTextView txtQuantity;
    @BindView(R.id.rvAccessories)
    CustomRecyclerView rvAccessories;
    @BindView(R.id.btnAddAccessories)
    Button btnAddAccessories;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    Unbinder unbinder;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;

    private static String roomName = "";
    private ArrayList<Accessory> accessoriesCollection = new ArrayList<>();
    private SharedDataModel sharedDataModel;
    private static String RoomKey = "RoomKey";
    private CreateRoomEnt createRoomEntity;

    public static RoomDetailsFragment newInstance(CreateRoomEnt roomData,String name) {
        Bundle args = new Bundle();
        args.putString(RoomKey, new Gson().toJson(roomData));
        roomName=name;
        RoomDetailsFragment fragment = new RoomDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(RoomKey);
            if (jsonString != null) {
                createRoomEntity = new Gson().fromJson(jsonString, CreateRoomEnt.class);
            }
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(roomName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainFrame.setVisibility(View.GONE);
        serviceHelper.enqueueCall(webService.getRoomAccessories(createRoomEntity.getId() + ""), GetRoomAccessories);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {

            case GetRoomAccessories:
                ArrayList<Accessory> response = (ArrayList<Accessory>) result;
                mainFrame.setVisibility(View.VISIBLE);
                bindAccessories(response);
                break;
        }
    }

    private void bindAccessories(ArrayList<Accessory> RoomAccessories) {

        if (RoomAccessories.size() > 0) {
            rlTitle.setVisibility(View.VISIBLE);
            accessoriesCollection=new ArrayList<>();
            accessoriesCollection.addAll(RoomAccessories);
            rvAccessories.bindRecyclerView(new RoomAccessoriesBinder(), accessoriesCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        } else {
            rlTitle.setVisibility(View.GONE);
            accessoriesCollection = new ArrayList<>();
            rvAccessories.bindRecyclerView(new RoomAccessoriesBinder(), accessoriesCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        }

        // sharedDataModel = ViewModelProviders.of(getDockActivity()).get(SharedDataModel.class);

       /* if (createRoomEntity.getSubscriptionRoomsAccessories().size() > 0) {
            rlTitle.setVisibility(View.VISIBLE);
            accessoriesCollection.addAll(createRoomEntity.getSubscriptionRoomsAccessories());
            rvAccessories.bindRecyclerView(new RoomAccessoriesBinder(), accessoriesCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        }else{
            rlTitle.setVisibility(View.GONE);
            accessoriesCollection=new ArrayList<>();
            rvAccessories.bindRecyclerView(new RoomAccessoriesBinder(), accessoriesCollection, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());
        }

       sharedDataModel.getAddedAccessories().removeObservers(this);
        sharedDataModel.getAddedAccessories().observe(this, item -> {
            if (accessoriesCollection != null) {
                accessoriesCollection.addAll(item);
                rlTitle.setVisibility(View.VISIBLE);
                rvAccessories.notifyDataSetChanged();
            }
        });*/
    }


    @OnClick({R.id.btnAddAccessories, R.id.btnUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddAccessories:
                getDockActivity().replaceDockableFragment(AddAccessoriesFragment.newInstance(createRoomEntity), AddAccessoriesFragment.TAG);
                break;
            case R.id.btnUpdate:
                getDockActivity().popFragment();
                break;
        }
    }


}