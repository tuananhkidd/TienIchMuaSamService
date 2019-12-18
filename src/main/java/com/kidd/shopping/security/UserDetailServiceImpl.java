package com.kidd.shopping.security;



import com.kidd.shopping.auth.entity.User;
import com.kidd.shopping.auth.repository.UserRepository;
import com.kidd.shopping.utils.GrantedAuthorityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                                                                        user.getPassword(),
                                                                        GrantedAuthorityUtils.getGrantedAuthorities(user));
    }
}
