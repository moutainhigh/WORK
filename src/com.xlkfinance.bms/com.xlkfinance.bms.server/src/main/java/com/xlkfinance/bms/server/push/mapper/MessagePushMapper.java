/**
 * 
 */
package com.xlkfinance.bms.server.push.mapper;

import java.util.List;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.push.MessagePush;

/**
 * 消息推送
 * @author huxinlong
 *
 */
public interface MessagePushMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 根据指定条件获取记录数
	 * @param messagePush
	 * @return
	 */
	public int listMessagePushCount(MessagePush messagePush);
	
	/**
	 * 保存消息推送对象 
	 * @param messagePush
	 * @return
	 */
	public int saveMessagePush(MessagePush messagePush);
	
	/**
	 * 根据指定条件获取消息推送列表
	 * @param messagePush
	 * @return
	 */
	public List<MessagePush > listMessagePush(MessagePush messagePush);
}
