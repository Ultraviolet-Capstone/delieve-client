package com.ultraviolet.delieve.view.login;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.UserDto;
import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.view.base.BaseActivity;
import com.ultraviolet.delieve.view.main.MainActivity;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity {

    @Inject
    AuthRepository mAuthRepository;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.birthday) EditText mBirthdayPicker;
    @BindView(R.id.radiogroupGender)
    RadioGroup genderRG;

    @OnClick(R.id.btn_signup)
    void onSignUpClicked(){
        signup();

    }

    @OnClick(R.id.link_login)
    void onLoginClicked(){

    }
    private void redirectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        getDiComponent().inject(this);


        String kakao_email=getIntent().getStringExtra("email");

        Log.d("guri", kakao_email);
        _emailText.setText(kakao_email);



    }

    @OnClick(R.id.birthday)
    public void onClickDatepicker(View v){
        Calendar mcurrentDate = Calendar.getInstance();
        int date = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        int month = mcurrentDate.get(Calendar.MONTH);
        int year = mcurrentDate.get(Calendar.YEAR);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mBirthdayPicker.setText(year + "/" + (month+1) + "/" +  dayOfMonth);
            }
        }, year, month,date );
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
        String name=_nameText.getText().toString();
        String phone=_passwordText.getText().toString();
        String email=_emailText.getText().toString();
        String birthday=mBirthdayPicker.getText().toString();
        String token=getIntent().getStringExtra("token");
        String url=getIntent().getStringExtra("url");
        String providerNickname=getIntent().getStringExtra("nickname");
        String gender=new String();

        int genderID = genderRG.getCheckedRadioButtonId();
        RadioButton genderRB = findViewById(genderID);
        if(genderRB.getResources().getResourceEntryName(genderID).equals("male")) {
            gender="male";
        }
        else if(genderRB.getResources().getResourceEntryName(genderID).equals("female")){
            gender="female";
        }

        mAuthRepository.register(new UserDto(name, phone, email,birthday, token, "kakao", url,
                providerNickname, gender))
                .subscribe(res -> {
                    Log.d("credt2", String.valueOf(res.code()));
                    if(res.code()==500){
                        Toast.makeText( getApplicationContext(),"회원가입을 할 수 없습니다.",Toast.LENGTH_LONG).show();
                    }
                    else if(res.code()==200){
                        redirectMainActivity();
                    }
                }, throwable -> {
                    throwable.printStackTrace();

                });

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String birthday = mBirthdayPicker.getText().toString();

        if (name.isEmpty() || name.length() < 2) {
            _nameText.setError("at least 2 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() !=11) {
            _passwordText.setError("핸드폰 번호를 올바르게 입력하세요(010-XXXX-XXXX)");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (birthday.isEmpty()) {
            _passwordText.setError("본인의 생일을 지정해주세요.");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}


