package com.lefu.verify.frame;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

/**
 * @author liufu on 16/1/16.
 */
public class TextChangeListenerImpl implements TextWatcher {

    /**
     * 上次输入的文本
     */
    private String mPreStr = "";

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!mPreStr.equals(s.toString())) {
            mPreStr = s.toString();
            if (this.mOnNeedScanListener != null) {
                this.mOnNeedScanListener.needScan();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private OnNeedScanListener mOnNeedScanListener;

    public void setOnNeedScanListener(OnNeedScanListener listener) {
        this.mOnNeedScanListener = listener;
    }

    /**
     * @author liufu on 16/1/16.
     */
    public interface OnNeedScanListener {
        /**
         * 需要去扫描所有EditText时候触发
         */
        void needScan();
    }

    public void onDestroy() {
        mOnNeedScanListener = null;
    }
}
