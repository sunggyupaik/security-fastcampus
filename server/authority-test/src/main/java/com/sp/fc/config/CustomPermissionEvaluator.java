package com.sp.fc.config;

import com.sp.fc.service.Paper;
import com.sp.fc.service.PaperService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Lazy
    private PaperService paperService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {
        Paper paper = paperService.getPaper((long) targetId);
        if(paper == null) throw new AccessDeniedException("시험지가 존재하지 않음");

        if(paper.getState() == Paper.State.PREPARE) throw new AccessDeniedException("준비중에는 접근 금지");

        return paper.getStudentIds().stream().anyMatch(userId -> userId.equals(authentication.getName()));
    }
}
