package com.centralplatform.server.payload.request.OrganizationAccess;

import com.centralplatform.server.payload.request.Request;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = OrganizationAccessInfoRequest.class, name = "info"),
    @JsonSubTypes.Type(value = OrganizationAccessDeleteRequest.class, name = "delete"),
    @JsonSubTypes.Type(value = OrganizationAccessCreateRequest.class, name = "create")
})
public abstract class OrganizationAccessRequest extends Request {
}
