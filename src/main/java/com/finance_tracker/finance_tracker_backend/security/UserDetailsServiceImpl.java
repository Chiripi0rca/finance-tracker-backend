/*Esta clase implementara la interfaz de UserDetailsService de Spring Security, su unico trabajo
  es decirle a Spring como buscar al usuario en la base de datos cuando alguien intenta autenticarse
*/


package com.finance_tracker.finance_tracker_backend.security;

import com.finance_tracker.finance_tracker_backend.entity.UserEntity;
import com.finance_tracker.finance_tracker_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

     private final UserRepository userRepository;

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         UserEntity user = userRepository.findByEmail(username)
                 .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

         return User.withUsername(user.getEmail())
                 .password(user.getPassword())
                 .build();
     }
}
