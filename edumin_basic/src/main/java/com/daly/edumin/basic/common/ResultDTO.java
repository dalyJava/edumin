package com.daly.edumin.basic.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import org.springframework.validation.FieldError;


public @Data class ResultDTO implements Serializable {
	private int resCode;//状态码
	private String resMsg;//返回消息
	private Object resData;//返回数据
	private List<FieldError> resValidData;//验证的错误信息集合
	private Map<String,List<Map<String,String>>> resFilterMap; //返回表格数据的筛选条件 IVIEW使用
	public ResultDTO(){}


	public ResultDTO(int resCode, String resMsg) {
		this.resCode = resCode;
		this.resMsg = resMsg;
	}

	public ResultDTO(int resCode, String resMsg,Object resData) {
		this.resCode = resCode;
		this.resMsg = resMsg;
		this.resData = resData;
	}

	public  void  setInfo(int resCode,String resMsg){
		this.resCode = resCode;
		this.resMsg = resMsg;
	}
	public  void  setInfo(int resCode,Object resData){
		this.resCode = resCode;
		this.resData = resData;
	}
	public  void  setInfo(int resCode,String resMsg,Object resData){
		this.resCode = resCode;
		this.resMsg = resMsg;
		this.resData = resData;
	}

}
