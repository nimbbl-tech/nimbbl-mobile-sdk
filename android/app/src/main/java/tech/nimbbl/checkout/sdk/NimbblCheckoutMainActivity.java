package tech.nimbbl.checkout.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.react.BuildConfig;
import com.facebook.react.PackageList;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.soloader.SoLoader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;

public class NimbblCheckoutMainActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    private NimbblCheckoutOptions options;
    String[] optionsList = {"Unable to find my payment method","Unable to complete the payment","Don't want to make a purchase right now","Don't understand how to proceed","Others"};
    private static AsyncHttpClient client = new AsyncHttpClient();
    String cancelOption = "Others";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SoLoader.init(this, false);
        //
        // SharedPrefs mSharedPrefs = new SharedPrefs(this);
        mReactRootView = new ReactRootView(this);
        List<ReactPackage> packages = new PackageList(getApplication()).getPackages();
        packages.add(new NimbblCheckoutPackage());
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setCurrentActivity(this)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(packages)
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        Bundle b = new Bundle();
        //b.putString("loginData", mSharedPrefs.getObjectAsString(Constants.WEFITTER_LOGIN_DATA));
        //b.putString("user", mSharedPrefs.getObjectAsString(Constants.WEFITTER_USER_DATA));
        //b.putString("userName", "");
        //b.putString("roomName", "");
        //b.putInt("startTab", from);
        //NimbblCheckoutOptions options=null;
        if(getIntent().hasExtra("options")){
            options = getIntent().getParcelableExtra("options");
        }
        mReactRootView.startReactApplication(mReactInstanceManager, "Nimbbl", options!=null?options.asProps():b);

        setContentView(mReactRootView);

        NimbblCheckoutSDK.getInstance().setMyActivityWeakReference(this);

    }

    @Override
    public void invokeDefaultOnBackPressed() {
        //super.onBackPressed();
         AlertDialog.Builder b = new AlertDialog.Builder(this);
         b.setMessage("Are you sure you want to cancel this payment?").
                setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //open another dialog
                dialog.dismiss();
                openCancelOptions();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void openCancelOptions(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        AlertDialog d;
        //b.setTitle("Are you sure you want to cancel this payment?").
        b.setCancelable(false);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = (View) inflater.inflate(R.layout.custom_alert_dialog, null);
        b.setView(dialogView);
        d = b.create();
        RecyclerView rv = (RecyclerView) dialogView.findViewById(R.id.rv);

        PaymentCancelOptionsAdapter adapter = new PaymentCancelOptionsAdapter(this, optionsList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Button okBtn = (Button)dialogView.findViewById(R.id.btn_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId = options.getOrderId();
                RequestParams params = new RequestParams();
                params.add("cancellation_reason", cancelOption);

                client.put("https://api.nimbbl.tech/api/v2/update-order/"+orderId, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        //Log.d("asd", "---------------- this is response : " + response);
                        try {
                            JSONObject serverResp = new JSONObject(response.toString());

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                Intent i = new Intent();
                i.setAction("PaymentFailure");
                i.putExtra("data", cancelOption);
                sendBroadcast(i);
                d.dismiss();
                finish();
            }
        });

        d.show();
    }

    public void updateCancelOption(String option){
        //call api
        //https://api.nimbbl.tech/api/v2/update-order/order_aQA3j4dMNZLRx72N
        /*
        {"cancellation_reason":"Don't understand how to proceed","user-agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36","ipAddress":"49.36.115.6"}
         */

        /*String orderId = options.getOrderId();
        RequestParams params = new RequestParams();
        params.add("cancellation_reason", option);

        client.put("https://api.nimbbl.tech/api/v2/update-order/"+orderId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                //Log.d("asd", "---------------- this is response : " + response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        finish();*/
        this.cancelOption = option;
    }
}