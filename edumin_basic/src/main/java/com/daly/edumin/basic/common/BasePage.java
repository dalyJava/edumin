package com.daly.edumin.basic.common;

import com.github.pagehelper.Page;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;


@Data
@ToString
public class BasePage {
	/**
	 * IVIEW 的 排序参数
	 */
	private Map<String,String> sortParam;
	/**
	 * IVIEW 的 搜索参数
	 */
	private Map<String,String> searchFilter;
	/**
	 * IVIEW 的 搜索参数
	 */
	private Map<String,String> columnFilterMap;
	/**
	 * IVIEW 的 搜索参数
	 */
	private Map<String,String> filterParam;
	/**
	 * 页码，从1开始
	 */
	private int pageNum = 1;
	/**
	 * 页面大小
	 */
	private int pageSize = 10;
	/**
	 * 起始行
	 */
	private int startRow;
	/**
	 * 末行
	 */
	private int endRow;
	/**
	 * 总数
	 */
	private long total;
	/**
	 * 总页数
	 */
	private int pages;
	
	
	public void setPage(Page page) {
		this.setPageNum(page.getPageNum());
		this.setPageSize(page.getPageSize());
		this.setTotal(page.getTotal());
		this.setPages(page.getPages());
		this.setStartRow(page.getStartRow());
		this.setEndRow(page.getEndRow());
	}
}
