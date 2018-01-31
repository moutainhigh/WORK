@echo off

setlocal enabledelayedexpansion
title= Server Application

:start

	echo.
	echo ===============================================================================
	echo JAVA_HOME=%JAVA_HOME%	
	echo ===============================================================================
	
	
	set JAVA="%JAVA_HOME%"\bin\java
	set JVM_MEMORY=-Xms512M -Xmx3048M -Xmn340M -XX:MaxPermSize=768m -server
	set JVM_TIMEZONE=-Duser.timezone=Asia/Shanghai
	
	set JAVA_OPTS= %JVM_MEMORY% %JVM_TIMEZONE% -XX:+AggressiveOpts -XX:+UseParallelGC 

	set MAIN=com.xlkfinance.bms.server.RunServer
	
	set ETC=..\etc;
	set CONFIG=..\config;
	set CP=!ETC!;%CONFIG%;
	
	set LIBS=..\lib
	for /f %%i in ('dir /b %LIBS%\*.jar^|sort') do (
	   set CP=!CP!%LIBS%\%%i;
	)
	
	set MODULES=..\module
	for /f %%i in ('dir /b %MODULES%\*.jar^|sort') do (
	   set CP=!CP!%MODULES%\%%i;
	)
	
	goto showenv

:showenv
	echo ===============================================================================	
	echo   CLASSPATH: %CP%
	echo ===============================================================================
	echo   Server Application is running......
	echo   JAVA_HOME: %JAVA_HOME%
	echo   JVM_MEMORY: %JVM_MEMORY%
	echo   JVM_TIMEZONE: %JVM_TIMEZONE% 
	echo   MAIN_CLASS: %MAIN%
	echo ===============================================================================
	
	goto startupapp

:startupapp
	echo.
	%JAVA% %JAVA_OPTS% -cp %CP% %MAIN%
	echo.
	goto end
	
:end
	goto exit
:exit
	pause
rem	exit