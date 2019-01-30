package com.ingic.ezhalbatek.technician.fragments.standard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 6/5/18.
 */
public class ContactUsFragment extends BaseFragment {
    public static final String TAG = "ContactUsFragment";
    @BindView(R.id.edt_name)
    AnyEditTextView edtName;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.edtCity)
    AnyEditTextView edtCity;
    @BindView(R.id.edtQuery)
    AnyEditTextView edtQuery;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Unbinder unbinder;

    public static ContactUsFragment newInstance() {
        Bundle args = new Bundle();

        ContactUsFragment fragment = new ContactUsFragment();
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
        titleBar.setSubHeading(getResString(R.string.contact_form));
    }

    private boolean isvalidated() {

        if (edtName.getText().toString().isEmpty() || edtName.getText().toString().trim().equals("")) {
            edtName.setError(getString(R.string.enter_name));
            return false;
        } else if (edtPhone.getText().toString().trim().equals("") && edtPhone.getText().toString().isEmpty()) {
            edtPhone.setError(getString(R.string.enter_phone));
            return false;
        } else if (edtPhone.getText().toString().length() < 9 || edtPhone.getText().toString().length() > 16) {
            edtPhone.setError(getString(R.string.numberLength));
            return false;
        } else if (edtCity.getText().toString().isEmpty() || edtCity.getText().toString().trim().equals("")) {
            edtCity.setError(getString(R.string.enter_city));
            return false;
        } else if (edtCity.getText().toString().length() < 3) {

            edtCity.setError(getString(R.string.enter_valid_city));
            return false;
        } else if (edtQuery.getText().toString().isEmpty() || edtQuery.getText().toString().length() < 3 || edtQuery.getText().toString().trim().equals("")) {
            edtQuery.setError(getString(R.string.enter_query));
            return false;
        } else {
            return true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (isvalidated()) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.TAG);
        }
    }
}