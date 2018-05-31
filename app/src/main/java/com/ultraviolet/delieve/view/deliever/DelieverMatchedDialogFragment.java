package com.ultraviolet.delieve.view.deliever;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.DelieverAcceptDto;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.view.base.BaseDialogFragment;

import org.w3c.dom.Text;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class DelieverMatchedDialogFragment extends BaseDialogFragment {

    @Inject
    DeliveryRepository mDeliveryRepository;

    @Inject
    UserRepository mUserRepository;


    @OnClick(R.id.btn_deliever_accpted)
    public void onClick(){
        Log.d("credt", "test44");
        mDeliveryRepository.delieverAccept(new DelieverAcceptDto(
                mUserRepository.getUserId(),
                requestId,
                (new Date()).toString()
        )).subscribe(res -> {
            if(res.code() == 200 && res.body().delivererId == mUserRepository.getUserId())
                Log.d("credt", "successfully accepted request");
            Intent intent = new Intent(getActivity(), DelieverMatchedActivity.class);
            intent.putExtra("from", beginAddress);
            intent.putExtra("to", finishAddress);
            intent.putExtra("weight", weight);
            intent.putExtra("max", requestMaxTime);

            startActivity(intent);
        }, throwable -> {

        });
    }

    @OnClick(R.id.btn_deliever_reject)
    public void onRejectClick(){
        mDeliveryRepository.delieverFlush(mUserRepository.getUserId())
                .subscribe(
                        res->{
                            if (res.code() == 200){
                                Log.d("credt", "flushed");
                            }
                            else {
                                Log.d("credt", "wtf?");
                            }
                        }, throwable -> {

                        }
                );
    }


    String beginAddress;
    String finishAddress;
    String size;
    int type;
    String code;
    String weight;
    String requestTime;
    String requestMaxTime;
    int requestId;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getBeginAddress() {
        return beginAddress;
    }

    public void setBeginAddress(String beginAddress) {
        this.beginAddress = beginAddress;
    }

    public String getFinishAddress() {
        return finishAddress;
    }

    public void setFinishAddress(String finishAddress) {
        this.finishAddress = finishAddress;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        if(type==0){
            code="특징 없음";
        }else if(type==1){
            code="깨지기 쉬움";
        }

        else if(type==2){
            code="액체";
        }
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = String.valueOf(weight);
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestMaxTime() {
        return requestMaxTime;
    }

    public void setRequestMaxTime(String requestMaxTime) {
        this.requestMaxTime = requestMaxTime;
    }

    Context mContext;
    public static DelieverMatchedDialogFragment newInstance() {
        DelieverMatchedDialogFragment f = new DelieverMatchedDialogFragment();
        return f;
    }

    public DelieverMatchedDialogFragment() {
        mContext = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        super.onCreateView(inflater, parent, state);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_deliever_matched_dialog, parent, false);

        getDiComponent().inject(this);

        TextView tx_beginAddress= view.findViewById(R.id.beginAddress);
        TextView tx_finishAddress= view.findViewById(R.id.finishAddress);
        TextView tx_size= view.findViewById(R.id.requestSize);
        TextView tx_type= view.findViewById(R.id.requestType);
        TextView tx_weight= view.findViewById(R.id.requestWeight);
        TextView tx_beginTime= view.findViewById(R.id.requestBeginTime);
        TextView tx_finishTime= view.findViewById(R.id.requestFinishTime);

        tx_beginAddress.setText(beginAddress);
        tx_finishAddress.setText(finishAddress);
        tx_size.setText(size);
        tx_type.setText(code);
        tx_weight.setText(weight);
        tx_beginTime.setText(requestTime);
        tx_finishTime.setText(requestMaxTime);

        ButterKnife.bind(this, view);

        return view;
    }
}
