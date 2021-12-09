package com.mobest1an.LAB4_WEB.resvice;

import com.mobest1an.LAB4_WEB.model.Check;
import org.springframework.stereotype.Component;

@Component
public class HitChecker {

    public boolean checkHit(Check check) {

        double x = check.getX();
        double y = check.getY();
        double r = check.getR();

        if (x >= 0 && y >= 0 && (Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r, 2)) {
            return true;
        }

        if (x <= 0 && y <= 0 && y + x >= (-r / 2)) {
            return true;
        }

        if (x >= 0 && y <= 0 && x <= r / 2 && y >= -r) {
            return true;
        }

        return false;
    }
}
