package com.coms4156.client;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class FoodRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int requestId;

  private int quantityRequested;

  private String requestTime;

  @Transient // Prevents JPA from persisting these fields
  private JsonNode client;  // Dynamic JSON for client

  @Transient
  private JsonNode account; // Dynamic JSON for account

  @Transient
  private JsonNode listing; // Dynamic JSON for listing

  // Getters and setters
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

  public JsonNode getClient() {
    return client;
  }

  public void setClient(JsonNode client) {
    this.client = client;
  }

  public JsonNode getAccount() {
    return account;
  }

  public void setAccount(JsonNode account) {
    this.account = account;
  }

  public JsonNode getListing() {
    return listing;
  }

  public void setListing(JsonNode listing) {
    this.listing = listing;
  }
}
