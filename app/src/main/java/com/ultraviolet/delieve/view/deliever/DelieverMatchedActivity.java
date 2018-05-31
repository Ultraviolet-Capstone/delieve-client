package com.ultraviolet.delieve.view.deliever;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ultraviolet.delieve.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DelieverMatchedActivity extends AppCompatActivity {

    @BindView(R.id.m_from)
    TextView from;

    @BindView(R.id.m_to)
    TextView to;

    @BindView(R.id.m_weight)
    TextView weigth;

    @BindView(R.id.until)
    TextView until;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliever_matched);
        ButterKnife.bind(this);

        from.setText(getIntent().getStringExtra("from"));
        to.setText(getIntent().getStringExtra("to"));
        weigth.setText(getIntent().getStringExtra("weight" ) + " Kg");
        until.setText("until " + getIntent().getStringExtra("max"));


    }
}
