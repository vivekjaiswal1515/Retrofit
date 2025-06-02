package com.example.loginandregisterusingretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginandregisterusingretrofit.ModelClass.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    EditText email_edt, password_edt;
    Button login_btn,register_btn,password_btn;
    String URL = "http://192.168.53.74/Login1.php/";
    String email, password;
    Retrofit retrofit;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
        password_btn = findViewById(R.id.password_btn);
        sessionManagement = new SessionManagement(getApplicationContext());

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidAndRetrofit();
            }
        });


        password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgetPassword.class));
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
        Call<LoginModel> call = api.Logins(email,password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                 if (response.isSuccessful()&&response.body()!=null){
                     LoginModel loginModel = response.body();
                     String message = loginModel.getMessage();
                     String errors = loginModel.getErrorCodes();
                     if (message.equals("SuccessFully")){
                         email_edt.setText("");
                         password_edt.setText("");
                         sessionManagement.EmailAndPassword(email,password);
                         Toast.makeText(Login.this,message+""+errors, Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(Login.this,MainActivity.class));
                     }else {
                         Toast.makeText(Login.this,message, Toast.LENGTH_SHORT).show();
                     }
                 }else {
                     Toast.makeText(Login.this, "Error Occured try again...", Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}