package com.ingic.ezhalbatek.technician.fragments.standard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 5/16/18.
 */
public class LanguageSelectionFragment extends BaseFragment {
    public static final String TAG = "LanguageSelectionFragment";
    @BindView(R.id.img_splash)
    ImageView imgSplash;
    @BindView(R.id.btn_english)
    Button btnEnglish;
    @BindView(R.id.btn_arabic)
    Button btnArabic;
    Unbinder unbinder;

    public static LanguageSelectionFragment newInstance() {
        Bundle args = new Bundle();

        LanguageSelectionFragment fragment = new LanguageSelectionFragment();
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
        View view = inflater.inflate(R.layout.fragment_language_selection, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMainActivity().changeBackgroundResources(R.drawable.bg);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showTitleBar();
        titleBar.setSubHeading(getResString(R.string.select_language));
    }

    @OnClick({R.id.btn_english, R.id.btn_arabic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_english:
                prefHelper.setLanguageSelected(true);

                if (!prefHelper.isLanguageArabian()) {
                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                } else {
                    prefHelper.putLang(getDockActivity(), "en");
                }

                break;
            case R.id.btn_arabic:
                if (prefHelper.isLanguageArabian()) {
                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                } else {
                    prefHelper.putLang(getDockActivity(), "ar");
                }
                break;
        }
    }
}