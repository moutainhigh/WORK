package com.xlkfinance.bms.server.system.service.util;

import java.util.List;

public class Convert {

	public final String[] convertList2Array(List<String> pids,String str) {
		StringBuffer sb = new StringBuffer(pids.size());
		for (String pid : pids) {
			sb.append(pid).append(" ");
		}
		return sb.toString().split(" +");
	}

}
