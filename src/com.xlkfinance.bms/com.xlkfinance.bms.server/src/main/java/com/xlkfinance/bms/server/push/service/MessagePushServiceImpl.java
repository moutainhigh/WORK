/**
 * 
 */
package com.xlkfinance.bms.server.push.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.push.MessagePush;
import com.xlkfinance.bms.rpc.push.MessagePushService.Iface;
import com.xlkfinance.bms.server.push.mapper.MessagePushMapper;

/**
 * 消息推送
 * @author huxinlong
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("messagePushServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.push.MessagePushService")
public class MessagePushServiceImpl implements Iface {

	@Resource(name="messagePushMapper")
	private MessagePushMapper messagePushMapper; 
	/* (non-Javadoc)
	 * @see com.xlkfinance.bms.rpc.push.MessagePushService.Iface#listMessagePush(com.xlkfinance.bms.rpc.push.MessagePush)
	 */
	@Override
	public List<MessagePush> listMessagePush(MessagePush messagePush)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return messagePushMapper.listMessagePush(messagePush);
	}

	/* (non-Javadoc)
	 * @see com.xlkfinance.bms.rpc.push.MessagePushService.Iface#saveMessagePush(com.xlkfinance.bms.rpc.push.MessagePush)
	 */
	@Override
	public int saveMessagePush(MessagePush messagePush)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		messagePushMapper.saveMessagePush(messagePush);
		return 0;
	}

	@Override
	public int listMessagePushCount(MessagePush messagePush)
			throws ThriftServiceException, TException {
		// TODO Auto-generated method stub
		return messagePushMapper.listMessagePushCount(messagePush);
	}

}
