# FormVerfiy
安卓文本验证框架,一个简单的界面输入框架，目前支持有功能不多，还有很多不足的地方，有兴趣的朋友一起学习.
<hr>
用法很简单
```Java
    // 需要在Activity中创建对象
    private VerifyFrame mVerifyFrame;
```
目前有个缺点就是需要在你所有的加注解的View findViewById 后才可以new 这个对象.
```Java
    @Verify(type = Type.PHONE)
    private ExpandableEditText mEtPhone;
    // other code
    mEtPhone = (ExpandableEditText) findViewById(R.id.eet_login_phone_number);
    // 创建对象时候需要传入当前Activity
    mVerifyFrame = new VerifyFrame(this);
```
添加两个监听
```Java
    // 如果不符合注解中的条件回调方法
    mVerifyFrame.setOnErrorListener(this);
    // 在表单中所有输入框都不为空的时候返回true,其他时候返回false
    mVerifyFrame.setOnAllowSubmitListener(this);
```
```java
    // 进行表单验证
    if (mVerifyFrame.verify()) {
        // 验证通过后执行的方法
    }
```
别忘记在Activity销毁的时候调用对象的销毁方法
```java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVerifyFrame.onDestroy();
    }
```
写的还不完善,请各位多指教.