/**
 * @Title: MemcachedSessionDAO.java
 * @Package com.xlkfinance.bms.web.shiro
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2015年1月30日 上午9:16:59
 * @version V1.0
 */
package com.xlkfinance.bms.web.shiro;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MemcachedSessionDAO
 * @Description: 集群模式下Shiro Session共享
 * @author: Simon.Hoo
 * @date: 2015年1月30日 上午9:47:42
 */
@Component("memcachedSessionDAO")
public class MemcachedSessionDAO extends AbstractSessionDAO {
	private Logger logger = LoggerFactory.getLogger(MemcachedSessionDAO.class);

	public MemcachedSessionDAO() {

	}

	@Override
	public void delete(Session session) {
		try {

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		try {

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		try {

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}

	@Override
	protected Serializable doCreate(Session session) {
		try {

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return null;
	}

	@Override
	protected Session doReadSession(Serializable session) {
		try {

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return null;
	}

}
