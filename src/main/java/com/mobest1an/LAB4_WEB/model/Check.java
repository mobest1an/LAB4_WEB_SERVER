package com.mobest1an.LAB4_WEB.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "checks")
public class Check {

    @Id
    @GeneratedValue()
    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private boolean isHit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public long getUser() {
        return user.getId();
    }
}
