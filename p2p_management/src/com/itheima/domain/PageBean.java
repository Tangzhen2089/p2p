package com.itheima.domain;

import java.util.List;

public class PageBean {
	private int pageNo;//ҳ��
	private int pageSize;//ÿҳ����
	private int totalPage;//��ҳ��
	private int totalCount;//������
	private List<Product> content;//ÿҳ����
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<Product> getContent() {
		return content;
	}
	public void setContent(List<Product> content) {
		this.content = content;
	}
	
}
