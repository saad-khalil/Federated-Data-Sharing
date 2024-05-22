package com.centralplatform.server.payload.request.UserOrganizationApplication;

import com.centralplatform.server.payload.request.Organization.OrganizationUpdateRequest;
import com.centralplatform.server.payload.request.Request;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = UserOrganizationApplicationInfoRequest.class, name = "info"),
    @JsonSubTypes.Type(value = UserOrganizationApplicationDeleteRequest.class, name = "delete"),
    @JsonSubTypes.Type(value = UserOrganizationApplicationUpdateRequest.class, name = "update"),
    @JsonSubTypes.Type(value = UserOrganizationApplicationCreateRequest.class, name = "create")
})
public abstract class UserOrganizationApplicationRequest extends Request {
}
