package com.techmatch.base.common.entity.requirement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="tm_requirement_statuses")
public class TechMatchRequirementStatusEntity {
	@Id
	 private String requirementStatusCd;
	 private String name;
}
