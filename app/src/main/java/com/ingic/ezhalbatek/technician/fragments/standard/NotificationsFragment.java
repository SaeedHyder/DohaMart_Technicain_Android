package com.ingic.ezhalbatek.technician.fragments.standard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.NotificationsEnt;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.CalenderJobsFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.JobReportFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.RegisteredUserJobDetailFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.SubscriberJobDetailFragment;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.global.WebServiceConstants;
import com.ingic.ezhalbatek.technician.helpers.DialogHelper;
import com.ingic.ezhalbatek.technician.interfaces.OnLongTap;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.adapters.ArrayListAdapter;
import com.ingic.ezhalbatek.technician.ui.binders.BinderNotification;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.leolin.shortcutbadger.ShortcutBadger;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.DeleteAllNotificaiton;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.DeleteNotification;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.DeleteRoom;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.MarkUnRead;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.getNotifications;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.logout;

/**
 * Created by khan_muhammad on 9/15/2017.
 */

public class NotificationsFragment extends BaseFragment implements OnLongTap {

    @BindView(R.id.lv_notification)
    CustomRecyclerView lvNotification;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    private ArrayListAdapter<NotificationsEnt> adapter;
    private Integer jobDonePosition;

    private ArrayList<NotificationsEnt> notifications;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //   adapter = new ArrayListAdapter<>(getDockActivity(), new BinderNotification(getDockActivity(), prefHelper));
    }

   /* @Override
    public void ResponseSuccess(Object result, String Tag) {
        switch (Tag) {
            case WebServiceConstants.getNotification:
                bindData((ArrayList<NotificationEnt>) result);
                break;
            case WebServiceConstants.NotificaitonCount:
                prefHelper.setNotificationCount(0);
                break;
        }
    }*/

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showHideDeleteAllButton(true);
        titleBar.setSubHeading(getResString(R.string.notification));
        titleBar.showDeleteAllButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHelper.showCommonDialog(v -> {

                    serviceHelper.enqueueCall(webService.deleteAllNotification(prefHelper.getUser().getId()), DeleteAllNotificaiton);
                    dialogHelper.hideDialog();
                }, R.string.delete_notification_, R.string.delete_all_message, R.string.yes, R.string.no, true, true);
                dialogHelper.showDialog();
                dialogHelper.setCancelable(false);

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setNotificationCount(0);

        serviceHelper.enqueueCall(webService.getNotifications(prefHelper.getUser().getId() + ""), getNotifications);


    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case getNotifications:
                notifications = (ArrayList<NotificationsEnt>) result;
                if (notifications.size() <= 0 && getTitleBar() != null) {
                    getTitleBar().showHideDeleteAllButton(false);
                } else if (getTitleBar() != null) {
                    getTitleBar().showHideDeleteAllButton(true);
                }
                ShortcutBadger.applyCount(getDockActivity(), 0);
                bindData(notifications);
                break;


            case DeleteNotification:
                if (jobDonePosition != null) {
                    notifications.remove((int) jobDonePosition);
                    lvNotification.notifyDataSetChanged();
                    if (notifications.size() <= 0) {
                        txtNoData.setVisibility(View.VISIBLE);
                        lvNotification.setVisibility(View.GONE);
                    } else {
                        txtNoData.setVisibility(View.GONE);
                        lvNotification.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case DeleteAllNotificaiton:
                serviceHelper.enqueueCall(webService.getNotifications(prefHelper.getUser().getId() + ""), getNotifications);
                break;
        }
    }


    public void bindData(ArrayList<NotificationsEnt> result) {

        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvNotification.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvNotification.setVisibility(View.VISIBLE);
            lvNotification.bindRecyclerView(new BinderNotification(getDockActivity(), prefHelper, ItemClicklistener, this), result, new LinearLayoutManager(getDockActivity()), new DefaultItemAnimator());

        }

    }

    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.ll_mainFrame:
                NotificationsEnt entity = (NotificationsEnt) ent;

                serviceHelper.enqueueCall(webService.markUnRead(prefHelper.getUser().getId(), entity.getId()), MarkUnRead);

                if (entity.getActionType().equals(AppConstants.ADDITIONALJOBREQUEST)) {
                    getDockActivity().replaceDockableFragment(RegisteredUserJobDetailFragment.newInstance(entity.getActionId() + ""), "RegisteredUserJobDetailFragment");
                } else if (entity.getActionType().equals(AppConstants.ADDITIONALJOBSUBSCRIPTION)) {
                    getDockActivity().replaceDockableFragment(SubscriberJobDetailFragment.newInstance(entity.getActionId() + ""), "SubscriberJobDetailFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.JobPush)) {
                    getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), "CalenderJobsFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.SubscriptionPush)) {
                    getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), "CalenderJobsFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.AdditionalJobAccepted)) {
                    getDockActivity().replaceDockableFragment(RegisteredUserJobDetailFragment.newInstance(entity.getActionId() + ""), "RegisteredUserJobDetailFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.AdditionalJobRejected)) {
                    getDockActivity().replaceDockableFragment(RegisteredUserJobDetailFragment.newInstance(entity.getActionId() + ""), "RegisteredUserJobDetailFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.AdditionalJobSubscriptionAccepted)) {
                    getDockActivity().replaceDockableFragment(SubscriberJobDetailFragment.newInstance(entity.getActionId() + ""), "SubscriberJobDetailFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.AdditionalJobSubscriptionRejected)) {
                    getDockActivity().replaceDockableFragment(SubscriberJobDetailFragment.newInstance(entity.getActionId() + ""), "SubscriberJobDetailFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.JobReminder)) {
                    getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), "CalenderJobsFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.SubscriptionReminder)) {
                    getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), "CalenderJobsFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.block_user)) {
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.JobAcknowledge)) {
                    getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(entity.getActionId(), entity.getActionType()), "CalenderJobsFragment");
                   /* DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.jobCompletedDialoge();
                    if (entity.getState() != null && entity.getState().equals("0") || entity.getState().equals("1")) {
                        serviceHelper.enqueueCall(webService.getNotifications(prefHelper.getUser().getId() + ""), getNotifications);
                    }
                    dialogHelper.showDialog();*/
                } else if (entity.getActionType() != null && entity.getActionType().equals(AppConstants.SubscriptionAcknowledge)) {
                    getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(entity.getActionId(), entity.getActionType()), "CalenderJobsFragment");
                 /*   DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.jobCompletedDialoge();
                    if (entity.getState() != null && entity.getState().equals("0") || entity.getState().equals("1")) {
                        serviceHelper.enqueueCall(webService.getNotifications(prefHelper.getUser().getId() + ""), getNotifications);
                    }
                    dialogHelper.showDialog();*/
                }

                break;
        }
    });


    @Override
    public void onClick(Object entity, int position) {

        NotificationsEnt data = (NotificationsEnt) entity;
        dialogHelper.showCommonDialog(v -> {
            jobDonePosition = position;
            serviceHelper.enqueueCall(webService.deleteNotification(prefHelper.getUser().getId(), data.getId() + ""), DeleteNotification);
            dialogHelper.hideDialog();
        }, R.string.delete_notification, R.string.delete_notification_message, R.string.yes, R.string.no, true, true);
        dialogHelper.showDialog();
        dialogHelper.setCancelable(false);
    }
}
