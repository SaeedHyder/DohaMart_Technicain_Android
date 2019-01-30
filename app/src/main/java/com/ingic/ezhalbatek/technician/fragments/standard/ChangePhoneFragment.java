package com.ingic.ezhalbatek.technician.fragments.standard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import  com.ingic.ezhalbatek.technician.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 6/6/18.
 */
public class ChangePhoneFragment extends BaseFragment {
    public static final String TAG = "ChangePhoneFragment";
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.edtNewPhone)
    AnyEditTextView edtNewPhone;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    Unbinder unbinder;

    public static ChangePhoneFragment newInstance() {
        Bundle args = new Bundle();

        ChangePhoneFragment fragment = new ChangePhoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_change_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.change_number));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean isvalidated() {

        if (edtPhone.getText().toString().equals("") && edtPhone.getText().toString().isEmpty()) {
            edtPhone.setError(getString(R.string.enter_phone));
            return false;
        } else if (edtPhone.getText().toString().length() < 9 || edtPhone.getText().toString().length() > 10) {
            edtPhone.setError(getString(R.string.numberLength));
            return false;
        }
        if (edtNewPhone.getText().toString().equals("") && edtNewPhone.getText().toString().isEmpty()) {
            edtNewPhone.setError(getString(R.string.enter_phone));
            return false;
        } else if (edtNewPhone.getText().toString().length() < 9 || edtNewPhone.getText().toString().length() > 10) {
            edtNewPhone.setError(getString(R.string.numberLength));
            return false;
        } else {
            return true;
        }

    }

    @OnClick(R.id.btnUpdate)
    public void onViewClicked() {
        if (isvalidated()) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.TAG);
        }
    }
}