package com.chubaievskyi.database;

import com.chubaievskyi.exceptions.DBExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TableGeneratorTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    private TableGenerator tableGenerator;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        tableGenerator = new TableGenerator();
    }

    @Test
    void testCreateTables() throws Exception {
        String pathToSqlFile = "testTableGenerator.sql";
        tableGenerator.createTables(pathToSqlFile, connection);
        verify(preparedStatement, times(14)).execute();
    }

    @Test
    void testSQLExceptionIsThrown() throws Exception {
        String pathToSqlFile = "testTableGenerator.sql";
        doThrow(new SQLException()).when(preparedStatement).execute();

        assertThrows(DBExecutionException.class, () -> {
            tableGenerator.createTables(pathToSqlFile, connection);
        });
    }
}