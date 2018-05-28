package com.ultraviolet.delieve.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ultraviolet.delieve.EvaluateDeliver1;
import com.ultraviolet.delieve.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {


    public MyPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_deliever_start_authentication, container, false);
        Button btn_start_judge=(Button)view.findViewById(R.id.btn_satrt_judge);

        btn_start_judge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EvaluateDeliver1.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
