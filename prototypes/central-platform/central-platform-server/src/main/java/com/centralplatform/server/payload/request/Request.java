package com.centralplatform.server.payload.request;

import com.centralplatform.server.model.Role.Role;

import java.util.List;

public abstract class Request {
    private String email;
    private List<Role> roles;
}
