package com.mobest1an.LAB4_WEB.resvice;

import com.mobest1an.LAB4_WEB.model.Check;
import com.mobest1an.LAB4_WEB.model.User;
import com.mobest1an.LAB4_WEB.repository.ChecksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChecksLinkedToUserService implements CheckService {

    private final ChecksRepository checksRepository;
    private final HitChecker hitChecker;

    public ChecksLinkedToUserService(ChecksRepository checksRepository, HitChecker hitChecker) {
        this.checksRepository = checksRepository;
        this.hitChecker = hitChecker;
    }

    public void addCheck(User user, Check check) {
        check.setUser(user);
        check.setHit(hitChecker.checkHit(check));
        checksRepository.save(check);
    }

    public List<Check> getUserChecks(User user) {
        return checksRepository.findAllByUser(user);
    }
}
