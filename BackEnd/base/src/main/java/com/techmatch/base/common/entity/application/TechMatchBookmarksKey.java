package com.techmatch.base.common.entity.application;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class TechMatchBookmarksKey implements Serializable{
	private static final long serialVersionUID = 1L;
    @Basic(optional = false)
	@Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
	@Column(name = "requirement_id")
    private int id;

    public  TechMatchBookmarksKey() {

    }

    public TechMatchBookmarksKey(int id,String userId) {
		this.id = id;
		this.userId = userId;
	}
}
