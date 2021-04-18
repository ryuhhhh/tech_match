package com.techmatch.base.common.entity.application;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Table(name="tm_applications")
@Data
@Entity
public class TechMatchApplicationEntity {

    @EmbeddedId
    private TechMatchApplicationKey applicationKey;
    
    private boolean isAdoption;
    
    public TechMatchApplicationEntity() {
    	
    }
    
    public TechMatchApplicationEntity(TechMatchApplicationKey applicationKey) {
    	this.applicationKey = applicationKey;
    }
}
