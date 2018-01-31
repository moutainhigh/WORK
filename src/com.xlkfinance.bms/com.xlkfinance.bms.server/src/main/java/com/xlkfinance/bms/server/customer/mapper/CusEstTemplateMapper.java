package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusEstTemplate;

@MapperScan("cusEstTemplateMapper")
@SuppressWarnings("rawtypes")
public interface CusEstTemplateMapper<T, PK> extends BaseMapper<T, PK> {
	public List<GridViewDTO> getCusEstTemplates(CusEstTemplate cusEstTemplate);
	
	
	public CusEstTemplate selectModelName(int pid);
	
	public int deleteEstTemplates(List list);
	
	public CusEstTemplate getMakeEstTemplate(int pid);
	
	public List<CusEstTemplate> selectAllEstTemplateName(CusEstTemplate cet);
}
