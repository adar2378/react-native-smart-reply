package com.reactnativesmartreply;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.module.annotations.ReactModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.smartreply.SmartReply;
import com.google.mlkit.nl.smartreply.SmartReplyGenerator;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import com.google.mlkit.nl.smartreply.TextMessage;

import java.util.ArrayList;
import java.util.List;

@ReactModule(name = SmartReplyModule.NAME)
public class SmartReplyModule extends ReactContextBaseJavaModule {
    public static final String NAME = "SmartReply";

    public SmartReplyModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
        promise.resolve(a * b);
    }

    @ReactMethod
    public void generateSmartReply(ReadableArray messages, Promise promise) {
      List<TextMessage> conversation = new ArrayList<TextMessage>();
      for (int i = 0 ; i < messages.size(); i++){
        ReadableMap conversationObject = messages.getMap(i);
        boolean isLocalUser = conversationObject.getBoolean("isLocalUser");
        String message = conversationObject.getString("message");
        String userId = conversationObject.getString("userId");
        if(!isLocalUser){
          conversation.add(TextMessage.createForRemoteUser(
            message, System.currentTimeMillis(), userId));
        }else {
          conversation.add(TextMessage.createForLocalUser(
            message, System.currentTimeMillis()));
        }
      }
      SmartReplyGenerator smartReply = SmartReply.getClient();
      smartReply.suggestReplies(conversation).addOnSuccessListener(new OnSuccessListener<SmartReplySuggestionResult>() {
        @Override
        public void onSuccess(@NonNull SmartReplySuggestionResult smartReplySuggestionResult) {
          List<String> resultTexts = new ArrayList<String>();
          WritableArray writableArray = new WritableNativeArray();
          if (smartReplySuggestionResult.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
            // The conversation's language isn't supported, so
            // the result doesn't contain any suggestions.
          } else if (smartReplySuggestionResult.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
            // Task completed successfully
            // ...

            for (SmartReplySuggestion suggestion : smartReplySuggestionResult.getSuggestions()) {
              String replyText = suggestion.getText();
              resultTexts.add(replyText);
              writableArray.pushString(replyText);
            }
          }



          promise.resolve(writableArray);
        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          // Task failed with an exception
          // ...
          promise.reject(e);
        }
      });;
    }

    public static native int nativeMultiply(int a, int b);
}
