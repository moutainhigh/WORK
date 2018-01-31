 
 
//luozhonghua  2015/4/8
//pojo公共模块
namespace java com.xlkfinance.bms.rpc.file

//文件
struct FileInfo{
	1: i32 dataId;    // 对应模块的文件表主键如BIZ_PROJECT_VIOLATION_FILE 的主键
	2: i32 projectId;  
	3: i32 fileId;   // biz_file 表主键
	4: i32 uploadUserId;  
	5: i32 fileProperty;//文件类型
	6: string filePropertyName;
	7: string fileDesc; // 描述
	8: i32 status;
	9: string fileName; 
	10: string fileType;
	11: i32 fileSize;
	12: string fileUrl;
	13: string uploadDttm;
	14: string cusType;
	15: i32 page;
	16: i32 rows;
	17: i32 refId;//管理的业务模块表的ID，如违约表 biz_project_violation的主键
	18: i32 userId;//当前用户
}




