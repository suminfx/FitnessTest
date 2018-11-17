package com.sumin.fitnesstest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.sumin.fitnesstest.R;

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

    private static final String BASE_URL = "https://sample.fitnesskit-admin.ru/schedule/get_group_lessons_v2/1/";
    private static final int REQUEST_TIMEOUT = 3000;
    private static boolean needRealData = false; //TODO: чтобы использовать реальные данные переключите на true и переменной BASE_URL присвоить правильное значение.

    public static JSONObject getJSONFromWeb() {
        JSONObject result = null;
        if (needRealData) {
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
                result = new JSONObject(builderResult.toString());
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
        } else {
            //Используются тестовые данные, без подключения к интернету
            try {
                String fakeData = "{\"results\":[{\"name\":\"Тренировка на турнике\",\"startTime\":\"10.00\",\"endTime\":\"10:45\",\"teacher\":\"Валентина Петровна\",\"place\":\"58\",\"description\":\"Программа занятий на турнике состоит из упражнений, направленных на развитие и укрепление мышц спины, плеч, пресса, бицепсов, трицепсов, грудных мышц. Тренироваться на турнике рекомендуется через день, по 30–40 минут. Перед каждой тренировкой нужно разминаться в течение 15 минут\",\"weekDay\":1},{\"name\":\"Тренировка на брусьях\",\"startTime\":\"11.00\",\"endTime\":\"11:45\",\"teacher\":\"Николай Александрович\",\"place\":\"34\",\"description\":\"Программа занятий на турнике состоит из упражнений, направленных на развитие и укрепление мышц спины, плеч, пресса, бицепсов, трицепсов, грудных мышц. Тренироваться на турнике рекомендуется через день, по 30–40 минут. Перед каждой тренировкой нужно разминаться в течение 15 минут\",\"weekDay\":2},{\"name\":\"Тренировка - бег\",\"startTime\":\"13.00\",\"endTime\":\"14:45\",\"teacher\":\"Николай Александрович\",\"place\":\"25\",\"description\":\"Программа занятий на турнике состоит из упражнений, направленных на развитие и укрепление мышц спины, плеч, пресса, бицепсов, трицепсов, грудных мышц. Тренироваться на турнике рекомендуется через день, по 30–40 минут. Перед каждой тренировкой нужно разминаться в течение 15 минут\",\"weekDay\":3},{\"name\":\"Тренировка на кольцах\",\"startTime\":\"15.00\",\"endTime\":\"15:45\",\"teacher\":\"Николай Александрович\",\"place\":\"42\",\"description\":\"Программа занятий на турнике состоит из упражнений, направленных на развитие и укрепление мышц спины, плеч, пресса, бицепсов, трицепсов, грудных мышц. Тренироваться на турнике рекомендуется через день, по 30–40 минут. Перед каждой тренировкой нужно разминаться в течение 15 минут\",\"weekDay\":4},{\"name\":\"Игра в баскетбол\",\"startTime\":\"16.00\",\"endTime\":\"18:45\",\"teacher\":\"Николай Александрович\",\"place\":\"21\",\"description\":\"Программа занятий на турнике состоит из упражнений, направленных на развитие и укрепление мышц спины, плеч, пресса, бицепсов, трицепсов, грудных мышц. Тренироваться на турнике рекомендуется через день, по 30–40 минут. Перед каждой тренировкой нужно разминаться в течение 15 минут\",\"weekDay\":5}]}";
                result = new JSONObject(fakeData);
            } catch (JSONException e) {
                e.printStackTrace();
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

    public static class JSONFromWebLoader extends AsyncTaskLoader<JSONObject> {

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
        public JSONObject loadInBackground() {
            return getJSONFromWeb();
        }
    }

}
