package com.mobest1an.LAB4_WEB.resvice;

import com.mobest1an.LAB4_WEB.model.Check;
import com.mobest1an.LAB4_WEB.model.User;

import java.util.List;

public interface CheckService {

    void addCheck(User user, Check check);

    List<Check> getUserChecks(User user);
}
