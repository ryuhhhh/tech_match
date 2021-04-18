package com.techmatch.base.common.entity.user;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;


import lombok.Data;

@Data
@Entity
@Table(name="tmp_tm_users")
public class TechMatchTmpUserEntity {

	@Id
	private String tmpUserId;
	
	private String userId;
	
	private String password;

	private String mailAddress;
	
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date createDate;
	
	@Column(nullable = false, updatable = true)
	@CreationTimestamp
	private Date updateDate;
	

	@PreUpdate
	  protected void onUpdate() {
//	    updateDate = new Timestamp(System.currentTimeMillis());
	    updateDate = new Date(System.currentTimeMillis());
	  }
}
