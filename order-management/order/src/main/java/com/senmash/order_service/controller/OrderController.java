package com.senmash.order_service.controller;

import com.base.base.dto.OrderEventDTO;
import com.senmash.order_service.common.OrderResponse;
import com.senmash.order_service.dto.OrderDto;
import com.senmash.order_service.kafka.OrderProducer;
import com.senmash.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

   @Autowired
   private OrderService orderService;

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/add")
    public OrderResponse makeOrder(OrderDto orderDto){

        OrderEventDTO orderEventDTO = new OrderEventDTO();
        orderEventDTO.setMessage("Order is committed");
        orderEventDTO.setStatus("pending");
        orderProducer.sendMessage(orderEventDTO);

        return  orderService.makeOrder(orderDto);
    }


    @GetMapping("/get")
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PutMapping("/update")
    public OrderDto updateOrder(@RequestBody OrderDto orderDto){

        return orderService.updateOrder(orderDto);

    }


    @DeleteMapping("/delete")
    public String deleteOrderBYID(int id){
        return orderService.deleteOrderBYID(id);
    }

    @GetMapping("/getByID")
    public OrderDto getBYID(int id){
        return orderService.getOrderByID(id);
    }

}
