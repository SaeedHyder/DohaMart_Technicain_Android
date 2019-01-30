package com.ingic.ezhalbatek.technician.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.technician.R;
import com.ingic.ezhalbatek.technician.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.technician.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.technician.ui.views.AnyTextView;
import com.ingic.ezhalbatek.technician.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.technician.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created on 6/4/18.
 */
public class RateTechnicianFragment extends BaseFragment {
    public static final String TAG = "RateTechnicianFragment";
    @BindView(R.id.imgProfilePic)
    CircleImageView imgProfilePic;
    @BindView(R.id.txtTechnicainName)
    AnyTextView txtTechnicainName;
    @BindView(R.id.rbRating)
    CustomRatingBar rbRating;
    @BindView(R.id.edtFeedback)
    AnyEditTextView edtFeedback;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    Unbinder unbinder;

    public static RateTechnicianFragment newInstance() {
        Bundle args = new Bundle();

        RateTechnicianFragment fragment = new RateTechnicianFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_technician, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.rate_review));
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

    @OnClick(R.id.btnConfirm)
    public void onViewClicked() {
        getDockActivity().popFragment();
    }
}