#!/bin/sh 
CI_ROOT="/app/ci"
CI_PROJECTS="$CI_ROOT/projects/com.xlkfinance.bms"

CI_BMS_SERVER_TARGET="$CI_PROJECTS/com.xlkfinance.bms.server/target"
CI_BMS_WEB_TARGET="$CI_PROJECTS/com.xlkfinance.bms.web/target"
CI_BMS_RES_TARGET="$CI_PROJECTS/com.xlkfinance.bms.res/target"


APP_SERVER_ROOT="/app/BMS-SERVER"
TOMCAT_WEBAPPS="/app/apache-tomcat-7.0.59/webapps"
DTTM=date+%y%m%d%H%M%S

moveOldApp(){  
	mv $APP_SERVER_ROOT/BMS-SERVER /app/tmp/BMS-SERVER_$DTTM
	mv $TOMCAT_WEBAPPS/BMS /app/tmp/BMS_$DTTM
	mv $TOMCAT_WEBAPPS/BMS.war /app/tmp/BMS.war_$DTTM
	mv $TOMCAT_WEBAPPS/RES /app/tmp/RES_$DTTM
	mv $TOMCAT_WEBAPPS/RES.war /app/tmp/RES.war_$DTTM
}

deployNewApp(){  
	cp $CI_BMS_WEB_TARGET/BMS.war -d $TOMCAT_WEBAPPS
	cp $CI_BMS_RES_TARGET/RES.war -d $TOMCAT_WEBAPPS
	
	unzip $CI_BMS_SERVER_TARGET/BMS-SERVER.zip -d /app/BMS-SERVER
	chmod +x /app/BMS-SERVER/bin/*.sh
}  


moveOldApp
deployNewApp