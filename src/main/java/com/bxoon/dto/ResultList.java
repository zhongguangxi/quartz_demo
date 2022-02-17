package com.bxoon.dto;

import java.util.List;

public class ResultList<T> extends Result {

	private List<T> list;
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public static <T> ResultList<T> success(List<T> list){
		ResultList<T> json = new ResultList<T>();
		json.setCode(0);
		json.setMsg("操作成功");
		json.setList(list);
		return json;
	}
	
	public static <T> ResultList<T> error(){
		return error(1, "操作失败");
	}
	
	public static <T> ResultList<T> error(String msg){
		return error(500, "操作失败");
	}
	
	public static <T> ResultList<T> error(int code, String msg){
		ResultList<T> json = new ResultList<T>();
		json.setCode(1);
		json.setMsg(msg);
		return json;
	}
}
