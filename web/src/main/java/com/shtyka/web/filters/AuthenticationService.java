package com.shtyka.web.filters;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
//
//	@Autowired
//	private UserService userService;
//
//	@Transactional
//	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//		User user = null;
//		try {
//			user = userService.getUserByLogin(login);
//			if (user == null) {
//				throw new UsernameNotFoundException("Login " + login + " doesn't exist!");
//			}
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//		return new org.springframework.security.core.userdetails.User
//				(user.getLogin(), user.getPassword(), true, true, true, true, getGrantedAuthorities(user));
//	}
//
//	private List<GrantedAuthority> getGrantedAuthorities(User user) {
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().toUpperCase().toString()));
//		return authorities;
//	}
}