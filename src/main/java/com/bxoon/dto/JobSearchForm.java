package com.bxoon.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JobSearchForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String jobName;
	
	private String status;
	
	private String methodName;
}
