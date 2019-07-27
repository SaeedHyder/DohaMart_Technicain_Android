package com.ingic.ezhalbatek.technician.interfaces;

import java.util.Date;

/**
 * Created on 6/19/18.
 */
public class AppInterfaces {
    public interface DateFilterListener{
        void onDateFilterChange(Date date);
    }

    public interface SpinnerInterface{
        void onSpinnerSelected();
    }

    public interface SearchInterface{
        void onSearchClick(Date dateSelected,Date endDateSelected, int subscriptionId,int categoryId);
    }


}
