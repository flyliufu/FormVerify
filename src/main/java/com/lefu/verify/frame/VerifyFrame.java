package com.lefu.verify.frame;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lefu.verify.annotation.Verify;
import com.lefu.verify.util.ErrorState;
import com.lefu.verify.util.Type;
import com.lefu.verify.util.VerifyTools;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * @author liufu on 16/1/14.
 */
@SuppressWarnings("all")
public class VerifyFrame implements TextChangeListenerImpl.OnNeedScanListener {

    private Object object;
    private Class clazz;
    private ArrayList<TextView> views = new ArrayList<TextView>();
    private ArrayList<Verify> verifies = new ArrayList<Verify>();


    public VerifyFrame(Object object) {
        this.object = object;
        clazz = object.getClass();
        init();
    }

    /**
     * 初始化界面,获取需要验证的view
     */
    public void init() {
        try {
            //当前类的所有声明的属性
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                // 拿到属性的注解
                Verify annotation = field.getAnnotation(Verify.class);
                if (annotation != null) {
                    //属性的类类型
                    Class tvClazz = field.getType();
                    Object o = field.get(object);
                    if (!(o instanceof TextView)) {
                        throw new Exception("verify property must be a TextView or SubView");
                    }
                    TextView v = (TextView) o;
                    TextChangeListenerImpl listener = new TextChangeListenerImpl();
                    listener.setOnNeedScanListener(this);
                    v.addTextChangedListener(listener);
                    views.add(v);
                    verifies.add(annotation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证表单是否正确
     *
     * @return 正确返回true, 否则返回false.
     */
    public boolean verify() {
        boolean flag = true;
        TextView tv = null;
        int errorState = -1;
        //当前类的所有声明的属性
        for (int i = 0; i < views.size(); i++) {
            tv = views.get(i);
            String value = tv.getText().toString();
            Verify verify = verifies.get(i);
            if (!verify.nullable() && !notNullVerify(value)) {
                errorState = ErrorState.NOT_NULL_ERROR;
                flag = false;
                break;
            }
            int length = verify.length();
            if (length >= 0 && !lengthVerify(value, length)) {
                errorState = ErrorState.LENGTH_ERROR;
                flag = false;
                break;
            }
            Type type = verify.type();
            if (Type.TEXT != type && !typeVerify(value, type)) {
                errorState = ErrorState.TYPE_ERROR;
                flag = false;
                break;
            }
            String regExp = verify.regExp();
            if (!"".equals(regExp) && !regExpVerify(regExp, value)) {
                errorState = ErrorState.REG_EXP_ERROR;
                flag = false;
                break;
            }
        }
        if (mOnErrorListener != null && !flag) {
            mOnErrorListener.onError(tv, errorState);
        }
        return flag;
    }

    /**
     * 正则验证
     *
     * @param regExp
     * @param value
     * @return
     */
    private boolean regExpVerify(String regExp, String value) {
        return VerifyTools.isMatch(regExp, value);
    }

    /**
     * 文本类型验证
     *
     * @param value
     * @param type
     * @return
     */
    private boolean typeVerify(String value, Type type) {
        boolean flag = true;
        int id = type.getId();
        if (id == Type.EMAIL.getId() && !VerifyTools.isEmail(value)) {
            flag = false;
        } else if (id == Type.ID_CARD.getId() && !VerifyTools.isIDCard(value)) {
            flag = false;
        } else if (id == Type.PHONE.getId() && !VerifyTools.isPhone(value)) {
            flag = false;
        } else if (id == Type.PASSWORD.getId() && !VerifyTools.isRealPwd(value)) {
            flag = false;
        }
        return flag;
    }

    /**
     * 文本长度验证
     *
     * @param value
     * @param length
     * @return
     */
    private boolean lengthVerify(String value, int length) {
        boolean flag = true;
        if (value.length() < length) {
            flag = false;
        }
        return flag;
    }

    /**
     * 非空验证
     *
     * @param value
     * @return
     */
    private boolean notNullVerify(String value) {
        boolean flag = true;
        if (TextUtils.isEmpty(value)) {
            flag = false;
        }
        return flag;
    }

    private OnErrorListener mOnErrorListener;

    /**
     * 错误监听
     *
     * @param listener
     */
    public void setOnErrorListener(OnErrorListener listener) {
        this.mOnErrorListener = listener;
    }

    @Override
    public void needScan() {
        if (this.mOnAllowSubmitListener == null) return;
        boolean flag = true;
        for (int i = 0; i < views.size(); i++) {
            TextView textView = views.get(i);
            if ("".equals(textView.getText().toString())) {
                flag = false;
                break;
            }
        }
        this.mOnAllowSubmitListener.onAllowSubmit(flag);
    }

    public interface OnErrorListener {
        /**
         * 不满足时候返回该View
         *
         * @param view  不符合条件的view对象
         * @param state {@link ErrorState}
         */
        void onError(View view, int state);
    }

    private OnAllowSubmitListener mOnAllowSubmitListener;

    public void setOnAllowSubmitListener(OnAllowSubmitListener listener) {
        this.mOnAllowSubmitListener = listener;
    }

    public interface OnAllowSubmitListener {
        /**
         * 是否允许提交
         *
         * @param flag true 可以提交,false 不可以
         */
        public void onAllowSubmit(boolean flag);
    }

    public void onDestroy() {
        clazz = null;
        object = null;

        views.clear();
        views = null;

        verifies.clear();
        verifies = null;

        this.mOnAllowSubmitListener = null;
        this.mOnErrorListener = null;
    }
}
