package com.techmatch.base.logic.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.techmatch.base.common.entity.requirement.TechMatchRequirementEntity;
import com.techmatch.base.representative.repository.requirement.TechMatchRequirementInfoRepository;

@Service
public class TechMatchRequirementSpecification {
	@Autowired
	TechMatchRequirementInfoRepository techMatchRequirementInfoRepository;

	private Specification<TechMatchRequirementEntity> titleContains(String text){
		return (root, query, cb) -> cb.like(root.get("title"),"%"+text+"%");
	}
	private Specification<TechMatchRequirementEntity> whyContains(String text){
		return (root, query, cb) -> cb.like(root.get("whyText"),"%"+text+"%");
	}
	private Specification<TechMatchRequirementEntity> whatContains(String text){
		return (root, query, cb) -> cb.like(root.get("whatText"),"%"+text+"%");
	}
	private Specification<TechMatchRequirementEntity> howContains(String text){
		return (root, query, cb) -> cb.like(root.get("howText"),"%"+text+"%");
	}

	// 最後にスキルがinner joinし、specificationを返す処理
	private Specification<TechMatchRequirementEntity> skillContains(String skillCd,String skillLevelCd){
		return (root, query, cb) -> {
			if(Objects.isNull(skillLevelCd) || skillLevelCd.length()==0) {
				return cb.equal(root.join("RequirementSkillEntityList",JoinType.INNER).get("skillCd") , skillCd);
			}
			Join<TechMatchRequirementEntity,TechMatchRequirementEntity> join= root.join("RequirementSkillEntityList",JoinType.INNER);
			return cb.and(
					cb.equal(join.get("skillCd") , skillCd),
					cb.equal(join.get("skillLevelCd") , skillLevelCd)
			);
		};
	}

	public Page<TechMatchRequirementEntity> findAll(List<String> keyWords, Map<String,String> selectors,Pageable pageable) {
		final Specification<TechMatchRequirementEntity> zero = Specification.where((Specification<TechMatchRequirementEntity>)null);
		final Specification<TechMatchRequirementEntity> titleSpec = keyWords
				.stream()
				.map(this::titleContains)
				.reduce(zero, Specification<TechMatchRequirementEntity>::or);// zeroを初期値として、orで結合処理を行う

		final Specification<TechMatchRequirementEntity> whySpec = keyWords
				.stream()
				.map(this::whyContains)
				.reduce(zero, Specification<TechMatchRequirementEntity>::or);

		final Specification<TechMatchRequirementEntity> whatSpec = keyWords
				.stream()
				.map(this::whatContains)
				.reduce(zero, Specification<TechMatchRequirementEntity>::or);

		final Specification<TechMatchRequirementEntity> howSpec = keyWords
				.stream()
				.map(this::howContains)
				.reduce(zero, Specification<TechMatchRequirementEntity>::or);

		final Specification<TechMatchRequirementEntity> selectorsSpec = selectors
				.entrySet()
				.stream()
				.map(x ->skillContains(x.getKey(),x.getValue()))
				.reduce(zero, Specification<TechMatchRequirementEntity>::or);

		Specification<TechMatchRequirementEntity> all = titleSpec.or(whySpec).or(whatSpec).or(howSpec).and(selectorsSpec);
		return techMatchRequirementInfoRepository.findAll(all,pageable);
	}
}
