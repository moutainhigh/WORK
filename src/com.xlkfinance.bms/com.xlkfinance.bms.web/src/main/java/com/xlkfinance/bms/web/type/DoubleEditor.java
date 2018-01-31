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
package com.xlkfinance.bms.web.type;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

public class DoubleEditor extends CustomNumberEditor {

	private DoubleEditor(Class<? extends Number> numberClass, boolean allowEmpty) throws IllegalArgumentException {
		super(numberClass, allowEmpty);
	}

	public DoubleEditor() {
		this(Double.class, true);
	}

	@Override
	public void setValue(Object value) {
		if (value == null) {
			super.setValue(0.0);
		} else {
			super.setValue(value);
			
		}
	}

	
	

}
