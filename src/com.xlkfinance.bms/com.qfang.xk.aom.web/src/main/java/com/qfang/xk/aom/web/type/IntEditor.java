/**
 * @Title: IntEditor.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月23日 下午12:42:16
 * @version V1.0
 */
package com.qfang.xk.aom.web.type;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

public class IntEditor extends CustomNumberEditor {

	private IntEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
		super(numberClass, allowEmpty);
	}

	public IntEditor() {
		this(Integer.class, true);
	}

	@Override
	public void setValue(Object value) {
		if (value == null) {
			super.setValue(0);
		} else {
			super.setValue(value);
		}
	}

}
