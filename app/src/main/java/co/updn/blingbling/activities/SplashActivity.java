package co.updn.blingbling.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.JsonObject;

import co.updn.blingbling.R;
import co.updn.blingbling.utils.Constants;
import co.updn.blingbling.utils.Data;
import co.updn.blingbling.utils.RetroAPI;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RetroAPI.NetworkCalls.getResources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(Constants.TAG, Log.getStackTraceString(e));
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        Data.loadResourcesFromJson(jsonObject);
                        RetroAPI.NetworkCalls.getCategories()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<JsonObject>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(Constants.TAG, Log.getStackTraceString(e));
                                    }

                                    @Override
                                    public void onNext(JsonObject jsonObject) {
                                        Data.loadCategoriesFromJson(jsonObject);
                                        Intent intent = new Intent(SplashActivity.this, CategoryActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    }
                });
    }
}
