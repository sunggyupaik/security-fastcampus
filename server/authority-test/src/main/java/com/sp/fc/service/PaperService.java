package com.sp.fc.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperService implements InitializingBean {
    private HashMap<Long, Paper> paperDB = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void setPaper(Paper paper){
        paperDB.put(paper.getPaperId(), paper);
    }

    @PostFilter("notPrepareState(filterObject)")
    public List<Paper> getMyPapers(String username) {
//        return new ArrayList<>(paperDB.values());
        return paperDB.values().stream().filter(
                paper -> paper.getStudentIds().contains(username)
        ).collect(Collectors.toList());
    }

    public List<Paper> getAllPapers() {
        return new ArrayList<>(paperDB.values());
    }

    //@PostAuthorize("returnObject.studentIds.contains(principal.username)")
    public Paper getPaper(Long paperId) {
        return paperDB.get(paperId);
    }
}

