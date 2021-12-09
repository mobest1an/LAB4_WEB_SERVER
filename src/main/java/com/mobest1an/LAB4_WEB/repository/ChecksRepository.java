package com.mobest1an.LAB4_WEB.repository;

import com.mobest1an.LAB4_WEB.model.Check;
import com.mobest1an.LAB4_WEB.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChecksRepository extends CrudRepository<Check, Long> {

    List<Check> findAllByUser(User user);
}
