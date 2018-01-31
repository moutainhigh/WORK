namespace java com.xlkfinance.bms.rpc.push
include "Common.thrift"

/*
* 消息推送
* 表:biz_message_push 
* @author huxinlong
* @date 2016-1-27
* 
*
*/
struct MessagePush{
  1: i32 id;
  2: string title;
  3: string content;
  4: i32 deviceType;
  5: string userName;
  6: string sendTime;
  7: i32 page;
  8: i32 rows;
  9: i32 bizType;
  10: string remark;
}

/*
* 消息推送服务接口
* @author huxinlong
* @date 2016-1-27
* 
*/
service MessagePushService{
	/*
	* 根据指定条件获取消息推送列表	 
	* @author 胡新龙
	* @date 2016-1-27
	*/
	list<MessagePush> listMessagePush (1:MessagePush messagePush) throws(1:Common.ThriftServiceException e);
	/*
	* 根据指定条件获取消息推送记录数	 
	* @author 胡新龙
	* @date 2016-1-27
	*/
	i32 listMessagePushCount(1:MessagePush messagePush) throws(1:Common.ThriftServiceException e);
	/*
	* 保存消息推送对象	 
	* @author 胡新龙
	* @date 2016-1-27
	*/
	i32 saveMessagePush (1:MessagePush messagePush) throws(1:Common.ThriftServiceException e);
}