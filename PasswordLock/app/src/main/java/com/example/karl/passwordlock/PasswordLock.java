package com.example.karl.passwordlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by karl on 2018/09/08.
 */

public class PasswordLock extends Activity implements PasswordKeyboardView.IOnKeyboardListener {

    private static final String TAG = "PasswordLock";

    private EditText mEditText;
    private PasswordKeyboardView mKeyboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.disa_input);
        mEditText.setInputType(InputType.TYPE_NULL);

        mKeyboardView = (PasswordKeyboardView) findViewById(R.id.disa_view_keyboard);
        mKeyboardView.setIOnKeyboardListener(this);
        hideStatusNavigationBar();
    }

    private void hideStatusNavigationBar(){
        if(Build.VERSION.SDK_INT<16){
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onInsertKeyEvent(String text) {
        mEditText.append(text);
    }

    @Override
    public void onDeleteKeyEvent() {
        int start = mEditText.length() - 1;
        if (start >= 0) {
            mEditText.getText().delete(start, start + 1);
        }
    }

    @Override
    public void onEnterKeyEvent() {
        String inputCode = mEditText.getText().toString();
        if (inputCode.equals("")) return;
        mEditText.setText("");
        if (inputCode.equals("123456")) {
            Log.d(TAG,"start...");
            Toast.makeText(this, "start...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClassName("com.android.settings","com.android.settings.Settings");
            startActivity(intent);
        } else {
            Log.d(TAG,"return...");
            Toast.makeText(this, "return...", Toast.LENGTH_SHORT).show();
        }
    }
}

