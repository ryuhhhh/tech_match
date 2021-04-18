package com.techmatch.base.config.user;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techmatch.base.common.entity.user.TechMatchUserEntity;
import com.techmatch.base.representative.repository.user.TechMatchUserRepository;

@Service
public class TechMatchUserDetailsService implements UserDetailsService{
	@Autowired
	TechMatchUserRepository techMatchUserRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		TechMatchUserEntity entityUser=null;
		try {
			entityUser = techMatchUserRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException(userId));
		} catch(UsernameNotFoundException e) {
			throw e;
		}
		catch(Exception e) {
			throw new UsernameNotFoundException("ユーザ情報を探す際にエラーが起きました");
		}
		return new User(entityUser.getUserId(),entityUser.getPassword(),new ArrayList<>());
	}
}
