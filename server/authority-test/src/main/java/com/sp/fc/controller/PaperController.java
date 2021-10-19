package com.sp.fc.controller;

import com.sp.fc.service.Paper;
import com.sp.fc.service.PaperService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/paper")
@RestController
public class PaperController {
    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

//    @PreAuthorize("isStudent()")
    @GetMapping("/mypapers")
    public List<Paper> myPapers(@AuthenticationPrincipal User user) {
        return paperService.getMyPapers(user.getUsername());
    }

//    @PreAuthorize("hasPermission(#paperId, 'paper', 'read')")
    @PostAuthorize("returnObject.studentIds.contains(#user.username)")
    @GetMapping("/get/{paperId}")
    public Paper getPaper(@AuthenticationPrincipal User user, @PathVariable Long paperId){
        return paperService.getPaper(paperId);
    }
}
