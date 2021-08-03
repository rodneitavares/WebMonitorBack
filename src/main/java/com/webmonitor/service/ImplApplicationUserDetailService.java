package com.webmonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webmonitor.model.ApplicationUser;
import com.webmonitor.repository.ApplicationUserRepository;

@Service
public class ImplApplicationUserDetailService implements UserDetailsService {

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ApplicationUser appUsers = applicationUserRepository.findUserByUsername(username);

		if (appUsers == null) {
			throw new UsernameNotFoundException("User not found.");
		}

		return new User(appUsers.getUsername(), appUsers.getPassword(), appUsers.getAuthorities());
	}

}
