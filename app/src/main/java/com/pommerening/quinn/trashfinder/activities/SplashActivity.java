package com.pommerening.quinn.trashfinder.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.esri.core.internal.catalog.User;
import com.esri.core.io.UserCredentials;
import com.esri.core.portal.Portal;
import com.esri.core.portal.PortalItem;
import com.esri.core.portal.PortalItemType;
import com.esri.core.portal.PortalUser;
import com.esri.core.portal.PortalUserContent;
import com.pommerening.quinn.trashfinder.R;
import com.pommerening.quinn.trashfinder.pojo.JSONParser;
import com.pommerening.quinn.trashfinder.pojo.UserWebmaps;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "Splash Activity";
    private final String URL = "https://www.arcgis.com/sharing/rest/oauth2/token";
    private static final String ACCESS_TAG = "access_token";
    private static final String EXPIERS_TAG = "expires_in";
    private static final String CLIENT_ID = "Exe1PE6yrpOez6VN";
    private static final String SECRET_ID = "3d65e2fa33364cd5b8e507d8b8dd54fe";
    private String mToken;
    private String mExpires;
    private UserCredentials credentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Task().execute();
    }

    private class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "In background");
            try {
                run();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private void run() throws Exception {
            UserWebmaps userWebMaps;
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("client_secret", SECRET_ID));
            params.add(new BasicNameValuePair("client_id", CLIENT_ID));
            params.add(new BasicNameValuePair("grant_type", "client_credentials"));
            params.add(new BasicNameValuePair("f", "json"));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = jsonParser.makeHttpRequest(URL, "POST", params);

            mToken = jsonObject.getString(ACCESS_TAG);
            mExpires = jsonObject.getString(EXPIERS_TAG);
            credentials = new UserCredentials();
            credentials.setUserAccount("", "");
            Portal portal = new Portal("https://www.arcgis.com", credentials);
            try {
                PortalUser user = portal.fetchUser();
                PortalUserContent puc = user.fetchContent();
                List<PortalItem> items = puc.getItems();

                if(items == null) {
                    Log.d(TAG, "No items found");
                    return;
                }

                for(PortalItem temp : items) {
                    if(temp.getType() == PortalItemType.WEBMAP){
                        byte[] data = temp.fetchThumbnail();
                        Bitmap bitmap;
                        if(data != null) {
                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        } else {
                            //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
                            Log.d(TAG, "Hellow World");
                        }
                    }
                }

                Log.d(TAG, "Items size" + items.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
