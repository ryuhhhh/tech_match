package com.techmatch.base.common.entity.application;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Table(name="tm_bookmarks")
@Data
@Entity
public class TechMatchBookmarksEntity {
    @EmbeddedId
    private TechMatchBookmarksKey bookmarksKey;
	
    public TechMatchBookmarksEntity() {
    	
    }
    
    public TechMatchBookmarksEntity(TechMatchBookmarksKey bookmarksKey) {
    	this.bookmarksKey = bookmarksKey;
    }
}
