package com.ingic.ezhalbatek.technician.helpers;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.MainThread;

import com.ingic.ezhalbatek.technician.entities.AccessoriesDataEnt;
import com.ingic.ezhalbatek.technician.entities.AccessoriesEnt;
import com.ingic.ezhalbatek.technician.entities.Accessory;

import java.util.ArrayList;

/**
 * Created on 6/27/18.
 */
public class SharedDataModel extends ViewModel {
    private MutableLiveData<ArrayList<Accessory>> liveAccessoriesCollections = new MutableLiveData<ArrayList<Accessory>>();
    private ArrayList<Accessory> accessoriesCollection;

    public void addAccessories(Accessory item) {
        if (accessoriesCollection==null){
            accessoriesCollection = new ArrayList<>();
        }
        accessoriesCollection.add(item);
        liveAccessoriesCollections.setValue(accessoriesCollection);
    }


    public LiveData<ArrayList<Accessory>> getAddedAccessories() {
        return liveAccessoriesCollections;
    }


    public void clearList() {
        liveAccessoriesCollections=new MutableLiveData<>();
    }

}
