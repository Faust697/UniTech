package com.unitech.UniTech.service;



import com.unitech.UniTech.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


public class CustomUserDetails implements UserDetails {

    private final Optional<UserEntity> userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = Optional.ofNullable(userEntity);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return userEntity.map(UserEntity::getPassword).orElse(null);
    }

    @Override
    public String getUsername() {
        return userEntity.map(UserEntity::getUsername).orElse(null);
    }
    

   // public String getPin() {
 //       return userEntity.map(UserEntity::getPincode).orElse(null);
  //  }

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

 

}

