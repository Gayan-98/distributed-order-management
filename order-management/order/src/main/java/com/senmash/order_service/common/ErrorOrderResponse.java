package com.senmash.order_service.common;

import lombok.Getter;

@Getter
public class ErrorOrderResponse implements OrderResponse {
    private final String errorMessage;

    public ErrorOrderResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
