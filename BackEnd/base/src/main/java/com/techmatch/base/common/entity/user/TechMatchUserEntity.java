package com.techmatch.base.common.entity.user;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.Nullable;

import lombok.Data;

@Data
@Entity
@Table(name="tm_users")
public class TechMatchUserEntity {
	@Id
	private String userId;
	
	private String password;

	private String mailAddress;
	
	@Nullable
	private String twitterAccount;
	
	@Nullable
	private String facebookAccount;
	
	@Nullable
	private String introduce;
	
	@Nullable
	private String userImage;
	
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
