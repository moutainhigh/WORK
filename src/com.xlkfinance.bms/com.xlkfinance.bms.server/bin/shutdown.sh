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
JVM_TIMEZONE="-Duser.timezone=Asia/Shanghai"

JAVA_OPTS="$JVM_MEMORY $JVM_TIMEZONE -XX:+AggressiveOpts -XX:+UseParallelGC"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=12345 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

MAIN="com.xlkfinance.bms.server.RunServer"

tradePortalPID=0
 
getTradeProtalPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $MAIN`
    if [ -n "$javaps" ]; then
        tradePortalPID=`echo $javaps | awk '{print $1}'`
    else
        tradePortalPID=0
    fi
}

shutdown(){
    getTradeProtalPID
    echo "================================================================================================================"
    if [ $tradePortalPID -ne 0 ]; then
        echo -n "Stopping $MAIN(PID=$tradePortalPID)..."
        kill -9 $tradePortalPID
        if [ $? -eq 0 ]; then
            echo "[Shutdown Success]"
            echo "================================================================================================================"
        else
            echo "[Shutdown Failed]"
            echo "================================================================================================================"
        fi
        getTradeProtalPID
        if [ $tradePortalPID -ne 0 ]; then
            shutdown
        fi
    else
        echo "$MAIN is not running"
        echo "================================================================================================================"
    fi
}

shutdown