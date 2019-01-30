package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AccessoriesDataEnt;
import com.ingic.ezhalbatek.technician.entities.AccessoriesItemEnt;
import com.ingic.ezhalbatek.technician.entities.CreateRoomEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.helpers.SharedDataModel;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.ui.views.AnySpinner;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.AddAccessories;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.AllAccessories;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getBrands;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getModels;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getTypes;

/**
 * Created on 6/27/18.
 */
public class AddAccessoriesFragment extends BaseFragment {
    public static final String TAG = "AddAccessoriesFragment";
    @BindView(R.id.spnItem)
    AnySpinner spnItem;
    @BindView(R.id.spnBrand)
    AnySpinner spnBrand;
    @BindView(R.id.spnType)
    AnySpinner spnType;
    @BindView(R.id.spnModel)
    AnySpinner spnModel;
    @BindView(R.id.spnQuantity)
    AnySpinner spnQuantity;
    @BindView(R.id.btnDone)
    Button btnDone;
    Unbinder unbinder;
    @BindView(R.id.edtQuantity)
    EditText edtQuantity;

    private ArrayList<AccessoriesItemEnt> allAccessories;
    private ArrayList<AccessoriesItemEnt> allBrands;
    private ArrayList<AccessoriesItemEnt> allTypes;
    private ArrayList<AccessoriesItemEnt> allModels;

    private ArrayList<String> itemCollections;
    private ArrayList<String> modelCollection;
    private ArrayList<String> typeCollection;
    private ArrayList<String> brandCollection;
    private ArrayList<String> quantityCollection;

    private String accessioriesId;
    private String brandId;
    private String modelId;
    private String typeId;


    private SharedDataModel sharedDataModel;
    private static String RoomKey = "RoomKey";
    private CreateRoomEnt createRoomEntity;

    public static AddAccessoriesFragment newInstance() {
        Bundle args = new Bundle();
        AddAccessoriesFragment fragment = new AddAccessoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AddAccessoriesFragment newInstance(CreateRoomEnt roomData) {
        Bundle args = new Bundle();
        args.putString(RoomKey, new Gson().toJson(roomData));
        AddAccessoriesFragment fragment = new AddAccessoriesFragment();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //sharedDataModel = ViewModelProviders.of(getDockActivity()).get(SharedDataModel.class);
        //generateDummyData();
        serviceHelper.enqueueCall(webService.allAccessories(), AllAccessories);
        serviceHelper.enqueueCall(webService.getBrands(), getBrands);

        setSpinnerListners();

    }

    private void setSpinnerListners() {

        spnItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accessioriesId = (allAccessories.get(position).getId() + "");

                if (accessioriesId != null)
                    serviceHelper.enqueueCall(webService.getTypes(accessioriesId), getTypes);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandId = (allBrands.get(position).getId() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeId = (allTypes.get(position).getId() + "");

                if (accessioriesId != null && typeId != null)
                    serviceHelper.enqueueCall(webService.getModels(accessioriesId, typeId), getModels);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelId = (allModels.get(position).getId() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case AllAccessories:

                allAccessories = (ArrayList<AccessoriesItemEnt>) result;

                itemCollections = new ArrayList<>();
                for (AccessoriesItemEnt item : allAccessories) {
                    itemCollections.add(item.getName() + "");
                }

                ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, itemCollections);
                itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnItem.setAdapter(itemAdapter);
                spnItem.setOnItemSelectedEvenIfUnchangedListener(itemSelectListener);


                break;

            case getBrands:

                allBrands = (ArrayList<AccessoriesItemEnt>) result;

                brandCollection = new ArrayList<>();
                for (AccessoriesItemEnt item : allBrands) {
                    brandCollection.add(item.getName() + "");
                }

                ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, brandCollection);
                brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnBrand.setAdapter(brandAdapter);
                spnBrand.setOnItemSelectedEvenIfUnchangedListener(itemSelectListener);

                break;

            case getTypes:

                allTypes = (ArrayList<AccessoriesItemEnt>) result;

                typeCollection = new ArrayList<>();
                for (AccessoriesItemEnt item : allTypes) {
                    typeCollection.add(item.getName() + "");
                }

                ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, typeCollection);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnType.setAdapter(typeAdapter);
                spnType.setOnItemSelectedEvenIfUnchangedListener(itemSelectListener);


                break;

            case getModels:

                allModels = (ArrayList<AccessoriesItemEnt>) result;

                modelCollection = new ArrayList<>();
                for (AccessoriesItemEnt item : allModels) {
                    modelCollection.add(item.getName() + "");
                }

                ArrayAdapter<String> modelAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, modelCollection);
                modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnModel.setAdapter(modelAdapter);
                spnModel.setOnItemSelectedEvenIfUnchangedListener(itemSelectListener);


                break;

            case AddAccessories:

             //   AccessoriesDataEnt accessoriesDataEnt = (AccessoriesDataEnt) result;
              //  sharedDataModel.addAccessories(accessoriesDataEnt);
                getDockActivity().popFragment();
                break;

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.add_accessories));
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.FRAGMENT_RESULT_CODE) {
                UIHelper.showShortToastInCenter(getDockActivity(), "Recieve");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_accessories, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        //  sharedDataModel.addAccessories(new AccessoriesDataEnt((String) spnItem.getSelectedItem(), (String) spnQuantity.getSelectedItem()));
        if (isValidate()) {
            serviceHelper.enqueueCall(webService.addRoomAccessories(accessioriesId, createRoomEntity.getId()+"", edtQuantity.getText().toString(), createRoomEntity.getSubscriptionId()+"", createRoomEntity.getUserSubsVisitId()+"", brandId, modelId, typeId), AddAccessories);
        }

    }

    private boolean isValidate() {
        if (accessioriesId == null || accessioriesId.isEmpty() || accessioriesId.equals("")) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Accessories");
            return false;
        } else if (brandId == null || brandId.isEmpty() || brandId.equals("")) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Brand");
            return false;
        } else if (modelId == null || modelId.isEmpty() || modelId.equals("")) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Model");
            return false;
        } else if (typeId == null || typeId.isEmpty() || typeId.equals("")) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Select Type");
            return false;
        } else if (edtQuantity == null || edtQuantity.getText().toString().isEmpty() || edtQuantity.getText().toString().equals("")) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Enter Quantity");
            return false;
        } else {
            return true;
        }
    }


    private AdapterView.OnItemSelectedListener itemSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (view.getId()) {
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}