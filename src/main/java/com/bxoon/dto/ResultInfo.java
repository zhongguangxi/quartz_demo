package com.bxoon.dto;

public class ResultInfo<T> extends Result {

	private T info;
	
	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}
	
	public static <T> ResultInfo<T> success(T info){
		ResultInfo<T> json = new ResultInfo<T>();
		json.setCode(0);
		json.setMsg("操作成功");
		json.setInfo(info);
		return json;
	}
	
	public static <T> ResultInfo<T> error(){
		return error("操作失败");
	}
	
	public static <T> ResultInfo<T> error(String msg){
		ResultInfo<T> json = new ResultInfo<T>();
		json.setCode(1);
		json.setMsg(msg);
		return json;
	}
}
