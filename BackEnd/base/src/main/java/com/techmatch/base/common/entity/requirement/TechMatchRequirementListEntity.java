package com.techmatch.base.common.entity.requirement;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
public class TechMatchRequirementListEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requirement_id_generator")
	@SequenceGenerator(name = "requirement_id_generator", sequenceName = "tm_requirement_infos_requirement_id_seq", allocationSize = 1)
	@Column(name="requirement_id")
	private int id;
	private String userId;
	private String title;
	private String requirementStatusCd;
	private Date expireDate;

}
