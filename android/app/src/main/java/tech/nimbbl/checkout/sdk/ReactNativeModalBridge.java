package tech.nimbbl.checkout.sdk;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;

public class ReactNativeModalBridge extends ReactContextBaseJavaModule {
    Context ctx;
    ReactNativeModalBridge(ReactApplicationContext context) {
        super(context);
        ctx = context;
    }

    @Override
    public String getName() {
        return "ReactNativeModalBridge";
    }

    @ReactMethod
    public void onResponse(String data) {
        Log.d("Nimbbl SDK getResponse",data);
        Intent i = new Intent();
        i.setAction("PaymentSuccess");
        i.putExtra("data", data);
        ctx.sendBroadcast(i);
    }

    @ReactMethod
    public void onError(String error) {
        Log.d("Nimbbl SDK getResponse",error);
        Intent i = new Intent();
        i.setAction("PaymentFailure");
        i.putExtra("data", error);
        ctx.sendBroadcast(i);
    }
}