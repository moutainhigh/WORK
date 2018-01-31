/**
 * @Title: Token.java
 * @Package com.xlkfinance.bms.common.annotation
 * @Description: HTTP SESSION TOKEN
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2015年2月3日 下午3:35:43
 * @version V1.0
 */
package com.xlkfinance.bms.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Token
 * @Description: 注解 TOKEN
 * @author: Simon.Hoo
 * @date: 2015年2月12日 下午1:57:13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	boolean save() default false;

	boolean remove() default false;
}
