package com.techmatch.base.representative.repository.applicant;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techmatch.base.common.entity.application.TechMatchBookmarksEntity;
import com.techmatch.base.common.entity.application.TechMatchBookmarksKey;
import com.techmatch.base.common.entity.user.TechMatchUserEntity;
import com.techmatch.base.common.model.application.TechMatchBookmarksModel;
@Repository
public interface TechMatchBookmarksRepository extends JpaRepository<TechMatchBookmarksEntity, TechMatchBookmarksKey> {
	@Query("SELECT bk FROM TechMatchBookmarksEntity bk WHERE bk.bookmarksKey.userId = :user_id")
	public List<TechMatchBookmarksEntity> getBookmarks(@Param("user_id")String userId);

	@Transactional
	@Modifying
	@Query("DELETE FROM TechMatchBookmarksEntity bk WHERE bk.bookmarksKey.userId = :user_id")
	public void deleteUserBookmarks(@Param("user_id")String userId);

}
