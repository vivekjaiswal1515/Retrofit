package com.example.loginandregisterusingretrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginandregisterusingretrofit.ModelClass.ForgetPasswordModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPassword extends AppCompatActivity {
    Button password_btn;
    EditText email_edt, password_edt;
    String URL = "http://192.168.53.74/PasswordUp.php/";
    String email, password;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);
        password_btn = findViewById(R.id.password_btn);

        password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidAndRetrofit();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void ValidAndRetrofit() {
        email = email_edt.getText().toString();
        password = password_edt.getText().toString();

        if (email.isEmpty()) {
            email_edt.requestFocus();
            email_edt.setError("email id can't empty...");
            return;
        } else if (!email.matches("[a-zA-Z0-9]+[@][a-zA-Z]+[.][a-zA-Z]+")) {
            email_edt.requestFocus();
            email_edt.setError("email id invalid try again..");
            return;
        } else if (password.isEmpty()) {
            password_edt.requestFocus();
            password_edt.setError("password can't empty...");
            return;
        } else if (!password.matches("[a-zA-Z0-9]+")) {
            password_edt.requestFocus();
            password_edt.setError("password invalid try again...");
            return;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterApi api = retrofit.create(RegisterApi.class);
        Call<ForgetPasswordModel> call = api.ForgetPassword(email,password);
        call.enqueue(new Callback<ForgetPasswordModel>() {
            @Override
            public void onResponse(Call<ForgetPasswordModel> call, Response<ForgetPasswordModel> response) {
                 if (response.isSuccessful()&&response.body()!=null){
                     ForgetPasswordModel forgetPasswordModel = response.body();
                     String message = forgetPasswordModel.getMessage();
                     if (message.equals("Password")){
                            email_edt.setText("");
                            password_edt.setText("");
                         Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                     }else {
                         Toast.makeText(ForgetPassword.this, "Email doesn't Exists", Toast.LENGTH_SHORT).show();
                     }
                 }else {
                     Toast.makeText(ForgetPassword.this, "Error OCCured Try Again", Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onFailure(Call<ForgetPasswordModel> call, Throwable t) {
                Toast.makeText(ForgetPassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}