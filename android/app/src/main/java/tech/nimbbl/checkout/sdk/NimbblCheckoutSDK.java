package tech.nimbbl.checkout.sdk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class NimbblCheckoutSDK {

    private static NimbblCheckoutSDK instance;
    private WeakReference<Activity> myActivityReference; // do not leak memory

    private PaymentEventBroadCastReceiver receiver;
    private NimbblCheckoutPaymentListener listener;
    private Context context;

    private NimbblCheckoutSDK(){}

    public static NimbblCheckoutSDK getInstance() {
        if(instance == null) {
            instance = new NimbblCheckoutSDK();
        }
        return  instance;
    }

    public void checkout(NimbblCheckoutOptions options){

        //this.options = options;
        Intent intent = new Intent(context, NimbblCheckoutMainActivity.class);
        intent.putExtra("options",options);
        context.startActivity(intent);

    }

    public void init(Activity activity) {
        if(activity == null) {
            Log.e("Nimbbl SDK", "activity can't be null. returning.");
            return;
        }

        listener = (NimbblCheckoutPaymentListener) activity;
        context = activity;
        receiver = new PaymentEventBroadCastReceiver();
        context.registerReceiver(receiver, new IntentFilter("PaymentSuccess"));
        context.registerReceiver(receiver, new IntentFilter("PaymentFailure"));

    }

    void dismissReactActivity() {
        if(this.myActivityReference.get() != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    ((NimbblCheckoutMainActivity)myActivityReference.get()).finish();
                }
            });
        }
    }

    void setMyActivityWeakReference(Activity myActivityReference) {
        this.myActivityReference = new WeakReference<Activity>(myActivityReference);
    }

    private class PaymentEventBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(myActivityReference.get() != null) {
                dismissReactActivity();
            }
            String action = intent.getAction();
            if(action.equals("PaymentSuccess")){
                Log.d("Nimbbl SDK","payment callback");
                //Log.d("Nimbbl SDK",intent.getStringExtra("data"));

                try {
                    listener.onPaymentSuccess((HashMap<String, Object>)intent.getSerializableExtra("data"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(action.equals("PaymentFailure")){
                listener.onPaymentFailed(intent.getStringExtra("data"));
            }
            context.unregisterReceiver(receiver);
        }
    }

    private Map<String, Object> jsonString2Map(String jsonString ) throws JSONException {
        Map<String, Object> keys = new HashMap<String, Object>();

        org.json.JSONObject jsonObject = new org.json.JSONObject( jsonString ); // HashMap
        Iterator<?> keyset = jsonObject.keys(); // HM

        while (keyset.hasNext()) {
            String key =  (String) keyset.next();
            Object value = jsonObject.get(key);
            System.out.print("\n Key : "+key);
            if ( value instanceof org.json.JSONObject ) {
                //System.out.println("Incomin value is of JSONObject : ");
                keys.put(key, jsonString2Map(value.toString()));
            }else{
                keys.put(key, value);
            }
        }
        return keys;
    }
}

