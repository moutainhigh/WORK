/**
 * @Title: DateTypeHandler.java
 * @Package com.xlkfinance.bms.common.annotation
 * @Description: 日期转换器
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2015年2月3日 下午3:35:43
 * @version V1.0
 */
package com.xlkfinance.bms.common.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @ClassName: DateTypeHandler
 * @Description: 日期转换器
 * @author: Simon.Hoo
 * @date: 2015年2月27日 上午11:49:51
 */
public class DateTypeHandler implements TypeHandler<String> {

	@Override
	public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (parameter == null || "".equals(parameter)) {
				ps.setDate(i, null);
			} else {
				ps.setDate(i, new java.sql.Date(sdf.parse(parameter).getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getResult(ResultSet rs, String columnName) throws SQLException {
		String date = rs.getString(columnName);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = date == null ? null : sdf.parse(date);
			return String.valueOf(d == null ? "" : sdf.format(d));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		String date = rs.getString(columnIndex);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = date == null ? null : sdf.parse(date);
			return String.valueOf(d == null ? "" : sdf.format(d));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String date = cs.getString(columnIndex);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = date == null ? null : sdf.parse(date);
			return String.valueOf(d == null ? "" : sdf.format(d));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
