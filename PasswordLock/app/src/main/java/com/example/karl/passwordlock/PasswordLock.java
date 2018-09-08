package com.example.karl.passwordlock;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
        setupUi();
    }

    private void setupUi() {
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.disa_input);
        mEditText.setInputType(InputType.TYPE_NULL);

        mKeyboardView = (PasswordKeyboardView) findViewById(R.id.disa_view_keyboard);
        mKeyboardView.setIOnKeyboardListener(this);
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
        } else {
            Log.d(TAG,"return...");
            Toast.makeText(this, "return...", Toast.LENGTH_SHORT).show();
        }
    }
}

