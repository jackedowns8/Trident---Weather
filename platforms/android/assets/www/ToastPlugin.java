package com.company.plugins;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.widget.Toast;

public class ToastPlugin extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args,
            CallbackContext callbackContext) throws JSONException {

        String message = args.getString(0);

        // used to log the text and can be seen in LogCat
        Log.d("Toast Plugin", "Calling the Toast...");
        Log.d("Toast Plugin", message);

        if (action.equals("shortToast")) {          
            this.shortToast(message, callbackContext);
            return true;
        } else if (action.equals("longToast")) {
            this.longToast(message, callbackContext);
            return true;
        }
        return false;
    }

    private void shortToast(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            Toast.makeText(cordova.getActivity().getApplicationContext(),
                    message, Toast.LENGTH_SHORT).show();
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void longToast(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            Toast.makeText(cordova.getActivity().getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}