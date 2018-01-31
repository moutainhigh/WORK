目录说明：
bin: 可执行文件，启动、停止脚本；
config: 日常运行中经常修改的配置文件，如：数据库连接、日志级别、系统能数等；
etc: 系统运行配置文件，通常不需要修改，直接修改config目录下的配置文件；
lib: 所有依赖的JAR包，包括本项目的所有模块及第三方JAR包；
logs: 系统运行日志文件；

运行说明：
1. 进入bin目录，运行startup.bat (Windows系统）或startup.sh (Linux系统)；

停止说明：
1. 进入bin目录，运行shutdown.bat (Windows系统）或shutdown.sh (Linux系统)；
