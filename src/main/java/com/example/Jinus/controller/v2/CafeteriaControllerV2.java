package com.example.Jinus.controller.v2;

import com.example.Jinus.service.userInfo.UserService;
import com.example.Jinus.service.v2.cafeteria.CafeteriaServiceV2;
import com.example.Jinus.service.v2.cafeteria.CampusServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CafeteriaControllerV2 {

    private final UserServiceV2 userServiceV2;
    private final CampusServiceV2 campusServiceV2;
    private final CafeteriaServiceV2 cafeteriaServiceV2;

    public CafeteriaControllerV2(
            UserServiceV2 userServiceV2,
            CampusServiceV2 campusServiceV2,
            CafeteriaServiceV2 cafeteriaServiceV2) {
        this.userServiceV2 = userServiceV2;
        this.campusServiceV2 = campusServiceV2;
        this.cafeteriaServiceV2 = cafeteriaServiceV2;
    }


}
