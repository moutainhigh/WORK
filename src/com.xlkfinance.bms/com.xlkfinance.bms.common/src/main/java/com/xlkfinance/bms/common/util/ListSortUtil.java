/**
 * @Title: ListSortUtil.java
 * @Package com.xlkfinance.bms.common.util
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2015年3月4日 下午9:08:49
 * @version V1.0
 */
package com.xlkfinance.bms.common.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSortUtil<E> {
	public void Sort(List<E> list, final String method, final String sort) {
		Collections.sort(list, new Comparator<E>() {

			@Override
			public int compare(E a, E b) {
				int ret = 0;
				try {
					// 获取m1的方法名
					Method m1 = a.getClass().getMethod(method, null);
					// 获取m2的方法名
					Method m2 = b.getClass().getMethod(method, null);

					if (sort != null && "desc".equals(sort)) {
						Object o2 = m2.invoke(((E) b), null);
						Object o1 = m1.invoke(((E) a), null);
						if (o2 == null) {
							return 0;
						}

						if (o1 == null) {
							return 1;
						}

						ret = o2.toString().compareTo(o1.toString());
					} else {
						// 正序排序
						Object o1 = m1.invoke(((E) a), null);
						Object o2 = m2.invoke(((E) b), null);

						if (o1 == null) {
							return 0;
						}

						if (o2 == null) {
							return 1;
						}

						ret = o1.toString().compareTo(o2.toString());
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ret;
			}

		});
	}
}
