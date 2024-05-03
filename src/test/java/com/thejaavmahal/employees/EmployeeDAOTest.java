package com.thejaavmahal.employees;

import com.thejaavmahal.logging.LogHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.sql.Connection;
import java.util.logging.Logger;
import static org.mockito.Mockito.*;

public class EmployeeDAOTest {

    @Mock
    private static Connection mockConnection;

    private static final Logger LOGGER = LogHandler.getLogger();
    private static Employee mockEmployee;

    @BeforeAll
    static void setUpBeforeClass() {
        mockEmployee = Mockito.mock(Employee.class);
        EmployeeDAO.createEmployee(mockEmployee);

        mockConnection = mock(Connection.class);
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mock(PreparedStatement.class));
    }


    @Test
    @DisplayName("Test SOME METHOD is called in the mock")
    void testSomeMethodIsCalledInTheMock() {
        Mockito.verify(mockEmployee, Mockito.atLeastOnce()).empId();
        //Mockito.verify(mockEmployee, Mockito.mock())

    }

    @Test
    @DisplayName("Test query doesnt return null result set")
    void checkThatExecuteSelectQueryDoesntReturnNull() {
        Assertions.assertNotNull(EmployeeDAO.executeSelectQuery("employee_id", 198429));
    }
}