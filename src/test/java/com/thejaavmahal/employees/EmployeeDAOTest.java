package com.thejaavmahal.employees;

import com.mysql.cj.jdbc.ConnectionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EmployeeDAOTest {

    @Test
    @DisplayName("Checking that employee with corresponding ID is deleted")
    void checkEmployeeCanBeDeleted(){

        Connection connectionMock = mock(ConnectionImpl.class);
        PreparedStatement selectStatementMock = mock(PreparedStatement.class);
        PreparedStatement deleteStatementMock = mock(PreparedStatement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        try{
        // Mock the behavior of methods in PreparedStatement and ResultSet
        when(connectionMock.prepareStatement(anyString())).thenReturn(selectStatementMock, deleteStatementMock);
        when(selectStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt("employee_id")).thenReturn(1);
        when(resultSetMock.getString("prefix")).thenReturn("Mr");
        when(resultSetMock.getString("first_name")).thenReturn("John");
        // Mock the behavior of executeUpdate method in PreparedStatement

        when(deleteStatementMock.executeUpdate()).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Call the method to be tested

        EmployeeDAO.deleteFromEmployees("employee_id", 1);

        // Verify that the methods were called with the expected parameters
        try {
            verify(connectionMock, times(2)).prepareStatement(anyString());
            verify(selectStatementMock).setInt(1, 1);
            verify(selectStatementMock).executeQuery();
            verify(deleteStatementMock).setInt(1, 1);
            verify(deleteStatementMock).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Now, mock behavior for the scenario when employee is not found
        try {
            when(resultSetMock.next()).thenReturn(false); // Employee not found
            when(selectStatementMock.executeQuery()).thenReturn(resultSetMock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Call the method to be tested again to attempt selecting the deleted employee
        EmployeeDAO.queryFromField("employee_id", 1);

        // Verify that the method was called with the expected parameters and no employee was found
        try {
            verify(selectStatementMock).setInt(1, 1);
            verify(selectStatementMock, times(2)).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
