package com.techmatch.base.representative.repository.user;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.user.TechMatchUserEntity;

/**
 * 認証用リポジトリです
 * @author firmy
 *
 */
@Repository
public interface TechMatchUserRepository extends JpaRepository<TechMatchUserEntity, String>{
	
	// UserIdが存在しているかチェック
	@Query("SELECT tu FROM TechMatchUserEntity tu WHERE userId = :user_id")
	public List<TechMatchUserEntity> isUserIdAlreadyExisted(@Param("user_id")String userId);
	
	// Facebookアカウントが存在しているかチェック
	@Query("SELECT tu FROM TechMatchUserEntity tu WHERE facebookAccount = :facebook_account")
	public List<TechMatchUserEntity> isFaceBookAccountAlreadyExisted(@Param("facebook_account")String facebookAccount);

	// メールアドレスが存在しているかチェック
	@Query("SELECT tu FROM TechMatchUserEntity tu WHERE mailAddress = :mail_address")
	public List<TechMatchUserEntity> isMailAddressAlreadyExisted(@Param("mail_address")String mailAddress);

	// Twitterアカウントが存在しているかチェック
	@Query("SELECT tu FROM TechMatchUserEntity tu WHERE twitterAccount = :twitter_account")
	public List<TechMatchUserEntity> isTwitterAccountAlreadyExisted(@Param("twitter_account")String twitterAccount);
	
	// ユーザの画像情報を取得
	@Query("SELECT tu.userImage FROM TechMatchUserEntity tu WHERE userId = :user_id")
	public String getUserImage(@Param("user_id")String userId);
	
	// ユーザの画像情報を更新
	@Transactional
	@Modifying
	@Query("UPDATE TechMatchUserEntity tu SET tu.userImage = :userImage WHERE userId = :user_id")
	public void updateUserImage(@Param("userImage")String userImage,@Param("user_id")String userId);
	
}
