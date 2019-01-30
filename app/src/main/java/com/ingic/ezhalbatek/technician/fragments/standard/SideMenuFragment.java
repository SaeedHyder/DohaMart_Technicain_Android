package com.ingic.ezhalbatek.technician.fragments.standard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.AdminEnt;
import com.ingic.ezhalbatek.technician.entities.NavigationEnt;
import com.ingic.ezhalbatek.technician.fragments.workschedule.AdditionalTaskFragment;
import com.ingic.ezhalbatek.technician.fragments.workschedule.CalenderJobsFragment;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.WebServiceConstants;
import com.ingic.ezhalbatek.technician.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.technician.ui.binders.NavigationBinder;
import com.ingic.ezhalbatek.technician.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.ADMIN;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.changePassword;
import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.logout;

public class SideMenuFragment extends BaseFragment {
    @BindView(R.id.rv_nav)
    CustomRecyclerView rvNav;
    Unbinder unbinder;
    private static String adminNumber;
    private ArrayList<NavigationEnt> navItems;
    private long mLastClickTime = 0;
    private RecyclerItemListener listener = (Ent, position, id) -> {
        NavigationEnt ent = (NavigationEnt) Ent;
        switch (ent.getTitle()) {
            case R.string.home:
                getMainActivity().closeDrawer();
                break;
            case R.string.calender:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
              //  getDockActivity().replaceDockableFragment(AdditionalTaskFragment.newInstance(),"AdditionalTaskFragment");
                getDockActivity().replaceDockableFragment(CalenderJobsFragment.newInstance(),CalenderJobsFragment.TAG);
                getMainActivity().closeDrawer();
                break;
            case R.string.call_us:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if(adminNumber!=null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+adminNumber));
                    startActivity(intent);
                    getMainActivity().closeDrawer();
                }
                break;
            case R.string.settings:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                getDockActivity().replaceDockableFragment(SettingFragment.newInstance(),SettingFragment.TAG);
                getMainActivity().closeDrawer();
                break;
            case R.string.logout:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                dialogHelper.showCommonDialog(v -> {
                    serviceHelper.enqueueCall(webService.logout(prefHelper.getUser().getId()+"", FirebaseInstanceId.getInstance().getToken()), logout);
                    dialogHelper.hideDialog();


                }, R.string.logout, R.string.logout_message, R.string.yes, R.string.no, true, true);
                dialogHelper.setCancelable(true);
                dialogHelper.showDialog();

                getMainActivity().closeDrawer();
                break;
        }
    };



    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case logout:
                prefHelper.setLoginStatus(false);
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                prefHelper.setFirebase_TOKEN("");
              //  getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                break;

            case ADMIN:
                AdminEnt adminEnt=(AdminEnt)result;
                adminNumber=adminEnt.getCountryCode()+adminEnt.getPhoneNo()+"";
                break;
        }
    }

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.getAdminDetail(), ADMIN);

        bindNavItems();

    }




    private void bindNavItems() {
        navItems = new ArrayList<>();
       /* if (!prefHelper.isLogin()) {
            navItems.add(new NavigationEnt(R.drawable.home_nav, (R.string.login)));
        }*/
        navItems.add(new NavigationEnt(R.drawable.home, (R.string.home)));
        navItems.add(new NavigationEnt(R.drawable.calender, (R.string.calender)));
        navItems.add(new NavigationEnt(R.drawable.contact_us, (R.string.call_us)));
        navItems.add(new NavigationEnt(R.drawable.settings, (R.string.settings)));
        navItems.add(new NavigationEnt(R.drawable.logout, (R.string.logout)));
        /*if (prefHelper.isLogin()) {
            navItems.add(new NavigationEnt(R.drawable.home_nav, (R.string.logout)));
        }*/
        rvNav.bindRecyclerView(new NavigationBinder(listener), navItems,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false),
                new DefaultItemAnimator());
    }
}
