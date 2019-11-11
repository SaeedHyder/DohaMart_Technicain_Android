package com.ingic.ezhalbatek.technician.fragments.workschedule;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getRequestJobs;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getSubscriptionJobs;

/**
 * Created on 6/22/18.
 */
public class NewJobsFragment extends BaseFragment {
    public static final String TAG = "NewJobsFragment";
    @BindView(R.id.btnSubscriber)
    AnyTextView btnSubscriber;
    @BindView(R.id.btnPendingJobs)
    LinearLayout btnPendingJobs;
    @BindView(R.id.btnRegisted)
    AnyTextView btnRegisted;
    @BindView(R.id.btnCompleteJobs)
    LinearLayout btnCompleteJobs;
    @BindView(R.id.fragmentContainer)
    LinearLayout fragmentContainer;
    Unbinder unbinder;

    private static String dateKey = "dateKey";
    private static String typeKey = "typeKey";
    private ArrayList<UserPaymentEnt> requestJobs;
    private ArrayList<SubscriptionPaymentEnt> subscriptionJobs;

    public static NewJobsFragment newInstance() {
        Bundle args = new Bundle();

        NewJobsFragment fragment = new NewJobsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static NewJobsFragment newInstance(String date, String type) {
        Bundle args = new Bundle();
        args.putString(dateKey, date);
        args.putString(typeKey, type);
        NewJobsFragment fragment = new NewJobsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dateKey = getArguments().getString(dateKey);
            typeKey = getArguments().getString(typeKey);

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
        View view = inflater.inflate(R.layout.fragment_new_jobs, container, false);
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

        if(prefHelper.isRequest()){
            changeColorToSelected(btnRegisted);
            changeColorToUnSelected(btnSubscriber);
            replaceFragmentOnTab(RegisteredUserNewJobsFragment.newInstance(dateKey));
         //   serviceHelper.enqueueCall(webService.getNewrRquestJobs(prefHelper.getUser().getId() + "", dateKey), getRequestJobs);
        }else{
            changeColorToSelected(btnSubscriber);
            changeColorToUnSelected(btnRegisted);
            replaceFragmentOnTab(SubscriberNewJobsFragment.newInstance(dateKey));
      //      serviceHelper.enqueueCall(webService.getNewrRquestJobs(prefHelper.getUser().getId() + "", dateKey), getRequestJobs);
      //      serviceHelper.enqueueCall(webService.getNewSubscriptionJobs(prefHelper.getUser().getId() + "", dateKey), getSubscriptionJobs);
        }



    }

    /*@Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case getRequestJobs:
                requestJobs = (ArrayList<UserPaymentEnt>) result;

                break;
            case getSubscriptionJobs:

                subscriptionJobs = (ArrayList<SubscriptionPaymentEnt>) result;
                replaceFragmentOnTab(SubscriberNewJobsFragment.newInstance(subscriptionJobs));
                break;
        }
    }*/

    private void changeColorToSelected(AnyTextView txtTitle) {
        txtTitle.setTextColor(getDockActivity().getResources().getColor(R.color.app_red));
    }

    private void changeColorToUnSelected(AnyTextView txtTitle) {
        txtTitle.setTextColor(getDockActivity().getResources().getColor(R.color.app_font_black));
    }

    private void replaceFragmentOnTab(BaseFragment frag) {
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.fragmentContainer, frag);
        transaction.commit();

    }

    @OnClick({R.id.btnSubscriber, R.id.btnRegisted})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubscriber:

                changeColorToSelected(btnSubscriber);
                changeColorToUnSelected(btnRegisted);
                replaceFragmentOnTab(SubscriberNewJobsFragment.newInstance(dateKey));
              //  replaceFragmentOnTab(SubscriberNewJobsFragment.newInstance(subscriptionJobs));
                break;
            case R.id.btnRegisted:
                changeColorToSelected(btnRegisted);
                changeColorToUnSelected(btnSubscriber);
                replaceFragmentOnTab(RegisteredUserNewJobsFragment.newInstance(dateKey));
              //  replaceFragmentOnTab(RegisteredUserNewJobsFragment.newInstance(requestJobs));
                break;
        }
    }
}