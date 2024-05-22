package com.centralplatform.server.payload.request.Inventory;


import com.centralplatform.server.payload.request.Request;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = InventoryInfoRequest.class, name = "info"),
    @JsonSubTypes.Type(value = InventoryUpdateRequest.class, name = "update"),
    @JsonSubTypes.Type(value = InventoryDeleteRequest.class, name = "delete"),
    @JsonSubTypes.Type(value = InventoryCreateRequest.class, name = "create")
})
public abstract class InventoryRequest extends Request {
}
