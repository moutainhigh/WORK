//Jony  2017/7/1
//pojo公共模块
namespace java com.xlkfinance.bms.rpc.workday

//法定假日表
struct WorkDay{
	1: i32 pid;    //主键
	2: i32 correctYear;  //年份
	3: string correctDate;   //日期
	4: i32 isHolidays;  //是否为节假日 （1=是；2=否）
	5: string remark;//备注
	6: i32 createId;   //创建人
	7: string createDate; //  创建日期
	8: i32 updateId;   
	9: string updateDate;   
	10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
	12: string fromCorrectDate; //  仅仅做查询
	13: string toCorrectDate; //   仅仅做查询
}
service WorkDayService{

	//新增法定节假日表
	i32 insert(1:WorkDay workDay);
	
	//修改法定节假日表
	i32 update(1:WorkDay workDay);
	
	//删除法定节假日表
	i32 deleteById(1:i32 pid);
	
	//根据条件查询法定假日列表(分页查询)
	list<WorkDay> getWorkDay (1:WorkDay workDay);
	
    //根据条件查询法定假日列
	i32 geWorkDayTotal (1:WorkDay workDay);
}
