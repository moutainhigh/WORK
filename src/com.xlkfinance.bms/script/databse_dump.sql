#expdp 导出DUMP文件
expdp bmsuser/Ab123456 directory=dump_dir dumpfile=BMS20150311.dmp

#impdp 导入DUMP文件
impdp bmsuser/Ab123456 directory=dump_dir dumpfile=BMS20150311.dmp schemas='BMSUSER'