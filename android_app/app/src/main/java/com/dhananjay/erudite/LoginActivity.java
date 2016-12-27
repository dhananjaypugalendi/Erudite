package com.dhananjay.erudite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<Result> {

    EditText user_name_login,password_login;
    TextView sign_up_login;
    Button button_login;
    String userName="",password="";
    private static final String TAG="LoginActivity";
    private static final String BASE_URL="http://192.168.1.100/";
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Gson gson=new Gson();
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        user_name_login= (EditText) findViewById(R.id.user_name_login);
        password_login= (EditText) findViewById(R.id.password_login);
        sign_up_login= (TextView) findViewById(R.id.sign_up_login);
        button_login= (Button) findViewById(R.id.button_login);
        sign_up_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
        button_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        userName=user_name_login.getText().toString();
        password=password_login.getText().toString();
        API api=retrofit.create(API.class);
        Call<Result> call =api.loginAuth(2,userName,password);
        call.enqueue(this);


    }

    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        Result result=response.body();
        Log.d(TAG, "onResponse: "+result.success+" "+result.message);

    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
        Log.d(TAG, "onFailure: failed"+t);
        Toast.makeText(this,"Failed! Make sure you have proper internet connection",Toast.LENGTH_LONG).show();
    }
}