package com.techmatch.base.common.entity.requirement;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Table(name="tm_requirement_infos")
@Entity
public class TechMatchRequirementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "requirement_id_generator")
	@SequenceGenerator(name = "requirement_id_generator", sequenceName = "tm_requirement_infos_requirement_id_seq", allocationSize = 1)
	@Column(name="requirement_id")
	private int id;
	//	private int id;
	private String userId;
	private String title;
	private String whyText;
	private String whatText;
	private String howText;
	private String image_1;
	private String image_2;
	private Integer requirementNum;
	private Date targetDate;
	private String requirementStatusCd;
	private Date expireDate;
	@CreationTimestamp
	private Timestamp createDate;
	@UpdateTimestamp
	private Timestamp updateDate;

	@OneToMany
	@JoinColumn(name="requirement_id",insertable = false, updatable = false)
	List<TechMatchRequirementSkillEntityToFullTextSearch> RequirementSkillEntityList;
}
