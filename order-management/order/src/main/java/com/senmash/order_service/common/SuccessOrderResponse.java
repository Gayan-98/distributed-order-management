package com.senmash.order_service.common;

import com.senmash.order_service.dto.OrderDto;
import lombok.Getter;


@Getter
public class SuccessOrderResponse implements OrderResponse{
    private final OrderDto order;

    public SuccessOrderResponse(OrderDto order) {
        this.order=order;
    }
}
