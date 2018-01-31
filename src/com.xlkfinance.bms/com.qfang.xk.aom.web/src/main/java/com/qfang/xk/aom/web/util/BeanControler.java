package com.qfang.xk.aom.web.util;

import java.util.Vector;

/**
 * 
 * @ClassName: BeanControler
 * @Description: TODO
 * @author: Chong.Zeng
 * @date: 2015年1月5日 下午7:45:34
 */
public class BeanControler {
	private static BeanControler beanControler = new BeanControler();
	private Vector<FileUploadStatus> vector = new Vector<FileUploadStatus>();

	private BeanControler() {
	}

	public static BeanControler getInstance() {
		return beanControler;
	}

	private int indexOf(String strID) {
		int nReturn = -1;
		for (int i = 0; i < vector.size(); i++) {
			FileUploadStatus status = (FileUploadStatus) vector.elementAt(i);
			if (status.getUploadAddr().equals(strID)) {
				nReturn = i;
				break;
			}
		}
		return nReturn;
	}

	public FileUploadStatus getUploadStatus(String strID) {
		return (FileUploadStatus) vector.elementAt(indexOf(strID));
	}

	public void setUploadStatus(FileUploadStatus status) {
		int nIndex = indexOf(status.getUploadAddr());
		if (-1 == nIndex) {
			vector.add(status);
		} else {
			vector.insertElementAt(status, nIndex);
			vector.removeElementAt(nIndex + 1);
		}
	}

	public void removeUploadStatus(String strID) {
		int nIndex = indexOf(strID);
		if (-1 != nIndex)
			vector.removeElementAt(nIndex);
	}
}
