package com.xlkfinance.bms.server;

import org.springframework.beans.factory.InitializingBean;

import com.achievo.framework.thrift.server.ThriftMultiBinaryServerFactory;

public class AsynchronousServer implements InitializingBean{

	private ThriftMultiBinaryServerFactory serverFactory;
	/**
	 * 延迟启动（毫秒）
	 */
	private long startDelay = 10000;
	
	
	public ThriftMultiBinaryServerFactory getServerFactory() {
		return serverFactory;
	}

	public void setServerFactory(ThriftMultiBinaryServerFactory serverFactory) {
		this.serverFactory = serverFactory;
	}
	

	public long getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(startDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				serverFactory.start();
			}
		}).start();
		
	}

	
	
}
