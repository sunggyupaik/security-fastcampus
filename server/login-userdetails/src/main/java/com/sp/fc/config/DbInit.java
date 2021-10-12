package com.sp.fc.config;

import com.sp.fc.user.domain.SpUser;
import com.sp.fc.user.service.SpUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements InitializingBean {
    private final SpUserService spUserService;

    public DbInit(SpUserService spUserService) {
        this.spUserService = spUserService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(spUserService.findUser("user1").isEmpty()) {
            SpUser spUser = spUserService.save(SpUser.builder()
                    .email("user1")
                    .password("1111")
                    .enabled(true)
                    .build());
            spUserService.addAuthority(spUser.getUserId(),  "ROLE_USER");
        }

        if(spUserService.findUser("user2").isEmpty()) {
            SpUser spUser = spUserService.save(SpUser.builder()
                    .email("user2")
                    .password("1111")
                    .enabled(true)
                    .build());
            spUserService.addAuthority(spUser.getUserId(),  "ROLE_USER");
        }

        if(spUserService.findUser("admin").isEmpty()) {
            SpUser spUser = spUserService.save(SpUser.builder()
                    .email("admin")
                    .password("1111")
                    .enabled(true)
                    .build());
            spUserService.addAuthority(spUser.getUserId(),  "ROLE_ADMIN");
        }
    }
}
