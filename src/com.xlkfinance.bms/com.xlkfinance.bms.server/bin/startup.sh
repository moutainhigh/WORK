#!/bin/sh 
if [ "$JAVA_HOME" = "" ] ;then
echo "JAVA_HOME did not find the current system environment variable, if set to the effective JAVA_HOME? (y/n)"
read FLAG
if [ "$FLAG" = "y"] ;then
echo "set JAVA_HOME="
read JAVA_HOME
fi
fi
echo ===============================================================================
echo JAVA_HOME=$JAVA_HOME
echo ===============================================================================


JAVA="$JAVA_HOME/bin/java"
JVM_MEMORY="-Xms1024M -Xmx4096M -Xmn512M -XX:PermSize=512M -XX:MaxPermSize=2048m -server"
JVM_TIMEZONE="-Duser.timezone=Asia/Shanghai"

JAVA_OPTS="$JVM_MEMORY $JVM_TIMEZONE -XX:+AggressiveOpts -XX:+UseParallelGC"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=12345 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

MAIN="com.xlkfinance.bms.server.RunServer"

ETC="../etc:"
CONFIG="../config:"

CP="$ETC$CONFIG"

LIBS="../lib"
for FILE in $LIBS/*.jar
do 
   CP="$CP$FILE:"
done

MODULES="../module"
for FILE in $MODULES/*.jar
do 
   CP="$CP$FILE:"
done

echo ===============================================================================
echo   CLASSPATH: $CP
echo ===============================================================================
echo   Server Application is running......
echo   JAVA_HOME: $JAVA_HOME
echo   JVM_MEMORY: $JVM_MEMORY
echo   JVM_TIMEZONE: $JVM_TIMEZONE
echo   MAIN_CLASS: $MAIN
echo ===============================================================================

tradePortalPID=0  
getTradeProtalPID(){  
	javaps=`$JAVA_HOME/bin/jps -l | grep $MAIN`  
	if [ -n "$javaps" ]; then  
		tradePortalPID=`echo $javaps | awk '{print $1}'`  
	else  
		tradePortalPID=0  
	fi  
}  

startup(){  
	echo "VAR JAVA: $JAVA"
	echo "VAR JAVA_OPTS : $JAVA_OPTS"
	echo "VAR CP: $CP"
	echo "VAR MAIN: $MAIN"

	getTradeProtalPID  
	echo "======================================================================================"  
	if [ $tradePortalPID -ne 0 ]; then  
		echo "$MAIN already started(PID=$tradePortalPID)"  
		echo "======================================================================================"  
	else  
		echo -n "Starting $MAIN"  
		$JAVA_HOME/bin/java $JAVA_OPTS -cp $CP $MAIN >> ../logs/server.log &  
		getTradeProtalPID  
		if [ $tradePortalPID -ne 0 ]; then  
			echo "(PID=$tradePortalPID)...[Success]"  
			echo "======================================================================================"  
		else  
			echo "[Failed]"  
			echo "======================================================================================"  
		fi  
	fi  
} 

startup