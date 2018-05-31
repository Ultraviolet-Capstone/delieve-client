package com.ultraviolet.delieve.view.deliever;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.view.base.BaseDialogFragment;

import org.w3c.dom.Text;

public class DelieverMatchedDialogFragment extends BaseDialogFragment {

    String beginAddress;
    String finishAddress;
    String size;
    int type;
    String code;
    String weight;
    String requestTime;
    String requestMaxTime;

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

    public void setWeight(String weight) {
        this.weight = weight;
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

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_send_matched_dialog, parent, false);

        TextView tx_beginAddress=(TextView)view.findViewById(R.id.beginAddress);
        TextView tx_finishAddress=(TextView)view.findViewById(R.id.finishAddress);
        TextView tx_size=(TextView)view.findViewById(R.id.requestSize);
        TextView tx_type=(TextView)view.findViewById(R.id.requestType);
        TextView tx_weight=(TextView)view.findViewById(R.id.requestWeight);
        TextView tx_beginTime=(TextView)view.findViewById(R.id.requestBeginTime);
        TextView tx_finishTime=(TextView)view.findViewById(R.id.requestFinishTime);

        tx_beginAddress.setText(beginAddress);
        tx_finishAddress.setText(finishAddress);
        tx_size.setText(size);
        tx_type.setText(code);
        tx_weight.setText(weight);
        tx_beginTime.setText(requestTime);
        tx_finishTime.setText(requestMaxTime);


        return view;
    }
}
