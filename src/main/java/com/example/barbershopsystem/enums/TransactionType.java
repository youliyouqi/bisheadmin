package com.example.barbershopsystem.enums;

public enum TransactionType {
    INCOME("收入", "0"),
    EXPENSE("支出", "1"),
    EXPENSE2("支出", "2"),
    EXPENSE3("支出", "3"),
    UNKNOWN("未知", "unknown");

    private final String description;
    private final String value;

    TransactionType(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public static TransactionType fromValue(String value) {
        for (TransactionType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
