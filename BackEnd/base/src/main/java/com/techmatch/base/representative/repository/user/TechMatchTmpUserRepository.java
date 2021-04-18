package com.techmatch.base.representative.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.user.TechMatchTmpUserEntity;

@Repository
public interface TechMatchTmpUserRepository  extends JpaRepository<TechMatchTmpUserEntity, String>{
	// UserIdが存在しているかチェック
	@Query("SELECT tu FROM TechMatchTmpUserEntity tu WHERE userId = :user_id")
	public List<TechMatchTmpUserEntity> isUserIdAlreadyExisted(@Param("user_id")String userId);

	// メールアドレスが存在しているかチェック
	@Query("SELECT tu FROM TechMatchTmpUserEntity tu WHERE mailAddress = :mail_address")
	public List<TechMatchTmpUserEntity> isMailAddressAlreadyExisted(@Param("mail_address")String mailAddress);
}
