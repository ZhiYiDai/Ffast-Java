package cn.ffast.core.vo;

import java.util.List;

/**
 * @Description: 分页结果
 * @author dzy
 * @vesion 1.0
 */
public class ServiceRowsResult<T> extends ServiceResult {
	private static final long serialVersionUID = 1L;

	public ServiceRowsResult(){}

	public ServiceRowsResult(Boolean success){
		this.success=success;
	}

	public void setPage(List<T> rows, Long total){
		addData("rows",rows);
		addData("total",total);
	}

	public void setPage(List<T> rows){
		addData("rows",rows);
	}



}
