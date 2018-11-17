package com.sumin.fitnesstest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.sumin.fitnesstest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private static final String BASE_URL = "https://sample.fitnesskit-admin.ru/schedule/get_group_lessons_v2/4/";
    private static final int REQUEST_TIMEOUT = 3000;

    public static JSONArray getJSONFromWeb() {
        JSONArray result = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(REQUEST_TIMEOUT);
            urlConnection.setConnectTimeout(REQUEST_TIMEOUT);
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder builderResult = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                builderResult.append(line);
                line = reader.readLine();
            }
            result = new JSONArray(builderResult.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    public static boolean isInternetConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public static class JSONFromWebLoader extends AsyncTaskLoader<JSONArray> {

        private OnLoadingProcessListener onLoadingProcessListener;

        public interface OnLoadingProcessListener {
            void onProcessStart();
        }

        public JSONFromWebLoader(@NonNull Context context) {
            super(context);
        }

        public JSONFromWebLoader(@NonNull Context context, OnLoadingProcessListener onLoadingProcessListener) {
            super(context);
            this.onLoadingProcessListener = onLoadingProcessListener;
        }

        public void setOnLoadingProcessListener(OnLoadingProcessListener onLoadingProcessListener) {
            this.onLoadingProcessListener = onLoadingProcessListener;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (onLoadingProcessListener != null) {
                onLoadingProcessListener.onProcessStart();
                forceLoad();
            }
        }

        @Nullable
        @Override
        public JSONArray loadInBackground() {
            return getJSONFromWeb();
        }
    }

}
