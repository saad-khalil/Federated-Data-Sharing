package com.centralplatform.server.controller.Order;

import com.centralplatform.server.dto.Order.OrderDTO;
import com.centralplatform.server.payload.request.Order.OrderInfoRequest;
import com.centralplatform.server.payload.request.Order.OrderRequest;
import com.centralplatform.server.service.Order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prod")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController<OrderRequest> {
    private final OrderServiceImpl orderService;

    @Override
    public <T extends OrderRequest> ResponseEntity<? extends List<OrderDTO>> getOrders(T request) {
        return ResponseEntity.ok(orderService.getOrders(request));
    }

    @Override
    public ResponseEntity<? extends OrderDTO> getOrderById(String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Override
    public <T extends OrderRequest> ResponseEntity<? extends OrderDTO> updateOrder(T request, String id) {
        return ResponseEntity.ok(orderService.updateOrder(request,id));
    }

    @Override
    public <T extends OrderRequest> ResponseEntity<? extends OrderDTO> uploadOrder(T request) {
        return ResponseEntity.ok(orderService.uploadOrder(request));
    }

    @Override
    public <T extends OrderRequest> ResponseEntity<? extends List<OrderDTO>> uploadOrders(T request) {
        return ResponseEntity.ok(orderService.uploadOrders(request));
    }

    @Override
    public ResponseEntity<?> deleteOrderById(String id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Success");
    }

    @Override
    public <T extends OrderRequest> ResponseEntity<?> deleteOrders(T request) {
        orderService.deleteOrders(request);
        return ResponseEntity.ok("Success");
    }
}
