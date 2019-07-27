package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.arch.lifecycle.ViewModelProviders;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AccessoriesDataEnt;
import com.ingic.ezhalbatek.technician.entities.Accessory;
import com.ingic.ezhalbatek.technician.entities.AccessoryItemDetailCustom;
import com.ingic.ezhalbatek.technician.entities.AdditionalJobMarkDone;
import com.ingic.ezhalbatek.technician.entities.CreateRoomEnt;
import com.ingic.ezhalbatek.technician.entities.HashMapEnt;
import com.ingic.ezhalbatek.technician.entities.MarkRoomDone;
import com.ingic.ezhalbatek.technician.entities.TaskEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.WebServiceConstants;
import com.ingic.ezhalbatek.technician.helpers.SharedDataModel;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.adapters.ArrayListExpandableAdapter;
import com.ingic.ezhalbatek.technician.ui.binders.AccessoriesExpendableBinder;
import com.ingic.ezhalbatek.technician.ui.binders.AddedTaskBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.GetRoomAccessories;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.markDoneRoom;

/**
 * Created on 6/27/18.
 */
public class RoomAccessoryDetailFragment extends BaseFragment {
    public static final String TAG = "RoomAccessoryDetailFragment";
    @BindView(R.id.expJobs)
    ExpandableListView expJobs;
    @BindView(R.id.btnAddAccessory)
    Button btnAddAccessory;
    @BindView(R.id.edtBody)
    AnyEditTextView edtBody;
    @BindView(R.id.edtPrice)
    AnyEditTextView edtPrice;
    @BindView(R.id.btnAddTask)
    LinearLayout btnAddTask;
    @BindView(R.id.rvAddtionalJobs)
    CustomRecyclerView rvAddtionalJobs;
    Unbinder unbinder;
    @BindView(R.id.txtJobs)
    AnyTextView txtJobs;
    @BindView(R.id.txtStatus)
    AnyTextView txtStatus;


    private static String RoomKey = "RoomKey";
    @BindView(R.id.txtItem)
    AnyTextView txtItem;
    @BindView(R.id.ll_textViews)
    LinearLayout llTextViews;
    @BindView(R.id.task)
    AnyTextView task;
    @BindView(R.id.btnDone)
    Button btnDone;
    @BindView(R.id.txt_currency_code)
    AnyTextView txtCurrencyCode;
    private CreateRoomEnt createRoomEntity;
    private SharedDataModel sharedDataModel;

    private ArrayListExpandableAdapter<Accessory, AccessoryItemDetailCustom> adapter;
    private ArrayList<Accessory> collectionGroup;
    private ArrayList<AccessoryItemDetailCustom> collectionChild;
    private HashMap<Accessory, ArrayList<AccessoryItemDetailCustom>> listDataChild;

    private ArrayList<TaskEnt> taskCollection;
    private static String VisitId = "";


    public static RoomAccessoryDetailFragment newInstance() {
        Bundle args = new Bundle();

        RoomAccessoryDetailFragment fragment = new RoomAccessoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static RoomAccessoryDetailFragment newInstance(CreateRoomEnt roomData, String id) {
        Bundle args = new Bundle();
        args.putString(RoomKey, new Gson().toJson(roomData));
        VisitId = id;
        RoomAccessoryDetailFragment fragment = new RoomAccessoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static RoomAccessoryDetailFragment newInstance(CreateRoomEnt roomData) {
        Bundle args = new Bundle();
        args.putString(RoomKey, new Gson().toJson(roomData));
        VisitId = "";
        RoomAccessoryDetailFragment fragment = new RoomAccessoryDetailFragment();
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
        titleBar.setSubHeading(getResString(R.string.accessories));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_accessory_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(VisitId.equals("")){
            btnDone.setVisibility(View.GONE);
            txtStatus.setVisibility(View.GONE);
        }
        serviceHelper.enqueueCall(webService.getRoomAccessories(createRoomEntity.getId() + ""), GetRoomAccessories);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case markDoneRoom:
                getDockActivity().popFragment();
                break;

            case GetRoomAccessories:
                ArrayList<Accessory> response =(ArrayList<Accessory>)result;
                bindAccessories(response);
                break;
        }
    }


    private void bindAccessories(ArrayList<Accessory> RoomAccessories) {

        sharedDataModel = ViewModelProviders.of(getDockActivity()).get(SharedDataModel.class);

        txtCurrencyCode.setText(getDockActivity().getResources().getString(R.string.qar)+" ");

        if (RoomAccessories.size()> 0) {

            llTextViews.setVisibility(View.VISIBLE);
            collectionGroup = new ArrayList<>();
            listDataChild = new HashMap<>();

            collectionChild = new ArrayList<>();


            for (Accessory itemData : RoomAccessories) {
                collectionGroup.add(itemData);

                for (AccessoriesDataEnt item : itemData.getRoomAccessories()) {
                    collectionChild.add(new AccessoryItemDetailCustom(item.getBrand() != null && item.getBrand().getName() != null && !item.getBrand().getName().equals("") ? item.getBrand().getName() : "",
                            item.getType() != null && item.getType().getName() != null && !item.getType().getName().equals("") ? item.getType().getName() : "",
                            item.getItemModel() != null && item.getItemModel().getName() != null && !item.getItemModel().getName().equals("") ? item.getItemModel().getName() : "",
                            item.getQuantity() != null && !item.getQuantity().equals("") ? item.getQuantity() : ""));
                }
                listDataChild.put(itemData, collectionChild);

                collectionChild = new ArrayList<>();
            }

            adapter = new ArrayListExpandableAdapter<>(getDockActivity(), collectionGroup, listDataChild, new AccessoriesExpendableBinder(getDockActivity(),VisitId));
            expJobs.setAdapter(adapter);
            adapter.notifyDataSetChanged();

           /* sharedDataModel.getAddedAccessories().removeObservers(this);
            sharedDataModel.getAddedAccessories().observe(this, data -> {

                if (data != null && data.size() > 0) {
                    for (Accessory itemData : data) {
                        collectionGroup.add(itemData);
                        for (AccessoriesDataEnt item : itemData.getRoomAccessories()) {
                            collectionChild.add(new AccessoryItemDetailCustom(item.getBrand() != null && item.getBrand().getName() != null && !item.getBrand().getName().equals("") ? item.getBrand().getName() : "",
                                    item.getType() != null && item.getType().getName() != null && !item.getType().getName().equals("") ? item.getType().getName() : "",
                                    item.getItemModel() != null && item.getItemModel().getName() != null && !item.getItemModel().getName().equals("") ? item.getItemModel().getName() : "",
                                    item.getQuantity() != null && !item.getQuantity().equals("") ? item.getQuantity() : ""));
                        }
                        listDataChild.put(itemData, collectionChild);
                        collectionChild = new ArrayList<>();
                    }
                }
            });*/


        } else {
            llTextViews.setVisibility(View.GONE);

            collectionGroup = new ArrayList<>();
            listDataChild = new HashMap<>();

            collectionChild = new ArrayList<>();

           /* sharedDataModel.getAddedAccessories().removeObservers(this);
            sharedDataModel.getAddedAccessories().observe(this, data -> {

                if (data != null && data.size() > 0) {
                    for (Accessory itemData : data) {
                        collectionGroup.add(itemData);
                        for (AccessoriesDataEnt item : itemData.getRoomAccessories()) {
                            collectionChild.add(new AccessoryItemDetailCustom(item.getBrand() != null && item.getBrand().getName() != null && !item.getBrand().getName().equals("") ? item.getBrand().getName() : "",
                                    item.getType() != null && item.getType().getName() != null && !item.getType().getName().equals("") ? item.getType().getName() : "",
                                    item.getItemModel() != null && item.getItemModel().getName() != null && !item.getItemModel().getName().equals("") ? item.getItemModel().getName() : "",
                                    item.getQuantity() != null && !item.getQuantity().equals("") ? item.getQuantity() : ""));
                        }
                        listDataChild.put(itemData, collectionChild);

                        collectionChild = new ArrayList<>();
                    }
                }

            });*/
            adapter = new ArrayListExpandableAdapter<>(getDockActivity(), collectionGroup, listDataChild, new AccessoriesExpendableBinder(getDockActivity(),""));
            expJobs.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        sharedDataModel.clearList();
        taskCollection = new ArrayList<>();

        expJobs.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            setListViewHeight(parent, groupPosition);
            return false;
        });
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    @OnClick({R.id.btnAddAccessory, R.id.btnAddTask, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddAccessory:
                getDockActivity().replaceDockableFragment(AddAccessoriesFragment.newInstance(createRoomEntity), AddAccessoriesFragment.TAG);
                break;
            case R.id.btnDone:

                if (collectionGroup.size() > 0) {
                    if (isChecked()) {

                        List<AdditionalJobMarkDone> additionalJobs = new ArrayList<>();

                        if (taskCollection != null && taskCollection.size() > 0) {
                            for (TaskEnt item : taskCollection) {
                                additionalJobs.add(new AdditionalJobMarkDone(item.getId(), item.getQuantity()));
                            }
                        }
                        MarkRoomDone markRoomDone = new MarkRoomDone(createRoomEntity.getId() + "", prefHelper.getUser().getId() + "",
                                VisitId, additionalJobs);
                        serviceHelper.enqueueCall(webService.markRoomDone(markRoomDone), markDoneRoom);

                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.select_all_accessories));
                    }
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.add_accessoires));
                }
                break;

        }
    }

    private boolean isChecked() {

        for (Accessory item : collectionGroup) {
            if (!item.isChecked()) {
                return false;
            }
        }
        return true;
    }




}