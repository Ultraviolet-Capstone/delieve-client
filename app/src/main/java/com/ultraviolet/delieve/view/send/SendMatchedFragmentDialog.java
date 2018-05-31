package com.ultraviolet.delieve.view.send;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.view.base.BaseDialogFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendMatchedFragmentDialog extends BaseDialogFragment {

    public static final String TAG = "Delieve";


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
        getDiComponent().inject(this);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_send_matched_dialog, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
