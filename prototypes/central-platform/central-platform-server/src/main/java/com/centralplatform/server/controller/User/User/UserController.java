package com.centralplatform.server.controller.User.User;

import com.centralplatform.server.model.UserOrganizationApplication.UserOrganizationApplication;
import com.centralplatform.server.payload.request.UserOrganizationApplication.UserOrganizationApplicationCreateRequest;
import com.centralplatform.server.service.UserOrganizationApplication.UserOrganizationApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
public class UserController {
    private final UserOrganizationApplicationService userOrganizationApplicationService;

    public UserController(UserOrganizationApplicationService userOrganizationApplicationService) {
        this.userOrganizationApplicationService = userOrganizationApplicationService;
    }
    @RequestMapping(value = "/user-assign-request/new", method = RequestMethod.POST)
    public ResponseEntity<UserOrganizationApplication> createUserOrganizationApplication(@RequestBody UserOrganizationApplicationCreateRequest request) {
        return ResponseEntity.ok(userOrganizationApplicationService.uploadUserOrganizationApplication(request));
    }
}
