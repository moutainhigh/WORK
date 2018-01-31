/**
 * 
 */
package com.xlkfinance.bms.xg.android.push.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;
import com.xlkfinance.bms.web.util.PropertiesUtil;

/**
 * 消息推送工厂
 * @author huxl
 *
 */
public class PushMessageFactory {

	private Logger logger = LoggerFactory.getLogger(PushMessageFactory.class);
	private XingeApp xgApp;
	private int accessId = PropertiesUtil.getIntValue("xg.push.acccess.id");
	private String secretkey = PropertiesUtil.getValue("xg.push.secret.key");
	/**
	 * 初始化信鸽消息推送组件
	 */
	public PushMessageFactory() {
		// TODO Auto-generated constructor stub
		logger.info("----init XingeApp start----");
		xgApp = new XingeApp(accessId,secretkey);
		logger.info("----init end----------");
	}

	/**
	 * 消息推送
	 * 默认推送所有设备
	 * @param pushMap 推送信息列表
	 * @return 推送结果
	 */
	public String pushMessage(Map<String,Object> pushMap){
		
		logger.info("-------XG push message start---------");
		JSONObject result = null;
		//推送类型，1：单个设备，2：单个账号，3：所有设备，4：按tag推送,5:所有账号
		int pushType = (Integer)pushMap.get("pushType");
		//消息类型，1：通知，2：透传消息，必填，默认1
		//int type = (Integer)pushMap.get("type");
		//消息发送类型，0：即时消息，1：间隔消息
		//int msgSendType = (Integer)pushMap.get("msgSendType");
		//账户列表
		JSONArray accountListJson = (JSONArray)pushMap.get("accountList");
		JSONArray tagListJson = (JSONArray)pushMap.get("tagList");
		//多个tag之间的运算关系,取AND或OR，只有一个tag时
		String tagsOp = (String)pushMap.get("tagsOp");
		//获取deviceToken
		List<String> tokenList = (List<String>)pushMap.get("deviceToken");
		Message message = (Message)pushMap.get("message");
		
		switch(pushType){
		//单个设备
		case 1:
			//按照deviceToken发送
			for(String token : tokenList){
				result = this.pushSingleDevice(token, message);
			}
			break;
		//单个帐号	
		case 2:
			result = this.pushSingleAccount(0, accountListJson.getString(0), message);
			break;
		//所有设备	
		case 3:
			result = this.pushAllDevices(0, message);
			break;
		//按tag推送
		case 4:
			
			List<String> tagList = new ArrayList<String>();
			if(tagListJson != null){
				
				if(!tagListJson.isEmpty()){
					
					Iterator<Object> it = tagListJson.iterator();
					while(it.hasNext()){
						tagList.add((String)it.next());
					}
				}
				/* 
				 * 做兼容性处理,因信鸽按照tag推送时,
				 * 它的API只能发送多个tag,
				 * 如果发生一个tag,则会出现参数错误,
				 * 导致无法推送
				 **/
				if(tagList.size() == 1){
					tagList.add(tagList.get(0));
				}
			}
			result = this.pushTags(0, tagList, tagsOp, message);
			break;
		//所有账号
		/*
		default:
			
			List<String> accountList = new ArrayList<String>();
			Iterator<Object> it = accountListJson.iterator();
			
			while(it.hasNext()){
				
				accountList.add((String)it.next());
			}
			result = this.pushAccountList(0, accountList, message);
			this.pushAccountList(0, accountList, message);
		*/
		}
		logger.info("--------------XG push message end------");
		return pushResult(result);
	}
	/**
	 * 单个设备消息推送
	 * @param deviceToken
	 * @param message
	 */
	private JSONObject pushSingleDevice(String deviceToken,Message message){
		
		JSONObject result = xgApp.pushSingleDevice(deviceToken, message);
		
		return result;
	}
	
	/**
	 * 单个账号推送消息
	 * @param deviceType
	 * @param account
	 * @param message
	 * @return
	 */
	private JSONObject pushSingleAccount(int deviceType,String account,Message message){
		
		JSONObject result = xgApp.pushSingleAccount(deviceType, account, message);
	
		return result;
	}
	
	/**
	 * 多个帐号推送消息
	 * @param deviceType
	 * @param accountList
	 * @param message
	 * @return
	 */
	private JSONObject pushAccountList(int deviceType,List<String> accountList,Message message){
		
		JSONObject result = xgApp.pushAccountList(deviceType, accountList, message);
		
		return result;
	}
	
	/**
	 * 推送消息给所有设备
	 * @param deviceType
	 * @param message
	 * @return
	 */
	private JSONObject pushAllDevices(int deviceType,Message message){
		
		JSONObject result = xgApp.pushAllDevice(deviceType, message);
		
		return result;
	}
	
	/**
	 * 按tag推送
	 * @param deviceType
	 * @param tagList
	 * @param tagOp
	 * @param message
	 * @return
	 */
	private JSONObject pushTags(int deviceType,List<String> tagList,String tagOp,Message message){
		
		JSONObject result = xgApp.pushTags(deviceType, tagList, tagOp, message);
		
		return result;
	}
	
	private String pushResult(JSONObject result){
		
		int code = result.getInt("ret_code");
		String msg = "";
		switch(code){
		
		case 0:
			msg ="发送成功";
			break;
		case -1:
			msg = "参数错误";
			break;
		case -2:
			msg = "请求时间戳不在有效期内";
			break;
		case -3:
			msg = "sign校验无效，请检查access id和secret key";
			break;
		case 2:
			msg = "参数错误，请对照文档检查请求参数";
			break;
		case 20:
			msg = "鉴权错误";
			break;
		case 40:
			msg = "推送的token没有在信鸽中注册";
			break;
		case 48:
			msg = "推送的账号没有在信鸽中注册";
			break;
		case 73:
			msg = "消息字符数超限";
			break;
		case 76:
			msg = "请求过于频繁，请稍后再试";
			break;
		case 100:
			msg = "APNS证书错误。请重新提交正确的证书";
			break;
		default:
			msg = "内部错误";
		}
		return msg;
	}
}
