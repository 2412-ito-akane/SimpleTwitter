package chapter6.beans;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
	private int id;
	private String text; //返信の内容
	private int userId; //ログインしているユーザー
	private int messageId; //返信先のつぶやき
	private Date createdDate; //登録日時
	private Date updatedDate; //更新日時

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//text
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	//userId
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	//messageId
	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	//createdDate
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	//updatedDate
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
