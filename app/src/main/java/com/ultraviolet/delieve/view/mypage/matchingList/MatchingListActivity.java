package com.ultraviolet.delieve.view.mypage.matchingList;

import android.os.Bundle;
import android.view.WindowManager;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.view.base.BaseActivity;

public class MatchingListActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_list);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

    }
}
