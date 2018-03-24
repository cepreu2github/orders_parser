package com.example.orders_parser.domain;

public class InputObject {
    private Long orderId;
    private Long amount;
    private String currency;
    private String comment;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void check() {
        if (orderId == null || amount == null || currency == null || comment == null){
            throw new NullPointerException();
        }
    }
}
