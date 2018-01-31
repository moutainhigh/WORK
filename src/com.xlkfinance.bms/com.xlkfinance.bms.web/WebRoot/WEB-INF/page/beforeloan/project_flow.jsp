<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/config.jsp"%>
<link rel="stylesheet" href="${ctx}/p/xlkfinance/css/style.css" type="text/css"></link>
<div style="width:auto;height:auto; overflow:hidden; position: relative;margin-top: 30px;">
<div class="flow">
	<div class="financial_line">
	<c:if test="${inloanMap['a'] == null}">
    	<div class="share charge" style="line-height: 35px;"><b></b>财务收费</div>
    </c:if>
	<c:if test="${inloanMap['a'] != null}">
    	<div class="share charge dispose"><b></b>财务收费<span>${inloanMap['a'].createrDateStr}&nbsp;${inloanMap['a'].handleAuthorName }</span></div>
    </c:if>
    <c:if test="${inloanMap['r'] == null}">
        <div class="jd t_zxf"><b></b>退咨询费<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['r'] != null}">
        <div class="jd t_zxf ycl"><b></b>退咨询费<span>${inloanMap['r'].createrDateStr}&nbsp;${inloanMap['r'].handleAuthorName }</span></div>
    </c:if>
    <c:if test="${inloanMap['t'] == null}">
        <div class="jd t_yj"><b></b>退佣金<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['t'] != null}">
        <div class="jd t_yj ycl"><b></b>退佣金<span>${inloanMap['t'].createrDateStr}&nbsp;${inloanMap['t'].handleAuthorName }</span></div>
    </c:if>
    </div>
    <!-- 贷前审批流程 -->
    <div class="approval_line">
    	<c:if test="${beforeLoanMap['a'] == null}">
    		<div class="jd khjl dqzt"><b></b>客户经理申请<span>&nbsp;</span></div>
        </c:if>
        <c:if test="${beforeLoanMap['a'] != null}">
    		<div class="jd khjl ycl"><b></b>客户经理申请<span>${beforeLoanMap['a'].createrDateStr}&nbsp;${beforeLoanMap['a'].handleAuthorName }</span></div>
        </c:if>
        
        <c:if test="${beforeLoanMap['b'] == null}">
        	<div class="share tiral" style="line-height: 35px;"><b></b>风控初审</div>
        </c:if>
        <c:if test="${beforeLoanMap['b'] != null}">
        	<div class="share tiral dispose"><b></b>风控初审<span>${beforeLoanMap['b'].createrDateStr}&nbsp;${beforeLoanMap['b'].handleAuthorName }</span></div>
        </c:if>
        
        <!-- 贷前审批根据金额进行判断 -->
        <!-- 大于1000开始 -->
        <c:if test="${project.projectGuarantee.loanMoney>10000000 }">
        <div class="shenpi">
        	<c:if test="${beforeLoanMap['b_a'] == null}">
	        	<div class="jd "><b></b>合规复审<span>&nbsp;</span></div>
	        </c:if>
	        <c:if test="${beforeLoanMap['b_a'] != null}">
	        	<div class="jd ycl"><b></b>合规复审<span>${beforeLoanMap['c'].createrDateStr}&nbsp;${beforeLoanMap['c'].handleAuthorName }</span></div>
	        </c:if>
        	
	        <c:if test="${beforeLoanMap['c'] == null}">
	        	<div class="jd "><b></b>风控复审<span>&nbsp;</span></div>
	        </c:if>
	        <c:if test="${beforeLoanMap['c'] != null}">
	        	<div class="jd ycl"><b></b>风控复审<span>${beforeLoanMap['c'].createrDateStr}&nbsp;${beforeLoanMap['c'].handleAuthorName }</span></div>
	        </c:if>
            
            <c:if test="${beforeLoanMap['d'] == null}">
	        	<div class="jd "><b></b>风控终审<span>&nbsp;</span></div>
	        </c:if>
	        <c:if test="${beforeLoanMap['d'] != null}">
	        	<div class="jd ycl"><b></b>风控终审<span>${beforeLoanMap['d'].createrDateStr}&nbsp;${beforeLoanMap['d'].handleAuthorName }</span></div>
	        </c:if>
        </div>
        	<c:if test="${beforeLoanMap['j'] == null}">
	        	<div class="share approval" style="line-height: 35px;"><b></b>总经理审批</div>
	        </c:if>
	        <c:if test="${beforeLoanMap['j'] != null}">
	        	<div class="share approval dispose"><b></b>总经理审批<span>${beforeLoanMap['j'].createrDateStr}&nbsp;${beforeLoanMap['j'].handleAuthorName }</span></div>
	        </c:if>
        </c:if>
        <!-- 大于1000结束 -->
         <!-- 小于1000大于500万开始-->
        <c:if test="${project.projectGuarantee.loanMoney<=10000000 && project.projectGuarantee.loanMoney >5000000 }">
        <div class="shenpi">
        	
        	<c:if test="${beforeLoanMap['b_a'] == null}">
	        	<div class="jd "><b></b>合规复审<span>&nbsp;</span></div>
	        </c:if>
	        <c:if test="${beforeLoanMap['b_a'] != null}">
	        	<div class="jd ycl"><b></b>合规复审<span>${beforeLoanMap['c'].createrDateStr}&nbsp;${beforeLoanMap['c'].handleAuthorName }</span></div>
	        </c:if>
	        
            <c:if test="${beforeLoanMap['c'] == null}">
	        	<div class="jd "><b></b>风控复审<span>&nbsp;</span></div>
	        </c:if>
	        <c:if test="${beforeLoanMap['c'] != null}">
	        	<div class="jd ycl"><b></b>风控复审<span>${beforeLoanMap['c'].createrDateStr}&nbsp;${beforeLoanMap['c'].handleAuthorName }</span></div>
	        </c:if>
        </div>
        	<c:if test="${beforeLoanMap['d'] == null}">
	        	<div class="share approval" style="line-height: 35px;"><b></b>风控终审</div>
	        </c:if>
	        <c:if test="${beforeLoanMap['d'] != null}">
	        	<div class="share approval dispose"><b></b>风控终审<span>${beforeLoanMap['d'].createrDateStr}&nbsp;${beforeLoanMap['d'].handleAuthorName }</span></div>
	        </c:if>
        </c:if>
        <!-- 小于1000大于500万结束-->
        <!-- 小于500万开始 -->
        <c:if test="${project.projectGuarantee.loanMoney<=5000000 }">
        	<div class="shenpi">
        		<c:if test="${beforeLoanMap['b_a'] == null}">
	        		<div class="jd "><b></b>合规复审<span>&nbsp;</span></div>
		        </c:if>
		        <c:if test="${beforeLoanMap['b_a'] != null}">
		        	<div class="jd ycl"><b></b>合规复审<span>${beforeLoanMap['c'].createrDateStr}&nbsp;${beforeLoanMap['c'].handleAuthorName }</span></div>
		        </c:if>
        		<div class=""><b></b>&nbsp;<span>&nbsp;</span></div>
            	<div class=""><b></b>&nbsp;<span>&nbsp;</span></div>
        	</div>
        	<c:if test="${beforeLoanMap['c'] == null}">
	        	<div class="share approval" style="line-height: 35px;"><b></b>风控复审</div>
	        </c:if>
	        <c:if test="${beforeLoanMap['c'] != null}">
	        	<div class="share approval dispose"><b></b>风控复审<span>${beforeLoanMap['c'].createrDateStr}&nbsp;${beforeLoanMap['c'].handleAuthorName }</span></div>
	        </c:if>
        </c:if>
         <!-- 小于500万结束 -->
    </div>
    <!-- 贷前审批流程 -->
    <div class="sjcd">
      <c:if test="${inloanMap['b'] == null}">
    	<div class="jd"><b></b>收件<span>&nbsp;&nbsp;</span></div>
      </c:if>
      <c:if test="${inloanMap['b'] != null}">
    	<div class="jd ycl"><b></b>收件<span>${inloanMap['b'].createrDateStr}&nbsp;${inloanMap['b'].handleAuthorName }</span></div>
      </c:if>
      <c:if test="${inloanMap['c'] == null}">
    	<div class="jd"><b></b>执行岗备注<span>&nbsp;&nbsp;</span></div>
      </c:if>
      <c:if test="${inloanMap['c'] != null}">
    	<div class="jd ycl"><b></b>执行岗备注<span>${inloanMap['c'].createrDateStr}&nbsp;${inloanMap['c'].handleAuthorName }</span></div>
      </c:if>
      <c:if test="${inloanMap['d'] == null}">
    	<div class="jd"><b></b>查诉讼<span>&nbsp;&nbsp;</span></div>
      </c:if>
      <c:if test="${inloanMap['d'] != null}">
    	<div class="jd ycl"><b></b>查诉讼<span>${inloanMap['d'].createrDateStr}&nbsp;${inloanMap['d'].handleAuthorName }</span></div>
      </c:if>

    </div>
     <c:if test="${inloanMap['e'] == null}">
      <div class="share cha_ss" style="line-height: 35px;"><b></b>查档</div>
     </c:if>
     <c:if test="${inloanMap['e'] != null}">
      <div class="share cha_ss dispose"><b></b>查档<span>${inloanMap['e'].createrDateStr}&nbsp;${inloanMap['e'].handleAuthorName }</span></div>
     </c:if>
     <c:if test="${inloanMap['f'] == null}">
       <div class="share cha_fh" style="line-height: 35px;"><b></b>查档复核</div>
     </c:if>
     <c:if test="${inloanMap['f'] != null}">
       <div class="share cha_fh dispose"><b></b>查档复核<span>${inloanMap['f'].createrDateStr}&nbsp;${inloanMap['f'].handleAuthorName }</span></div>
     </c:if>
    
    <div class="cw_fk">
    <c:if test="${inloanMap['g'] == null}">
        <div class="jd fk_sq"><b></b>放款申请<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['g'] != null}">
        <div class="jd fk_sq ycl"><b></b>放款申请<span>${inloanMap['g'].createrDateStr}&nbsp;${inloanMap['g'].handleAuthorName }</span></div>
    </c:if>
    <c:if test="${inloanMap['g_a'] == null}">
        <div class="jd fk_sq"><b></b>资金主管审核<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['g_a'] != null}">
        <div class="jd fk_sq ycl"><b></b>资金主管审核<span>${inloanMap['g_a'].createrDateStr}&nbsp;${inloanMap['g_a'].handleAuthorName }</span></div>
    </c:if>
    <%-- <c:if test="${realLoan<3000000 }">
     <div class="jd"><b></b>&nbsp;<span>&nbsp;&nbsp;</span></div>
    </c:if> --%>
    <c:if test="${realLoan>=3000000 }">
    <c:if test="${inloanMap['g_b'] == null}">
        <div class="jd fk_sq"><b></b>财务总监审核<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['g_b'] != null}">
        <div class="jd fk_sq ycl"><b></b>财务总监审核<span>${inloanMap['g_b'].createrDateStr}&nbsp;${inloanMap['g_b'].handleAuthorName }</span></div>
    </c:if>
    </c:if>
    
    </div>
    <c:if test="${inloanMap['g_c'] == null}">
        <div class="share cwfk" style="line-height: 35px;"><b></b>财务放款</div>
    </c:if>
    <c:if test="${inloanMap['g_c'] != null}">
        <div class="share cwfk dispose"><b></b>财务放款<span>${inloanMap['g_c'].createrDateStr}&nbsp;${inloanMap['g_c'].handleAuthorName }</span></div>
    </c:if>
    
    <div class="yw_bl">
     <c:if test="${inloanMap['h'] == null}">
        <div class="jd ywbl"><b></b>赎楼<span>&nbsp;&nbsp;</span></div>
     </c:if>
     <c:if test="${inloanMap['h'] != null}">
        <div class="jd ywbl ycl"><b></b>赎楼<span>${inloanMap['h'].createrDateStr}&nbsp;${inloanMap['h'].handleAuthorName }</span></div>
     </c:if>
     <c:if test="${inloanMap['j'] == null}">
        <div class="jd ywbl"><b></b>取旧证<span>&nbsp;&nbsp;</span></div>
     </c:if>
     <c:if test="${inloanMap['j'] != null}">
        <div class="jd ywbl ycl"><b></b>取旧证<span>${inloanMap['j'].createrDateStr}&nbsp;${inloanMap['j'].handleAuthorName }</span></div>
     </c:if>
     <c:if test="${inloanMap['k'] == null}">
        <div class="jd ywbl"><b></b>注销抵押<span>&nbsp;&nbsp;</span></div>
     </c:if>
     <c:if test="${inloanMap['k'] != null}">
        <div class="jd ywbl ycl"><b></b>注销抵押<span>${inloanMap['k'].createrDateStr}&nbsp;${inloanMap['k'].handleAuthorName }</span></div>
     </c:if>
     <c:if test="${project.businessCategory==13755}"><!-- 交易类型 -->
     <c:if test="${inloanMap['l'] == null}">
        <div class="jd ywbl"><b></b>过户<span>&nbsp;&nbsp;</span></div>
     </c:if>
     <c:if test="${inloanMap['l'] != null}">
        <div class="jd ywbl ycl"><b></b>过户<span>${inloanMap['l'].createrDateStr}&nbsp;${inloanMap['l'].handleAuthorName }</span></div>
     </c:if>
     <c:if test="${inloanMap['m'] == null}">
        <div class="jd ywbl"><b></b>取新证<span>&nbsp;&nbsp;</span></div>
     </c:if>
     <c:if test="${inloanMap['m'] != null}">
        <div class="jd ywbl ycl"><b></b>取新证<span>${inloanMap['m'].createrDateStr}&nbsp;${inloanMap['m'].handleAuthorName }</span></div>
     </c:if>
     </c:if>
     <c:if test="${inloanMap['n'] == null}">
        <div class="jd ywbl"><b></b>抵押<span>&nbsp;&nbsp;</span></div>
     </c:if>
     <c:if test="${inloanMap['n'] != null}">
        <div class="jd ywbl ycl"><b></b>抵押<span>${inloanMap['n'].createrDateStr}&nbsp;${inloanMap['n'].handleAuthorName }</span></div>
     </c:if>
     <c:if test="${inloanMap['n'] == null}">
        <div class="jd ywbl"><b></b>业务办理完成<span>&nbsp;&nbsp;</span></div>
     </c:if>
     <c:if test="${inloanMap['n'] != null}">
        <div class="jd ywbl ycl"><b></b>业务办理完成<span>${inloanMap['n'].createrDateStr}</span></div>
     </c:if>
    </div>
    
    <div class="hk">
    <c:if test="${inloanMap['o'] == null}">
        <div class="jd hk_tk"><b></b>回款<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['o'] != null}">
        <div class="jd hk_tk ycl"><b></b>回款<span>${inloanMap['o'].createrDateStr}&nbsp;${inloanMap['o'].handleAuthorName }</span></div>
    </c:if>
    <c:if test="${inloanMap['p'] == null}">
        <div class="jd hk_tk"><b></b>逾期费确认<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['p'] != null}">
        <div class="jd hk_tk ycl"><b></b>逾期费确认<span>${inloanMap['p'].createrDateStr}&nbsp;${inloanMap['p'].handleAuthorName }</span></div>
    </c:if>
    <c:if test="${inloanMap['q'] == null}">
        <div class="jd hk_tk"><b></b>退手续费<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['q'] != null}">
        <div class="jd hk_tk ycl"><b></b>退手续费<span>${inloanMap['q'].createrDateStr}&nbsp;${inloanMap['q'].handleAuthorName }</span></div>
    </c:if>
    <c:if test="${inloanMap['s'] == null}">
        <div class="jd hk_tk"><b></b>退尾款<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['s'] != null}">
        <div class="jd hk_tk ycl"><b></b>退尾款<span>${inloanMap['s'].createrDateStr}&nbsp;${inloanMap['s'].handleAuthorName }</span></div>
    </c:if>
    </div>
    
    <div class="ywc">
    <c:if test="${inloanMap['o'] == null || inloanMap['n'] == null}">
    	<div class="share" style="line-height: 35px;"><b></b>已完成</div>
    </c:if>
    <c:if test="${inloanMap['o'] != null && inloanMap['n'] != null}">
    	<div class="share dispose"><b></b>已完成<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['u'] == null}">
        <div class="jd ygd"><b></b>已归档<span>&nbsp;&nbsp;</span></div>
    </c:if>
    <c:if test="${inloanMap['u'] != null}">
        <div class="jd ygd ycl"><b></b>已归档<span>${inloanMap['u'].createrDateStr}&nbsp;${inloanMap['u'].handleAuthorName }</span></div>
    </c:if>
    </div>
</div>
</div>
	