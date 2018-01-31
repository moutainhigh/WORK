<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>主页</title>
<%@ include file="../../common.jsp"%>
<link rel="shortcut icon" href="favicon.ico">
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow: hidden">
 <div id="wrapper">
 
  <!--左侧导航开始-->
  <nav class="navbar-default navbar-static-side" role="navigation">
  <div class="nav-close">
   <i class="fa fa-times-circle"></i>
  </div>
  <div class="sidebar-collapse">
   <ul class="nav" id="side-menu">
    <li class="nav-header">
     <div class="dropdown profile-element">
      <span><img alt="image" class="img-circle" src="img/logo.png" /></span> 
      <%-- <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
       class="clear"
      > <span class="block m-t-xs"><strong class="font-bold">${loginUser.realName }</strong></span> <span class="text-muted text-xs block">${userTypeMap[loginUser.userType]}<b class="caret"></b></span>
      </span>
      </a>
      <ul class="dropdown-menu animated fadeInRight m-t-xs">
       <!-- <li><a class="J_menuItem" href="toFormAvatar.action">修改头像</a></li> -->
       <li><a class="J_menuItem" href="toProfile.action">个人资料</a></li>
       <li class="divider"></li>
       <li><a href="logout.action">安全退出</a></li>
      </ul> --%>
     </div>
     <div class="logo-element"><img alt="image" class="img-circle" src="img/logo_small.png" /></div>
    </li>
    <!-- 菜单加载 -->
    <c:forEach items="${sysMenus}" var="menu">
     <li>
       <a href="#"> <i class="${menu.iconCls}"></i> <span class="nav-label">${menu.menuName }</span> <span class="fa arrow"></span></a>
       <ul class="nav nav-second-level" style="background:#f6fafd;">
       <c:forEach items="${menu.childrenList}" var="childrenMenu">
          <li><a class="J_menuItem" onclick="openTab('${ctx}${childrenMenu.url}','${childrenMenu.menuName}','${ctx}${childrenMenu.url}')">${childrenMenu.menuName}</a></li>
       </c:forEach>
       </ul>
      </li>
    </c:forEach>
  </div>
  </nav>
  <!--左侧导航结束-->
  
  
  <!--右侧部分开始-->
  <div id="page-wrapper" class="gray-bg dashbard-1">
   <div class="row border-bottom">
 	
    <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">

    <div class="navbar-header" style="display: none;">
     <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
    </div>
    <!-- <ul class="nav navbar-top-links navbar-right">
     <li class="dropdown hidden-xs"><a class="right-sidebar-toggle" aria-expanded="false"> <i class="fa fa-tasks"></i> 主题
     </a></li>
    </ul> -->
    
    <ul class="nav navbar-top-links J_tabExit"><a href="logout.action" class="roll-nav roll-right "><i class="fa fa fa-sign-out"></i> 退出</a></ul>
    
    <ul class="nav navbar-top-links navbar-right cooperateStatus${cooperateStatus}">${cooperateStatusMap[cooperateStatus] }</ul>
    <ul class="nav navbar-top-links navbar-right auditStatus${auditStatus}">${auditStatusMap[auditStatus] }</ul>
    
    <ul class="nav navbar-top-links navbar-right">
     <a href="toProfile.action" class="fa J_menuItem"><li class="dropdown hidden-xs jg_hand"><c:if test="${loginUser.userType==1 }">${orgName}</c:if><c:if test="${loginUser.userType!=1 }">${loginUser.realName }</c:if> <b>( ${userTypeMap[loginUser.userType]} )</b></li></a>
    </ul>
   
    </nav>
   </div>
   <div class="row content-tabs" style="border-left: 1px #e7eaec solid;">
    <button class="roll-nav roll-left J_tabLeft">
     <i class="fa fa-backward"></i>
    </button>
    <nav class="page-tabs J_menuTabs">
    <div class="page-tabs-content" id="page-tabs-content">
    <!-- 机构首页 -->
    <c:if test="${loginUser.userType==1 || loginUser.userType==3}">
     <a href="javascript:;" name="J_menuTab" class="active J_menuTab" data-id="toOrgIndexContent.action">首页</a>
    </c:if>
    <!-- 合伙人首页 -->
    <c:if test="${loginUser.userType==2}">
     <a href="javascript:;" name="J_menuTab" class="active J_menuTab" data-id="toPartnerIndexContent.action">首页</a>
    </c:if>
    </div>
    </nav>
    <button class="roll-nav roll-right J_tabRight">
     <i class="fa fa-forward"></i>
    </button>
    <div class="btn-group roll-nav roll-right">
     <button class="dropdown J_tabClose" data-toggle="dropdown">
      关闭操作<span class="caret"></span>

     </button>
     <ul role="menu" class="dropdown-menu dropdown-menu-right">
      <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
      <li class="divider"></li>
      <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
      <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
     </ul>
    </div>
    
   </div>
   <div class="row J_mainContent" id="content-main" style="border-left: 1px #e7eaec solid;">
   <!-- 机构首页 -->
    <c:if test="${loginUser.userType==1 || loginUser.userType==3}">
    <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="toOrgIndexContent.action" frameborder="0" data-id="toOrgIndexContent.action" seamless></iframe>
    </c:if>
   <!-- 合伙人首页 -->
    <c:if test="${loginUser.userType==2}">
    <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="toPartnerIndexContent.action" frameborder="0" data-id="toPartnerIndexContent.action" seamless></iframe>
    </c:if>
   </div>
   <div class="footer" style="border-left: 1px #e7eaec solid;">
    <!-- <div class="pull-right">&copy; 2015-2016</div> -->
    <div class="pull-right tel_time">（工作日：9:00 - 18:00）</div>
    <div class="pull-right tel">400-9393-888</div>
   </div>
  </div>
  <!--右侧部分结束-->
  <!--右侧边栏开始-->
  
  <div id="right-sidebar">
   <div class="sidebar-container">

    <ul class="nav nav-tabs navs-3">

     <li class="active"><a data-toggle="tab" href="#tab-1"> <i class="fa fa-gear"></i> 主题
     </a></li>
    </ul>

    <div class="tab-content">
     <div id="tab-1" class="tab-pane active">
      <div class="sidebar-title">
       <h3>
        <i class="fa fa-comments-o"></i> 主题设置
       </h3>
       <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
      </div>
      <div class="skin-setttings">
       <div class="title">主题设置</div>
       <div class="setings-item">
        <span>收起左侧菜单</span>
        <div class="switch">
         <div class="onoffswitch">
          <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu"> <label class="onoffswitch-label" for="collapsemenu">
           <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
          </label>
         </div>
        </div>
       </div>
       <div class="setings-item">
        <span>固定顶部</span>

        <div class="switch">
         <div class="onoffswitch">
          <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar"> <label class="onoffswitch-label" for="fixednavbar">
           <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
          </label>
         </div>
        </div>
       </div>
       <div class="setings-item">
        <span> 固定宽度 </span>

        <div class="switch">
         <div class="onoffswitch">
          <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout"> <label class="onoffswitch-label" for="boxedlayout">
           <span class="onoffswitch-inner"></span> <span class="onoffswitch-switch"></span>
          </label>
         </div>
        </div>
       </div>
       <div class="title">皮肤选择</div>
       <div class="setings-item default-skin nb">
        <span class="skin-name "> <a href="#" class="s-skin-0"> 默认皮肤 </a>
        </span>
       </div>
       <div class="setings-item blue-skin nb">
        <span class="skin-name "> <a href="#" class="s-skin-1"> 蓝色主题 </a>
        </span>
       </div>
       <!-- <div class="setings-item yellow-skin nb">
        <span class="skin-name "> <a href="#" class="s-skin-3"> 黄色/紫色主题 </a>
        </span>
       </div> -->
      </div>
     </div>
    </div>

   </div>
  </div>
 </div>
 <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
 <script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
 <script src="js/plugins/layer/layer.min.js"></script>
 <script src="js/hplus.min.js?v=4.1.0"></script>
 <script type="text/javascript" src="js/contabs.min.js"></script>
 <script src="js/plugins/pace/pace.min.js"></script>
</body>
</html>