package com.lefu.verify.annotation;

import com.lefu.verify.util.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liufu on 16/1/14.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Verify {
    /**
     * 是否可以为空,是不是想说"这不是废话么"有种情况是可心为空,但是如果不为空应该满足一个条件,条件是RegExp<br/>
     * <code>true</code> 可以为空
     * <code>false</code> 不可以为空
     *
     * @return
     */
    boolean nullable() default false;

    /**
     * 不为空需要满足的条件
     *
     * @return
     */
    String regExp() default "";

    /**
     * 最少内容长度
     *
     * @return
     */
    int length() default -1;

    /**
     * 内容格式
     *
     * @return
     */
    Type type() default Type.TEXT;

    /**
     * 最大值
     *
     * @return
     */
    double minValue() default Double.MIN_VALUE;

    /**
     * 最小值
     *
     * @return
     */
    double maxValue() default Double.MAX_VALUE;
}
