package com.sp.fc.user.service;

import com.sp.fc.user.domain.SpAuthority;
import com.sp.fc.user.domain.SpOauth2User;
import com.sp.fc.user.domain.SpUser;
import com.sp.fc.user.repository.SpOauth2UserRepository;
import com.sp.fc.user.repository.SpUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SpUserService implements UserDetailsService {
    private final SpUserRepository spUserRepository;
    private final SpOauth2UserRepository spOauth2UserRepository;

    public SpUserService(SpUserRepository spUserRepository,
                         SpOauth2UserRepository spOauth2UserRepository) {
        this.spUserRepository = spUserRepository;
        this.spOauth2UserRepository = spOauth2UserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return spUserRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<SpUser> findUser(String email) {
        return spUserRepository.findUserByEmail(email);
    }

    public SpUser save(SpUser spUser) {
        return spUserRepository.save(spUser);
    }

    public void addAuthority(Long userId, String authority) {
        spUserRepository.findById(userId).ifPresent(user -> {
            SpAuthority newRole = new SpAuthority(user.getUserId(), authority);
            if(user.getAuthorities() == null) {
                Set<SpAuthority> authorities = new HashSet<>();
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            } else if(!user.getAuthorities().contains(newRole)) {
                HashSet<SpAuthority> authorities = new HashSet<>();
                authorities.addAll(user.getAuthorities());
                authorities.add(newRole);
                user.setAuthorities(authorities);
                save(user);
            }
        });
    }

    public void removeAuthority(Long userId, String authority){
        spUserRepository.findById(userId).ifPresent(user->{
            if(user.getAuthorities() == null) return;
            SpAuthority targetRole = new SpAuthority(user.getUserId(), authority);
            if(user.getAuthorities().contains(targetRole)){
                user.setAuthorities(
                        user.getAuthorities().stream().filter(auth->!auth.equals(targetRole))
                                .collect(Collectors.toSet())
                );
                save(user);
            }
        });
    }

    public SpUser loadUser(final SpOauth2User oauth2User){
        SpOauth2User user = spOauth2UserRepository.findById(oauth2User.getOauth2UserId()).orElseGet(()->{
            SpUser spUser = new SpUser();
            spUser.setEmail(oauth2User.getEmail());
            spUser.setEnabled(true);
            spUser.setPassword("");
            spUserRepository.save(spUser);
            addAuthority(spUser.getUserId(), "ROLE_USER");
            oauth2User.setUserId(spUser.getUserId());
            oauth2User.setCreated(LocalDateTime.now());
            return spOauth2UserRepository.save(oauth2User);
        });
        return spUserRepository.findById(user.getUserId()).get();
    }
}
