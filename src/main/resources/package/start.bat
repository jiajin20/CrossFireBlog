@echo off
setlocal & pushd

set MAIN_CLASS=awb.TomcatServer

set JAVA_OPTS=-Xms256m -Xmx2048m -Dfile.encoding=UTF-8

set APP_BASE_PATH=%~dp0
set CP=%APP_BASE_PATH%WebContent\WEB-INF\lib\*;%APP_BASE_PATH%WebContent\WEB-INF\classes

java -Xverify:none %JAVA_OPTS% -cp %CP% %MAIN_CLASS%
goto:eof