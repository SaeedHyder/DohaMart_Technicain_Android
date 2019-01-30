package com.ingic.ezhalbatek.technician.fragments.standard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.entities.UserEnt;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.global.AppConstants;
import com.ingic.ezhalbatek.technician.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ingic.ezhalbatek.technician.global.WebServiceConstants.LoginService;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.edt_email)
    AnyEditTextView edtName;
    @BindView(R.id.edt_password)
    AnyEditTextView edtRegistrationCode;
    @BindView(R.id.loginButton)
    Button loginButton;

    private static boolean showBackBtn = true;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public static LoginFragment newInstance(boolean showBtn) {
        showBackBtn = showBtn;
        return new LoginFragment();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        if (showBackBtn) {
            titleBar.showBackButton();
        }
        titleBar.setSubHeading(getString(R.string.imgdesc_signin));
        if (edtName != null && edtRegistrationCode != null) {
            edtName.setText("");
            edtRegistrationCode.setText("");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        getMainActivity().changeBackgroundResources(R.drawable.bg);

    }

    @OnClick({R.id.loginButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.loginButton:
                if (isvalidated()) {
                    serviceHelper.enqueueCall(webService.login(edtRegistrationCode.getText().toString(), AppConstants.TechnicianRoleId, AppConstants.Device_Type, FirebaseInstanceId.getInstance().getToken()), LoginService);
                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case LoginService:
                UserEnt userData = (UserEnt) result;
                prefHelper.putUser(userData);
                prefHelper.setFirebase_TOKEN(FirebaseInstanceId.getInstance().getToken());
                prefHelper.setLoginStatus(true);
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
             /*   dialogHelper.showCommonDialog(v -> {
                    dialogHelper.hideDialog();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                }, R.string.empty, R.string.registration_succesasfull, R.string.ok, R.string.empty, false, false);
                dialogHelper.setCancelable(false);
                dialogHelper.showDialog();*/
                break;
        }
    }

    private boolean isvalidated() {
       /* if (edtName.getText().toString().isEmpty() || edtName.getText().toString().length() < 3) {
            edtName.setError(getString(R.string.enter_valid_name));
            return false;
        } else */
       if (edtRegistrationCode.getText().toString().isEmpty()) {
            edtRegistrationCode.setError(getString(R.string.enter_password));
            return false;
        } else if (edtRegistrationCode.getText().toString().length() < 6) {
            edtRegistrationCode.setError(getString(R.string.passwordLength));
            return false;
        } else {
            return true;
        }
    }


}
