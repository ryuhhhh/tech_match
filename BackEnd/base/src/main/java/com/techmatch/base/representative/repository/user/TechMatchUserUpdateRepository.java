package com.techmatch.base.representative.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techmatch.base.common.entity.user.TechMatchUserEntityForUpdate;

public interface TechMatchUserUpdateRepository  extends JpaRepository<TechMatchUserEntityForUpdate, String>{

}
