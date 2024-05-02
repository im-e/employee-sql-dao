package com.thejaavmahal.utils;

import com.mysql.cj.jdbc.ConnectionImpl;
import com.thejaavmahal.logging.LogHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionManagerTests {

    private static final Logger LOGGER = LogHandler.getLogger();

    @Test
    @DisplayName("If get connection is called, returns not null")
    void ifGetConnectionIsCalledReturnsNotNull() {
        Connection connection = ConnectionManager.getConnection();
        Assertions.assertNotNull(connection);
    }

    @Test
    @DisplayName("If get connection is called, class of object is connection")
    void ifGetConnectionIsCalledClassOfObjectIsConnection() {
        Connection connection = ConnectionManager.getConnection();
        Assertions.assertSame(connection.getClass(), ConnectionImpl.class);

    }

    @Test
    @DisplayName("If closed connection is called, connection gets closed")
    void ifClosedConnectionIsCalledConnectionGetsClosed(){
        Connection connection = ConnectionManager.getConnection();
        ConnectionManager.closeConnection();
        try{
            Assertions.assertTrue(connection.isClosed());
        } catch (SQLException e){
            LOGGER.severe("Error when closing the connection: " + e.getMessage());
        }
    }
}
