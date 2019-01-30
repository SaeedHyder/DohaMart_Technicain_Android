package com.ingic.ezhalbatek.technician.fragments.orderhistory;

import android.app.DatePickerDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AllCategoriesEnt;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPackagesEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.WebServiceConstants;
import com.ingic.ezhalbatek.technician.helpers.DatePickerHelper;
import com.ingic.ezhalbatek.technician.interfaces.AppInterfaces;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.AllCategories;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.AllPackages;

/**
 * Created on 6/2/18.
 */
public class OrderHistoryFragment extends BaseFragment {
    public static final String TAG = "OrderHistoryFragment";
    @BindView(R.id.txtPendingJobCount)
    AnyTextView txtPendingJobCount;
    @BindView(R.id.txtPendingText)
    AnyTextView txtPendingText;
    @BindView(R.id.txtCompleteCount)
    AnyTextView txtCompleteCount;
    @BindView(R.id.txtCompleteText)
    AnyTextView txtCompleteText;
    @BindView(R.id.btnDate)
    TextView btnDate;
    @BindView(R.id.sp_sucscription)
    Spinner spSubscription;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.fragmentContainer)
    LinearLayout fragmentContainer;


    private AppInterfaces.DateFilterListener dateFilterListener;
    private AppInterfaces.SearchInterface searchInterface;
    private SubscriberTaskHistory subscriberTaskFragment;
    private RegisteredUserTaskHistory registeredUserTaskFragment;
    private Date DateSelected;
    private ArrayList<AllCategoriesEnt> allCategoriesCollection;
    private ArrayList<SubscriptionPackagesEnt> allPackagesCollection;

    public static OrderHistoryFragment newInstance() {
        Bundle args = new Bundle();

        OrderHistoryFragment fragment = new OrderHistoryFragment();
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
        titleBar.setSubHeading(getResString(R.string.order_history));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
      /*  btnDate.setText(dateFormat.format(date));
        DateSelected = date;*/
        btnDate.setText(getResString(R.string.select_date));


        if (prefHelper.isFromHistorySub()) {
            changeSubscriberTask();
        } else {
            changeRegisterUserTask();
        }
    }

    private void setSubscriptionSpinner() {
        serviceHelper.enqueueCall(webService.getAllPackages(), AllPackages);
    }

    private void setTaskSpinner() {
        serviceHelper.enqueueCall(webService.getAllCategories(), AllCategories);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case AllCategories:
                allCategoriesCollection = new ArrayList<>();
                AllCategoriesEnt allCategoriesEnt = new AllCategoriesEnt();
                allCategoriesEnt.setTitle(getResString(R.string.select_category));
                allCategoriesEnt.setArTitle(getResString(R.string.select_category));
                allCategoriesEnt.setId(0);
                allCategoriesCollection.add(allCategoriesEnt);
                allCategoriesCollection.addAll((ArrayList<AllCategoriesEnt>) result);

                ArrayList categoryList = new ArrayList();
                for (AllCategoriesEnt item : allCategoriesCollection) {
                    categoryList.add(item.getTitle());
                }

                ArrayAdapter<String> categoryAdapter;
                categoryAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, categoryList);

                categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spSubscription.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
                break;

            case AllPackages:
                allPackagesCollection = new ArrayList<>();
                SubscriptionPackagesEnt subscriptionPackagesEnt = new SubscriptionPackagesEnt();
                subscriptionPackagesEnt.setName(getResString(R.string.select_subscription_pakage));
                subscriptionPackagesEnt.setId(0);
                allPackagesCollection.add(subscriptionPackagesEnt);
                allPackagesCollection.addAll((ArrayList<SubscriptionPackagesEnt>) result);

                ArrayList subscriptionList = new ArrayList();
                for (SubscriptionPackagesEnt item : allPackagesCollection) {
                    subscriptionList.add(item.getName());
                }

                ArrayAdapter<String> subscriptionAdapter;
                subscriptionAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, subscriptionList);
                subscriptionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spSubscription.setAdapter(subscriptionAdapter);
                subscriptionAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void changeColorToSelected(AnyTextView txtCount, AnyTextView txtTitle) {
        txtCount.setTextColor(getDockActivity().getResources().getColor(R.color.app_red));
        txtTitle.setTextColor(getDockActivity().getResources().getColor(R.color.app_red));
    }

    private void changeColorToUnSelected(AnyTextView txtCount, AnyTextView txtTitle) {
        txtCount.setTextColor(getDockActivity().getResources().getColor(R.color.app_font_black));
        txtTitle.setTextColor(getDockActivity().getResources().getColor(R.color.app_font_black));
    }

    private void changeSubscriberTask() {
        if (subscriberTaskFragment == null) {
            subscriberTaskFragment = SubscriberTaskHistory.newInstance();
        }
        changeColorToSelected(txtPendingJobCount, txtPendingText);
        changeColorToUnSelected(txtCompleteCount, txtCompleteText);
        dateFilterListener = subscriberTaskFragment;
        searchInterface = subscriberTaskFragment;
        setSubscriptionSpinner();
        replaceFragmentOnTab(subscriberTaskFragment);
    }

    private void changeRegisterUserTask() {
        if (registeredUserTaskFragment == null) {
            registeredUserTaskFragment = RegisteredUserTaskHistory.newInstance();
        }
        changeColorToSelected(txtCompleteCount, txtCompleteText);
        changeColorToUnSelected(txtPendingJobCount, txtPendingText);
        dateFilterListener = registeredUserTaskFragment;
        searchInterface = registeredUserTaskFragment;
        setTaskSpinner();
        replaceFragmentOnTab(registeredUserTaskFragment);
    }

    private void replaceFragmentOnTab(BaseFragment frag) {
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.fragmentContainer, frag);
        transaction.commit();

    }

    private void initDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

// and get that as a Date
                        Date dateSpecified = c.getTime();
                       /* if (dateSpecified.before(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.date_before_error));
                        } else */
                        {
                            DateSelected = dateSpecified;
                            if (dateFilterListener != null) {
                                dateFilterListener.onDateFilterChange(dateSpecified);
                            }
                            if (prefHelper.isLanguageArabic())
                                textView.setText(new SimpleDateFormat("yyyy-MM-dd", new Locale("ar")).format(c.getTime()));
                            else
                                textView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime()));
                            String predate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime());

                        }

                    }
                }, "PreferredDate");
     //   datePickerHelper.setMaximumDate(new Date().getTime());
        datePickerHelper.showDate();
    }


    @OnClick({R.id.txtPendingText, R.id.txtCompleteText, R.id.btnDate, R.id.btnSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtPendingText:
                changeSubscriberTask();
                break;
            case R.id.txtCompleteText:
                changeRegisterUserTask();
                break;
            case R.id.btnDate:
                initDatePicker(btnDate);
                break;
            case R.id.btnSearch:
                if (searchInterface != null) {
                    int subscriptionId = 0;
                    int categoryId = 0;

                    if (allPackagesCollection != null && allPackagesCollection.size() > 0 && spSubscription.getSelectedItemPosition() < allPackagesCollection.size()) {
                        subscriptionId = allPackagesCollection.get(spSubscription.getSelectedItemPosition()).getId();
                    }


                    if (allCategoriesCollection != null && allCategoriesCollection.size() > 0 && spSubscription.getSelectedItemPosition() < allCategoriesCollection.size()) {
                        categoryId = allCategoriesCollection.get(spSubscription.getSelectedItemPosition()).getId();

                    }
                    searchInterface.onSearchClick(DateSelected, subscriptionId, categoryId);
                }

                break;
        }
    }


}