package com.ingic.ezhalbatek.technician.fragments.standard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.UserEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.helpers.UIHelper;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.toggleNotification;

/**
 * Created on 6/6/18.
 */
public class SettingFragment extends BaseFragment {
    public static final String TAG = "SettingFragment";
    @BindView(R.id.swtNotification)
    Switch swtNotification;
    Unbinder unbinder;
    @BindView(R.id.btn_english)
    AnyTextView btnEnglish;
    @BindView(R.id.btn_Arabic)
    AnyTextView btnArabic;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_setting_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.getUser() != null && prefHelper.getUser().getNotification() != null && prefHelper.getUser().getNotification().equals("1")) {
            swtNotification.setChecked(true);
        } else {
            swtNotification.setChecked(false);
        }

        if (prefHelper.isLanguageArabian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            btnEnglish.setTextColor(getResources().getColor(R.color.app_font_gray));
            btnArabic.setTextColor(getResources().getColor(R.color.app_red));

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            btnEnglish.setTextColor(getResources().getColor(R.color.app_red));
            btnArabic.setTextColor(getResources().getColor(R.color.app_font_gray));
        }

        swtNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserEnt data = prefHelper.getUser();
                data.setNotification(isChecked ? "1" : "0");
                prefHelper.putUser(data);
                serviceHelper.enqueueCall(webService.toggleNotification(prefHelper.getUser().getId() + "", isChecked ? "1" : "0"), toggleNotification);
            }
        });

    }


    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case toggleNotification:
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.app_settings));
    }


    @OnClick({R.id.btn_english, R.id.btn_Arabic, R.id.btnChangePassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_english:
                btnEnglish.setTextColor(getResources().getColor(R.color.app_red));
                btnArabic.setTextColor(getResources().getColor(R.color.app_font_gray));
                prefHelper.putLang(getDockActivity(), "en");
                break;
            case R.id.btn_Arabic:
                btnEnglish.setTextColor(getResources().getColor(R.color.app_font_gray));
                btnArabic.setTextColor(getResources().getColor(R.color.app_red));
                prefHelper.putLang(getDockActivity(), "ar");
                break;
            case R.id.btnChangePassword:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), ChangePasswordFragment.TAG);
                break;
        }
    }
}