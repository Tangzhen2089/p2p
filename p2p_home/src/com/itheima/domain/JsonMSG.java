package com.itheima.domain;

public class JsonMSG {

	private int status;//0ʧ��  1�ɹ�
	private String msg;
	private Object content; //���ڷ�װ����
	
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}