package ru.bmstu.dtos;

public class UpdateDto {
    private int amount;

    public UpdateDto() {
    }

    public UpdateDto(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
