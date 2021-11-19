package com.example.zalobk.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.zalobk.MainActivity;
import com.example.zalobk.databinding.ActivityOtpauthenticationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPAuthentication extends AppCompatActivity {

    private ActivityOtpauthenticationBinding otpbinding;

    private String mEnterOTP;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpbinding = ActivityOtpauthenticationBinding.inflate(getLayoutInflater());
        setContentView(otpbinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        otpbinding.mChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPAuthentication.this, MainActivity.class);
                startActivity(intent);
            }
        });

        otpbinding.mVertifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEnterOTP = otpbinding.mGetOtp.getText().toString();
                if(mEnterOTP.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Your OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    otpbinding.mProgessBarOfAuth.setVisibility(View.VISIBLE);
                    String coderecieved = getIntent().getStringExtra("otp");
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(coderecieved, mEnterOTP);
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });

    }
    private  void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    otpbinding.mProgessBarOfAuth.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OTPAuthentication.this, setProfile.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        otpbinding.mProgessBarOfAuth.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }

}