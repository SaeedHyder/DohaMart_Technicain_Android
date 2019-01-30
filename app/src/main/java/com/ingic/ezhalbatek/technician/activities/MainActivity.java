package com.ingic.ezhalbatek.technician.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.ezhalbatek.technician.entities.HashMapEnt;
import com.ingic.ezhalbatek.technician.entities.NotificationCountEnt;
import com.ingic.ezhalbatek.technician.entities.ResponseWrapper;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;
import com.ingic.ezhalbatek.technician.fragments.standard.HomeFragment;
import com.ingic.ezhalbatek.technician.fragments.standard.LanguageSelectionFragment;
import com.ingic.ezhalbatek.technician.fragments.standard.LoginFragment;
import com.ingic.ezhalbatek.technician.fragments.standard.NotificationsFragment;
import com.ingic.ezhalbatek.technician.fragments.standard.SideMenuFragment;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.CalenderJobsFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.JobReportFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.RegisteredUserJobDetailFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.SubscriberJobDetailFragment;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.global.SideMenuChooser;
import com.ingic.ezhalbatek.technician.global.SideMenuDirection;
import com.ingic.ezhalbatek.technician.global.WebServiceConstants;
import com.ingic.ezhalbatek.technician.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.technician.helpers.DialogHelper;
import com.ingic.ezhalbatek.technician.helpers.ScreenHelper;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.retrofit.WebService;
import com.ingic.ezhalbatek.technician.retrofit.WebServiceFactory;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.utils.Orientation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends DockActivity implements OnClickListener {
    public TitleBar titleBar;
    @BindView(R.id.sideMneuFragmentContainer)
    public FrameLayout sideMneuFragmentContainer;
    @BindView(R.id.header_main)
    TitleBar header_main;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.content_frame)
    RelativeLayout contentFrame;
    private MainActivity mContext;
    private boolean loading;
    private float lastTranslate = 0.0f;

    protected BroadcastReceiver broadcastReceiver;
    private String sideMenuType;
    private String sideMenuDirection;
    private WebService webservice;
    private BasePreferenceHelper preferenceHelper;


    public HashMap<String, HashMapEnt> additionalJobsHash = new HashMap<>();

    public View getDrawerView() {
        return getLayoutInflater().inflate(getSideMenuFrameLayoutId(), null);
    }

    public void changeBackgroundResources(int resID) {
        contentFrame.setBackgroundResource(resID);
    }

    public void pickImageForUser(int count) {
        FilePickerBuilder.getInstance().setMaxCount(count)
                .enableCameraSupport(true)
                .enableVideoPicker(false)
                .enableDocSupport(false)
                .enableSelectAll(false)
                .showGifs(false)
                .withOrientation(Orientation.PORTRAIT_ONLY)
                .showFolderView(false)
                .setActivityTheme(R.style.AppTheme)
                .pickPhoto(this);
    }

    public DisplayImageOptions getImageLoaderRoundCornerTransformation(int raduis) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder).resetViewBeforeLoading(true)
                .cacheInMemory(true).cacheOnDisc(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(raduis))
                .bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    private void settingSideMenu(String type, String direction) {

        if (type.equals(SideMenuChooser.DRAWER.getValue())) {


            DisplayMetrics matrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(matrics);
            Long longwidth = Math.round(matrics.widthPixels * 0.70);
            int drawerwidth = longwidth.intValue();
            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(drawerwidth, (int) DrawerLayout.LayoutParams.MATCH_PARENT);


            if (direction.equals(SideMenuDirection.LEFT.getValue())) {
                params.gravity = Gravity.LEFT;
                sideMneuFragmentContainer.setLayoutParams(params);
            } else {
                params.gravity = Gravity.RIGHT;
                sideMneuFragmentContainer.setLayoutParams(params);
            }
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            sideMenuFragment = SideMenuFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(getSideMenuFrameLayoutId(), sideMenuFragment).commit();
            drawerLayout.setScrimColor(getResources().getColor(R.color.semi_tranparent));
            setDrawerListeners();
            drawerLayout.closeDrawers();
        } else {
          /*  resideMenu = new ResideMenu(this);
            resideMenu.attachToActivity(this);
            resideMenu.setMenuListener(getMenuListener());
            resideMenu.setScaleValue(0.52f);*/

            setMenuItemDirection(direction);
        }
    }

    public void pickImageForUser(int count, BaseFragment fragment) {
        FilePickerBuilder.getInstance().setMaxCount(count)
                .enableCameraSupport(true)
                .enableVideoPicker(false)
                .enableDocSupport(false)
                .enableSelectAll(false)
                .showGifs(false)
                .withOrientation(Orientation.PORTRAIT_ONLY)
                .showFolderView(false)
                .setActivityTheme(R.style.AppTheme)
                .pickPhoto(fragment);
    }

    private void setMenuItemDirection(String direction) {

        if (direction.equals(SideMenuDirection.LEFT.getValue())) {

            SideMenuFragment leftSideMenuFragment = SideMenuFragment.newInstance();
            //resideMenu.addMenuItem(leftSideMenuFragment, "LeftSideMenuFragment", direction);

        } else if (direction.equals(SideMenuDirection.RIGHT.getValue())) {

            SideMenuFragment rightSideMenuFragment = SideMenuFragment.newInstance();
            //resideMenu.addMenuItem(rightSideMenuFragment, "RightSideMenuFragment", direction);

        }

    }

    private int getSideMenuFrameLayoutId() {
        return R.id.sideMneuFragmentContainer;

    }


    public void initFragment() {
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        if (prefHelper.isLogin()) {
            replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
        } else {
            replaceDockableFragment(LanguageSelectionFragment.newInstance(), LanguageSelectionFragment.TAG);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String type = bundle.getString("action_type");
            final String actionId = bundle.getString("action_id");
            final String notificationId = bundle.getString("notification_id");

            if (notificationId != null && !notificationId.equals("")) {
                MarkUnReadNotification(notificationId);
            }


            if (type != null && type.equals(AppConstants.ADDITIONALJOBREQUEST)) {
                getDockActivity().replaceDockableFragment(RegisteredUserJobDetailFragment.newInstance(actionId + ""), "RegisteredUserJobDetailFragment");
            } else if (type != null && type.equals(AppConstants.ADDITIONALJOBSUBSCRIPTION)) {
                getDockActivity().replaceDockableFragment(SubscriberJobDetailFragment.newInstance(actionId + ""), "SubscriberJobDetailFragment");
            } else if (type != null && type.equals(AppConstants.JobPush)) {
                getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), "CalenderJobsFragment");
            } else if (type != null && type.equals(AppConstants.SubscriptionPush)) {
                getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), "CalenderJobsFragment");
            }else if (type != null && type.equals(AppConstants.JobAcknowledge)) {
                getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(actionId, type), "CalenderJobsFragment");
            } else if (type != null && type.equals(AppConstants.SubscriptionAcknowledge)) {
                getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(actionId, type), "CalenderJobsFragment");
            } else if (type != null && type.equals(AppConstants.AdditionalJobAccepted)) {
                getDockActivity().replaceDockableFragment(RegisteredUserJobDetailFragment.newInstance(actionId + ""), "RegisteredUserJobDetailFragment");
            } else if (type != null && type.equals(AppConstants.AdditionalJobRejected)) {
                getDockActivity().replaceDockableFragment(RegisteredUserJobDetailFragment.newInstance(actionId + ""), "RegisteredUserJobDetailFragment");
            } else if (type != null && type.equals(AppConstants.AdditionalJobSubscriptionAccepted)) {
                getDockActivity().replaceDockableFragment(SubscriberJobDetailFragment.newInstance(actionId + ""), "SubscriberJobDetailFragment");
            } else if (type != null && type.equals(AppConstants.AdditionalJobSubscriptionRejected)) {
                getDockActivity().replaceDockableFragment(SubscriberJobDetailFragment.newInstance(actionId + ""), "SubscriberJobDetailFragment");
            } else if (type != null && type.equals(AppConstants.JobReminder)) {
                getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), "CalenderJobsFragment");
            } else if (type != null && type.equals(AppConstants.SubscriptionReminder)) {
                getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(), "CalenderJobsFragment");
            } else if (type != null && type.equals(AppConstants.block_user)) {
              /*  prefHelper.setLoginStatus(false);
                getDockActivity().popBackStackTillEntry(0);
                replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");*/
            }


        }
    }

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    System.out.println("registration complete");
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String type = bundle.getString("action_type");
                        final String actionId = bundle.getString("action_id");
                        final String notificationId = bundle.getString("notification_id");

                        if (notificationId != null && !notificationId.equals("")) {
                            MarkUnReadNotification(notificationId);
                        }

                        if (type != null && type.equals(AppConstants.ADDITIONALJOBREQUEST)) {

                        } else if (type != null && type.equals(AppConstants.ADDITIONALJOBSUBSCRIPTION)) {

                        } else if (type != null && type.equals(AppConstants.JobPush)) {

                        } else if (type != null && type.equals(AppConstants.SubscriptionPush)) {

                        } else if (type != null && type.equals(AppConstants.JobAcknowledge)) {
                            DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                            dialogHelper.jobCompletedDialoge();
                            dialogHelper.showDialog();
                        } else if (type != null && type.equals(AppConstants.SubscriptionAcknowledge)) {
                            DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                            dialogHelper.jobCompletedDialoge();
                            dialogHelper.showDialog();
                        } /*else if (type != null && type.equals(AppConstants.JobAcknowledge)) {
                            getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(actionId, type), "CalenderJobsFragment");
                        } else if (type != null && type.equals(AppConstants.SubscriptionAcknowledge)) {
                            getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(actionId, type), "CalenderJobsFragment");
                        }*/ else if (type != null && type.equals(AppConstants.block_user)) {

                            DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                            dialogHelper.BlockAccountDialoge(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogHelper.hideDialog();
                                }
                            });
                            dialogHelper.showDialog();

                            Logout();

                        }
                    }
                }
            }

        };
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    BaseFragment currFrag = (BaseFragment) manager.findFragmentById(getDockFrameLayoutId());
                    if (currFrag != null) {
                        currFrag.fragmentResume();
                    }
                }
            }
        };

        return result;
    }

    @Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            loading = true;
        }
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (progressBar != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        loading = false;

    }

    @Override
    public void onProgressUpdated(int percentLoaded) {

    }

    public void lockDrawer() {
        try {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    private void setDrawerListeners() {
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        final DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.MATCH_PARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                contentFrame.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        titleBar = header_main;
        changeBackgroundResources(R.drawable.bg);
        preferenceHelper = new BasePreferenceHelper(getDockActivity());
        // setBehindContentView(R.layout.fragment_frame);
        mContext = this;
        Log.i("Screen Density", ScreenHelper.getDensity(this) + "");

        sideMenuType = SideMenuChooser.DRAWER.getValue();
        sideMenuDirection = SideMenuDirection.LEFT.getValue();

        settingSideMenu(sideMenuType, sideMenuDirection);

        titleBar.setMenuButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sideMenuType.equals(SideMenuChooser.DRAWER.getValue()) && getDrawerLayout() != null) {
                    if (sideMenuDirection.equals(SideMenuDirection.LEFT.getValue())) {
                        drawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    //resideMenu.openMenu(sideMenuDirection);
                }

            }
        });

        titleBar.setBackButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {

                    popFragment();
                    UIHelper.hideSoftKeyboard(getApplicationContext(),
                            titleBar);
                }
            }
        });

        titleBar.setNotificationButtonListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        });

        onNotificationReceived();
        //  if (savedInstanceState == null)
        initFragment();

    }

    @Override
    public void onBackPressed() {
        if (loading) {
            UIHelper.showLongToastInCenter(getApplicationContext(),
                    R.string.message_wait);
        } else
            super.onBackPressed();

    }

    @Override
    public void onMenuItemActionCalled(int actionId, String data) {

    }

    @Override
    public void setSubHeading(String subHeadText) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void hideHeaderButtons(boolean leftBtn, boolean rightBtn) {
    }

    private void notImplemented() {
        UIHelper.showLongToastInCenter(this, "Coming Soon");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showSeprator(int visibility) {
        seprator.setVisibility(visibility);
    }

    public HashMap<String, HashMapEnt> getAdditionalJobsHash() {
        return additionalJobsHash;
    }

    public void setAdditionalJobsHash(HashMap<String, HashMapEnt> additionalJobsHash) {
        this.additionalJobsHash = additionalJobsHash;
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

       /* if (!prefHelper.isLogin() && !prefHelper.isBeforeLogin()) {
            replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
        }*/

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));


    }

    private void MarkUnReadNotification(String notificationId) {
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.Local_SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(this);
        Call<ResponseWrapper> call = webservice.markUnRead(preferenceHelper.getUser().getId(), Integer.parseInt(notificationId));
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

            }
        });
    }

    private void Logout() {
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(this);
        Call<ResponseWrapper> call = webservice.logout(preferenceHelper.getUser().getId() + "", FirebaseInstanceId.getInstance().getToken());
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                prefHelper.setLoginStatus(false);
                getDockActivity().popBackStackTillEntry(0);
                replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

            }
        });
    }
}
