package ru.bmstu.dto;

public class UpdateRequest {
    private int amount;

    public UpdateRequest() {
    }

    public UpdateRequest(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
