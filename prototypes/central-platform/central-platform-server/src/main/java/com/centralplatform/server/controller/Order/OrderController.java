package com.centralplatform.server.controller.Order;

import com.centralplatform.server.dto.Order.OrderDTO;
import com.centralplatform.server.payload.request.Order.OrderInfoRequest;
import com.centralplatform.server.payload.request.Order.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface OrderController<R extends OrderRequest> {
    // POST
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    <T extends R> ResponseEntity<? extends List<OrderDTO>> getOrders(@RequestBody T request);

    @RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
    ResponseEntity<? extends OrderDTO> getOrderById(@PathVariable("id") String id);

    @RequestMapping(value = "/order/update/{id}", method = RequestMethod.POST)
    <T extends R> ResponseEntity<? extends OrderDTO> updateOrder(@RequestBody T request, @PathVariable("id") String id);

    @RequestMapping(value = "/order/new", method = RequestMethod.POST)
    <T extends R> ResponseEntity<? extends OrderDTO> uploadOrder(@RequestBody T request);

    @RequestMapping(value = "/order/update", method = RequestMethod.POST)
    <T extends R> ResponseEntity<? extends List<OrderDTO>> uploadOrders(@RequestBody T request);


    // DELETE
    @RequestMapping(value = "/order/delete/{id}", method = RequestMethod.GET)
    ResponseEntity<?> deleteOrderById(@PathVariable("id") String id);

    @RequestMapping(value = "/order/delete", method = RequestMethod.GET)
    <T extends R> ResponseEntity<?> deleteOrders(@RequestBody T request);
}
