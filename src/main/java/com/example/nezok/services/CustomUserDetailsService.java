package com.example.nezok.services;

import com.example.nezok.models.UserModel;
import com.example.nezok.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserModel userModel = userRepo.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));
        return new org.springframework.security.core.userdetails.
                User(userModel.getEmail(), userModel.getPassword(), getAuthorities(userModel));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(UserModel userModel) {
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userModel.getRole());
        return authorities;
    }
}
