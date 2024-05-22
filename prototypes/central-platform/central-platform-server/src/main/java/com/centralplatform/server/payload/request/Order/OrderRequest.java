package com.centralplatform.server.payload.request.Order;


import com.centralplatform.server.payload.request.Request;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = OrderInfoRequest.class, name = "info"),
    @JsonSubTypes.Type(value = OrderUpdateRequest.class, name = "update"),
    @JsonSubTypes.Type(value = OrderDeleteRequest.class, name = "delete"),
    @JsonSubTypes.Type(value = OrderCreateRequest.class, name = "create")
})
public abstract class OrderRequest extends Request {
}
