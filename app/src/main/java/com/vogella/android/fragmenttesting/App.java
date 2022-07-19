package com.vogella.android.fragmenttesting;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.vogella.android.fragmenttesting.api.APIRequest;
import com.vogella.android.fragmenttesting.api.Api;
import com.vogella.android.fragmenttesting.api.SecondApi;
import com.vogella.android.fragmenttesting.constants.PreferenceConstant;
import com.vogella.android.fragmenttesting.constants.UrlConstant;
import com.vogella.android.fragmenttesting.database.DatabaseService;

import timber.log.Timber;

public class App extends Application {

    private static APIRequest mainApi = null;
    private static APIRequest secondApi = null;
    private static DatabaseService database =  null;
    private static SharedPreferences preferences = null;

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        initApi();
        initFakeApi();
        initDatabase();
        initSharedPreferences();
        super.onCreate();
    }
    private void initApi() {
        mainApi = new Api(UrlConstant.BASE_URL).provideApiCall();

    }
    public static APIRequest getApi() {
        if(mainApi == null) {
            mainApi = new Api(UrlConstant.BASE_URL).provideApiCall();
        }
        return mainApi;
    }
    private void initFakeApi() {
        secondApi = new SecondApi(UrlConstant.BASE_FAKE_URL).provideFakeApiCall();
    }
    public static APIRequest getFakeApi() {
        if (secondApi == null) {
            secondApi = new SecondApi(UrlConstant.BASE_FAKE_URL).provideFakeApiCall();
        }
        return secondApi;
    }
    private void initDatabase() {
        database = new DatabaseService(this);
    }
    public static DatabaseService getDatabase() {
        return database;
    }

    private void initSharedPreferences() {
        preferences = getSharedPreferences("", MODE_PRIVATE);
    }
    public static SharedPreferences getPreferences() {
        return preferences;
    }
}
