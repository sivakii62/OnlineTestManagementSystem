package com.cg.otm.security;

import java.util.ArrayList;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.otm.repository.UsersRepository;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UsersRepository userRepository;

	@Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		com.cg.otm.entities.User user;
		try {
			user = userRepository.findByName(s).get(0);		
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("Username or Password Doesnot Exist");
		}
		return new User(user.getUserName(), user.getUserPassword(),new ArrayList<>());
    }
}
