package com.centralplatform.server.payload.request.Organization;

import com.centralplatform.server.payload.request.Request;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = OrganizationInfoRequest.class, name = "info"),
    @JsonSubTypes.Type(value = OrganizationUpdateRequest.class, name = "update"),
    @JsonSubTypes.Type(value = OrganizationDeleteRequest.class, name = "delete"),
    @JsonSubTypes.Type(value = OrganizationCreateRequest.class, name = "create")
})
public abstract class OrganizationRequest extends Request {
}
