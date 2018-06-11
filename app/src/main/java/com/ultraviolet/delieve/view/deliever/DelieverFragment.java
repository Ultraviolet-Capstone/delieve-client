package com.ultraviolet.delieve.view.deliever;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.view.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DelieverFragment extends BaseFragment {

    @Inject
    UserRepository mUserRepository;

    @Inject
    DeliveryRepository mDeliveryRepository;

    @BindView(R.id.start_delieve_btn)
    Button button;

    @OnClick(R.id.start_delieve_btn)
    void onClick(){
        Intent intent = new Intent(getActivity(), DelieverWaitingForMatchingActivity.class);
        startActivity(intent);
    }
    public DelieverFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_deliever, container, false);

        ButterKnife.bind(this, rootView);

        getDiComponent().inject(this);
        button.setEnabled(false);

        mDeliveryRepository.getDeliveryMatchingListForDeliever(mUserRepository.getUserId())
                .subscribe(res->{
                    if (res.body() == null || res.body().size() == 0) {
                        button.setEnabled(true);
                        return;
                    }
                    DeliveryMatching matching = null;
                    for (DeliveryMatchingDto i : res.body()){
                        Log.d("credt", "matching status : " + i.matchingStatus);
                        if(i.matchingStatus.equals("READY") || i.matchingStatus.equals("PROGRESS")){
                            matching = new DeliveryMatching(i);
                            break;
                        }
                    }
                    if (matching == null){
                        button.setEnabled(true);
                        return;
                    }
                    Log.d("credt", "1234");
                    Intent intent = new Intent(getContext(), DelieverMatchedActivity.class);
                    intent.putExtra("Matching", matching);
                    startActivity(intent);
                    Log.d("credt", "5678");

                }, throwable -> {

                });

       return rootView;
    }

}
