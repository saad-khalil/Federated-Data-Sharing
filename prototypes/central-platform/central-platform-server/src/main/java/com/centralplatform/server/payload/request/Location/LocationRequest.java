package com.centralplatform.server.payload.request.Location;


import com.centralplatform.server.payload.request.Request;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LocationInfoRequest.class, name = "info"),
    @JsonSubTypes.Type(value = LocationUpdateRequest.class, name = "update"),
    @JsonSubTypes.Type(value = LocationDeleteRequest.class, name = "delete"),
    @JsonSubTypes.Type(value = LocationCreateRequest.class, name = "create")
})
public abstract class LocationRequest extends Request {
}
