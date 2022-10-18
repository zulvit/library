package ru.zulvit.database;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class DatabaseCustomException extends SQLException {

    public DatabaseCustomException(@NotNull String reason, @NotNull String sqlState, int vendorCode) {
        super(reason, sqlState, vendorCode);
        System.err.println("Reason: " + reason);
        System.err.println("SQLState: " + sqlState);
        System.err.println("VendorCode: " + vendorCode);
    }
}
