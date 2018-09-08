package com.example.karl.passwordlock;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by karl on 2018/09/08.
 */

public class PasswordKeyboardView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {

    private static final int KEYCODE_ENTER = -10;
    private Drawable mDeleteDrawable;
    private Drawable mEnterDrawable;
    private Rect mDeleteDrawRect;
    private Rect mEnterDrawRect;

    private IOnKeyboardListener mOnKeyboardListener;

    public PasswordKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PasswordKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XNumberKeyboardView,
                defStyleAttr, 0);
        mDeleteDrawable = a.getDrawable(R.styleable.XNumberKeyboardView_xnkv_deleteDrawable);
        mEnterDrawable = a.getDrawable(R.styleable.XNumberKeyboardView_xnkv_enterDrawable);

        a.recycle();

        // 设置软键盘按键的布局
        Keyboard keyboard = new Keyboard(context, R.xml.keyboard_number_password);
        setKeyboard(keyboard);

        setEnabled(true);
        setPreviewEnabled(false); // 设置按键没有点击放大镜显示的效果
        setOnKeyboardActionListener(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 遍历所有的按键
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            // 如果是右下角的删除按键，重画按键的背景，并且绘制删除图标
            if (key.codes[0] == Keyboard.KEYCODE_DELETE) {
                drawDeleteButton(key, canvas);
            } else if (key.codes[0] == KEYCODE_ENTER) {
                drawEnterButton(key, canvas);
            }
        }
    }

    // 绘制删除按键
    private void drawDeleteButton(Keyboard.Key key, Canvas canvas) {
        if (mDeleteDrawable == null) {
            return;
        }

        // 计算删除图标绘制的坐标
        if (mDeleteDrawRect == null || mDeleteDrawRect.isEmpty()) {
            int drawWidth, drawHeight;
            int intrinsicWidth = mDeleteDrawable.getIntrinsicWidth();
            int intrinsicHeight = mDeleteDrawable.getIntrinsicHeight();

            drawWidth = key.width / 3;
            drawHeight = drawWidth * intrinsicHeight / intrinsicWidth;

            // 获取删除图标绘制的坐标
            int left = key.x + (key.width - drawWidth) / 2;
            int top = key.y + (key.height - drawHeight) * 2 / 3;
            mDeleteDrawRect = new Rect(left, top, left + drawWidth, top + drawHeight);
        }

        // 绘制删除的图标
        if (mDeleteDrawRect != null && !mDeleteDrawRect.isEmpty()) {
            mDeleteDrawable.setBounds(mDeleteDrawRect.left, mDeleteDrawRect.top,
                    mDeleteDrawRect.right, mDeleteDrawRect.bottom);
            mDeleteDrawable.draw(canvas);
        }
    }

    private void drawEnterButton(Keyboard.Key key, Canvas canvas) {
        if (mEnterDrawable == null) {
            return;
        }

        // 计算删除图标绘制的坐标
        if (mEnterDrawRect == null || mEnterDrawRect.isEmpty()) {
            int drawWidth, drawHeight;
            int intrinsicWidth = mEnterDrawable.getIntrinsicWidth();
            int intrinsicHeight = mEnterDrawable.getIntrinsicHeight();

            drawWidth = key.width / 3;
            drawHeight = drawWidth * intrinsicHeight / intrinsicWidth;

            // 获取删除图标绘制的坐标
            int left = key.x + (key.width - drawWidth) / 2;
            int top = key.y + (key.height - drawHeight) * 2 / 3;
            mEnterDrawRect = new Rect(left, top, left + drawWidth, top + drawHeight);
        }

        // 绘制删除的图标
        if (mEnterDrawRect != null && !mEnterDrawRect.isEmpty()) {
            mEnterDrawable.setBounds(mEnterDrawRect.left, mEnterDrawRect.top,
                    mEnterDrawRect.right, mEnterDrawRect.bottom);
            mEnterDrawable.draw(canvas);
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        // 处理按键的点击事件
        // 点击了删除按键
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            if (mOnKeyboardListener != null)
                mOnKeyboardListener.onDeleteKeyEvent();
        }
        // 点击了确定按键
        else if (primaryCode == KEYCODE_ENTER) {
            if (mOnKeyboardListener != null) {
                mOnKeyboardListener.onEnterKeyEvent();
            }
        }
        // 点击了数字按键
        else {
            if (mOnKeyboardListener != null) {
                mOnKeyboardListener.onInsertKeyEvent(Character.toString(
                        (char) primaryCode));
            }
        }
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    /**
     * 设置键盘的监听事件。
     *
     * @param listener 监听事件
     */
    public void setIOnKeyboardListener(IOnKeyboardListener listener) {
        this.mOnKeyboardListener = listener;
    }

    /**
     * 键盘的监听事件。
     */
    public interface IOnKeyboardListener {

        /**
         * 点击数字按键。
         *
         * @param text 输入的数字
         */
        void onInsertKeyEvent(String text);

        /**
         * 点击了删除按键。
         */
        void onDeleteKeyEvent();

        /**
         * 点击了确定按键。
         */
        void onEnterKeyEvent();
    }
}