package com.example.loginandregisterusingretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginandregisterusingretrofit.ModelClass.DeleteModel;
import com.example.loginandregisterusingretrofit.ModelClass.RegisterModel;
import com.example.loginandregisterusingretrofit.ModelClass.UpdateModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
EditText name_edt,email_edt,password_edt,location_edt,phone_edt;
RadioGroup radioGroup;
RadioButton male_btn,female_btn;
Button register_btn,show_btn,update_btn,delete_btn;
Spinner spinner;
String URL="http://192.168.53.74/Registers1.php/";
String URL1="http://192.168.53.74/Update.php/";
String URL2="http://192.168.53.74/Delete.php/";
String name,email,password,gender,location,phone,course;
Retrofit retrofit;
SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        name_edt = findViewById(R.id.name_edt);
        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);
        radioGroup = findViewById(R.id.radioGroup);
        male_btn = findViewById(R.id.male_btn);
        female_btn = findViewById(R.id.female_btn);
        spinner = findViewById(R.id.spinner);
        location_edt = findViewById(R.id.location_edt);
        phone_edt = findViewById(R.id.phone_edt);
        register_btn = findViewById(R.id.register_btn);
        show_btn = findViewById(R.id.show_btn);
        update_btn = findViewById(R.id.update_btn);
        delete_btn = findViewById(R.id.delete_btn);
        sessionManagement = new SessionManagement(getApplicationContext());

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DeleteAccount();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 UpdateAccount();
            }
        });

        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, ShowDetails.class));
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
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
        name = name_edt.getText().toString();
        email = email_edt.getText().toString();
        password = password_edt.getText().toString();
        location = location_edt.getText().toString();
        phone = phone_edt.getText().toString();

        if (male_btn.isChecked()){
            gender = male_btn.getText().toString();
        }if (female_btn.isChecked()){
            gender = female_btn.getText().toString();
        }if (spinner.isClickable()){
            course = spinner.getSelectedItem().toString();
        }

        if (name.isEmpty()){
            name_edt.requestFocus();
            name_edt.setError("");
            return;
        } else if (!name.matches("[a-zA-Z ]+")) {
            name_edt.requestFocus();
            name_edt.setError("");
            return;
        } else if (email.isEmpty()) {
            email_edt.requestFocus();
            email_edt.setError("");
            return;
        } else if (!email.matches("[a-zA-Z0-9]+[@][a-zA-Z]+[.][a-zA-Z]+")) {
            email_edt.requestFocus();
            email_edt.setError("");
            return;
        } else if (password.isEmpty()) {
            password_edt.requestFocus();
            password_edt.setError("");
            return;
        } else if (!password.matches("[a-zA-Z0-9]+")) {
            password_edt.requestFocus();
            password_edt.setError("");
            return;
        } else if (location.isEmpty()) {
            location_edt.requestFocus();
            location_edt.setError("");
            return;
        } else if (!location.matches("[a-zA-Z0-9 ]+")) {
            location_edt.requestFocus();
            location_edt.setError("");
            return;
        } else if (phone.isEmpty()) {
            phone_edt.requestFocus();
            phone_edt.setError("");
            return;
        } else if (!phone.matches("[0-9]+\\d{9}")) {
            phone_edt.requestFocus();
            phone_edt.setError("");
            return;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterApi api = retrofit.create(RegisterApi.class);
        Call<RegisterModel> call = api.Register(name,email,password,gender,course,location,phone);
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    RegisterModel registerModel = response.body();
                    String message = registerModel.getMessage();
                    String errors = registerModel.getErrorCodes();
                    if (message.equals("Already")) {
                        name_edt.setText("");
                        email_edt.setText("");
                        password_edt.setText("");
                        radioGroup.clearCheck();
                        spinner.setSelection(0);
                        location_edt.setText("");
                        phone_edt.setText("");
                        Toast.makeText(MainActivity.this,message+""+errors, Toast.LENGTH_SHORT).show();
                    }else {
                        name_edt.setText("");
                        email_edt.setText("");
                        password_edt.setText("");
                        radioGroup.clearCheck();
                        spinner.setSelection(0);
                        location_edt.setText("");
                        phone_edt.setText("");
                        sessionManagement.InsertDataUsingSharePreference(name,email,password,gender,course,location,phone);
                        Toast.makeText(MainActivity.this,message+""+errors, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Error Occured try again...", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void UpdateAccount(){
        name = name_edt.getText().toString();
        email = email_edt.getText().toString();
        password = password_edt.getText().toString();
        location = location_edt.getText().toString();
        phone = phone_edt.getText().toString();

        if (male_btn.isChecked()){
            gender = male_btn.getText().toString();
        }if (female_btn.isChecked()){
            gender = female_btn.getText().toString();
        }if (spinner.isClickable()){
            course = spinner.getSelectedItem().toString();
        }

        if (name.isEmpty()){
            name_edt.requestFocus();
            name_edt.setError("");
            return;
        } else if (!name.matches("[a-zA-Z ]+")) {
            name_edt.requestFocus();
            name_edt.setError("");
            return;
        } else if (email.isEmpty()) {
            email_edt.requestFocus();
            email_edt.setError("");
            return;
        } else if (!email.matches("[a-zA-Z0-9]+[@][a-zA-Z]+[.][a-zA-Z]+")) {
            email_edt.requestFocus();
            email_edt.setError("");
            return;
        } else if (password.isEmpty()) {
            password_edt.requestFocus();
            password_edt.setError("");
            return;
        } else if (!password.matches("[a-zA-Z0-9]+")) {
            password_edt.requestFocus();
            password_edt.setError("");
            return;
        } else if (location.isEmpty()) {
            location_edt.requestFocus();
            location_edt.setError("");
            return;
        } else if (!location.matches("[a-zA-Z0-9 ]+")) {
            location_edt.requestFocus();
            location_edt.setError("");
            return;
        } else if (phone.isEmpty()) {
            phone_edt.requestFocus();
            phone_edt.setError("");
            return;
        } else if (!phone.matches("[0-9]+\\d{9}")) {
            phone_edt.requestFocus();
            phone_edt.setError("");
            return;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterApi api = retrofit.create(RegisterApi.class);
        Call<UpdateModel> call = api.UpdateAccount(name,email,password,gender,course,location,phone);
        call.enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    UpdateModel updateModel = response.body();
                    String message = updateModel.getMessage();
                    if (message.equals("Updated")) {
                        name_edt.setText("");
                        email_edt.setText("");
                        password_edt.setText("");
                        radioGroup.clearCheck();
                        spinner.setSelection(0);
                        location_edt.setText("");
                        phone_edt.setText("");
                        Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Name doesn't Exists", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Error OCCured try again...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void DeleteAccount(){
        name = name_edt.getText().toString();

        if (name.isEmpty()){
            name_edt.requestFocus();
            name_edt.setError("");
            return;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterApi api = retrofit.create(RegisterApi.class);
        Call<DeleteModel> call = api.DeleteAccount(name);
        call.enqueue(new Callback<DeleteModel>() {
            @Override
            public void onResponse(Call<DeleteModel> call, Response<DeleteModel> response) {
               if (response.isSuccessful()&&response.body()!=null){
                   DeleteModel deleteModel = response.body();
                   String message = deleteModel.getMessage();
                   if (message.equals("Delete")){
                       name_edt.setText("");
                       Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                   }
               }else {
                   Toast.makeText(MainActivity.this, "Error OCCured try again", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<DeleteModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}