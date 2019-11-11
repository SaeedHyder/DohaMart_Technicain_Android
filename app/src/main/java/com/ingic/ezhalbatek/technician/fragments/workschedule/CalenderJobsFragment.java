package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AllJobsEnt;
import com.ingic.ezhalbatek.technician.entities.SelectedDateItem;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.DateHelper;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getAllJobs;

/**
 * Created on 6/22/18.
 */
public class CalenderJobsFragment extends BaseFragment {
    public static final String TAG = "CalenderJobsFragment";

    Unbinder unbinder;
    @BindView(R.id.calendarView)
    MaterialCalendarView calendarView;
    private ArrayList<SelectedDateItem> selectedDateList;

    public static CalenderJobsFragment newInstance() {
        Bundle args = new Bundle();

        CalenderJobsFragment fragment = new CalenderJobsFragment();
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
        titleBar.setSubHeading(getResString(R.string.work_schedule));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs_calendar, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String firstDay = dateFormatter.format(calendar.getTime());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDay = dateFormatter.format(calendar.getTime());

        serviceHelper.enqueueCall(webService.getAllJobs(prefHelper.getUser().getId() + "", firstDay, lastDay), getAllJobs);

        calendarView.setPagingEnabled(false);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            if (selectedDateList != null)
                for (SelectedDateItem selectedDate : selectedDateList
                        ) {
                    if (DateHelper.isSameDay(selectedDate.getDate(), date.getDate())) {
                        prefHelper.setIsFromRequest(false);
                        getDockActivity().replaceDockableFragment(NewJobsFragment.newInstance(dateFormatter.format(selectedDate.getDate()), selectedDate.getType()), NewJobsFragment.TAG);
                        break;
                    }
                }

        });

    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case getAllJobs:
                ArrayList<AllJobsEnt> allJobs = (ArrayList<AllJobsEnt>) result;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                selectedDateList = new ArrayList<>();

                if (allJobs.size() > 0) {
                    for (AllJobsEnt item : allJobs) {
                        try {
                            selectedDateList.add(new SelectedDateItem(formatter.parse(item.getDate()), item.getType()));
                            calendarView.setDateSelected(formatter.parse(item.getDate()), true);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;

        }
    }

}