package com.senmash.order_service.service;

import com.senmash.inventory_service.dto.InventoryDTO;
import com.senmash.order_service.common.ErrorOrderResponse;
import com.senmash.order_service.common.OrderResponse;
import com.senmash.order_service.common.SuccessOrderResponse;
import com.senmash.order_service.dto.OrderDto;
import com.senmash.order_service.model.Orders;
import com.senmash.order_service.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final WebClient webClient;

   @Autowired
   private OrderRepository orderRepository;


   @Autowired
   private ModelMapper modelMapper;

    public OrderService(WebClient webClient, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8082/api/v1/").build();
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public OrderResponse makeOrder(OrderDto orderDto){

         int itemID=orderDto.getProductID();

         try {
           InventoryDTO inventoryResponse=  webClient.get()
                     .uri(uriBuilder -> uriBuilder.path("item/{itemId}").build(itemID))
                     .retrieve()
                     .bodyToMono(InventoryDTO.class)
                     .block();

             assert inventoryResponse != null;
             if (inventoryResponse.getQuantity()>0){
               orderRepository.save(modelMapper.map(orderDto, Orders.class));
               return new SuccessOrderResponse(orderDto);
           }
             else {
                 return new ErrorOrderResponse("Item not available please try again later");
             }
         }
         catch (Exception e){
             e.printStackTrace();
         }

        return null;

    }

    public List<OrderDto> getAllOrders() {
        List<Orders> orderList= orderRepository.findAll();
        return  modelMapper.map(orderList, new TypeToken<List<OrderDto>>(){}.getType());
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        Orders orders=orderRepository.save(modelMapper.map(orderDto, Orders.class));
        return modelMapper.map(orders, OrderDto.class);
    }


    public String deleteOrderBYID(int id) {
        orderRepository.deleteById(id);
        return "order has been deleted successfully";
    }

    public OrderDto getOrderByID(int id) {
        Optional<Orders> order=orderRepository.findById(id);
        return modelMapper.map(order, OrderDto.class);
    }
}
