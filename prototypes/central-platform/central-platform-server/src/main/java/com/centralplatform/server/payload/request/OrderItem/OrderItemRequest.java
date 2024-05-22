package com.centralplatform.server.payload.request.OrderItem;


import com.centralplatform.server.payload.request.Request;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = OrderItemInfoRequest.class, name = "info"),
    @JsonSubTypes.Type(value = OrderItemUpdateRequest.class, name = "update"),
    @JsonSubTypes.Type(value = OrderItemDeleteRequest.class, name = "delete"),
    @JsonSubTypes.Type(value = OrderItemCreateRequest.class, name = "create")
})
public abstract class OrderItemRequest extends Request {
}
