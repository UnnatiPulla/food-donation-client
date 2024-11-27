package com.coms4156.client.model;

public class FoodRequest {
    private int requestId;
    private int quantityRequested;
    private String requestTime;

    public int getRequestId() {
        return requestId;
    }
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    public int getQuantityRequested() {
        return quantityRequested;
    }
    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }
    public String getRequestTime() {
        return requestTime;
    }
    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
}
