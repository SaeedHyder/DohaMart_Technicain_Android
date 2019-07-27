package com.ingic.ezhalbatek.technician.fragments.payments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.interfaces.AppInterfaces;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.SubscriberTaskBinder;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.SubscriptionPayment;

/**
 * Created on 6/6/18.
 */
public class SubscriberTaskFragment extends BaseFragment implements AppInterfaces.DateFilterListener, AppInterfaces.SearchInterface {
    public static final String TAG = "SubscriberTaskFragment";
    @BindView(R.id.rvSubscriber)
    CustomRecyclerView rvSubscriber;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;


    public static SubscriberTaskFragment newInstance() {
        Bundle args = new Bundle();

        SubscriberTaskFragment fragment = new SubscriberTaskFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriber_tasks, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefHelper.setIsFromPaymentSubscriber(true);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        serviceHelper.enqueueCall(webService.subscriptionPayment(prefHelper.getUser().getId(), null, null, null), SubscriptionPayment);
        rvSubscriber.setNestedScrollingEnabled(false);

        //   serviceHelper.enqueueCall(webService.subscriptionPayment(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date)), SubscriptionPayment);


    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case SubscriptionPayment:
                ArrayList<SubscriptionPaymentEnt> subscriptionPaymentEnt = (ArrayList<SubscriptionPaymentEnt>) result;

                if (subscriptionPaymentEnt.size() > 0) {
                    rvSubscriber.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    rvSubscriber.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }

                rvSubscriber.bindRecyclerView(new SubscriberTaskBinder(ItemClicklistener), subscriptionPaymentEnt,
                        new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
                break;
        }
    }

    @Override
    public void onDateFilterChange(Date date) {
       /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        serviceHelper.enqueueCall(webService.subscriptionPayment(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(date)), SubscriptionPayment);*/
    }

    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btnViewDetail:
                SubscriptionPaymentEnt subscriberData = (SubscriptionPaymentEnt) ent;
                getDockActivity().replaceDockableFragment(TaskDetailFragment.newInstance(subscriberData, true), TaskDetailFragment.TAG);
                break;
        }
    });

    @Override
    public void onSearchClick(Date date,Date endDate, int subscriptionId, int categoryId) {
        if (subscriptionId != 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            if (date != null) {
                serviceHelper.enqueueCall(webService.subscriptionPayment(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(endDate), subscriptionId + ""), SubscriptionPayment);
            } else {
                serviceHelper.enqueueCall(webService.subscriptionPayment(prefHelper.getUser().getId(), null, null, subscriptionId + ""), SubscriptionPayment);
            }
        } else {
            //    UIHelper.showShortToastInCenter(getDockActivity(), "Please select subscription");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            if (date != null) {
                serviceHelper.enqueueCall(webService.subscriptionPayment(prefHelper.getUser().getId(), dateFormat.format(date), dateFormat.format(endDate), null), SubscriptionPayment);
            } else {
                serviceHelper.enqueueCall(webService.subscriptionPayment(prefHelper.getUser().getId(), null, null, null), SubscriptionPayment);
            }
        }

    }
}