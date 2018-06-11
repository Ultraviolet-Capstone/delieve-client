package com.ultraviolet.delieve.view.mypage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.util.ImageLoadHelper;
import com.ultraviolet.delieve.view.base.BaseFragment;
import com.ultraviolet.delieve.view.login.LoginActivity;
import com.ultraviolet.delieve.view.mypage.matchingList.MatchingListActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends BaseFragment {

    private View rootView;

    @Inject
    UserRepository mUserRepository;

    @BindView(R.id.mypage_profilepic)
    ImageView mProfilePicImageView;

    @BindView(R.id.mypage_username)
    TextView mMyPageUsername;

    public MyPageFragment() {
        // Required empty public constructor
    }


    @OnClick(R.id.mypage_view_my_transacrion)
    public void onClick(){
        Intent intent = new Intent(getActivity(), MatchingListActivity.class);
        startActivity(intent);
    }

    private void onClickLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_page, container, false);

        TextView logout=(TextView)rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                onClickLogout();
            }
        });


        getDiComponent().inject(this);
        ButterKnife.bind(this, rootView);

        setupUi();

        return rootView;

    }

    private void setupUi() {
        mMyPageUsername.setText(mUserRepository.getUsername());
        ImageLoadHelper.loadProfileImage(mProfilePicImageView, mUserRepository.getUserProfilePicURL());
    }

}
