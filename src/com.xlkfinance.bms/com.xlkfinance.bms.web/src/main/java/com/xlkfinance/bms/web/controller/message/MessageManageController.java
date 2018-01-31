/**
 * 
 */
package com.xlkfinance.bms.web.controller.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.push.MessagePush;
import com.xlkfinance.bms.rpc.push.MessagePushService;
import com.xlkfinance.bms.web.controller.BaseController;

/**
 * @description 消息推送
 * @author huxinlong
 *
 */
@Controller
@RequestMapping("/msgManageController")
public class MessageManageController extends BaseController {

	/**
	 * 消息推送列表页面初始化
	 * @return
	 */
	@RequestMapping(value="init")
	public String init(){
		
		return "push/message_push_list";
	}
	
	
	/**
	 * 获取消息推送列表
	 * @param messagePush
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="listMessagePush")
	@ResponseBody
	public void listMessagePush(MessagePush messagePush,HttpServletRequest request,HttpServletResponse response){
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_PUSH, "MessagePushService");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<MessagePush> results = new ArrayList<MessagePush>();
		int rows = 0;
		try {
			MessagePushService.Client client = (MessagePushService.Client) clientFactory.getClient();
			results = client.listMessagePush(messagePush);
			rows = client.listMessagePushCount(messagePush);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		resultMap.put("rows",results);
		resultMap.put("total", rows);
		
		outputJson(resultMap, response);
	}
}
