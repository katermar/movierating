package com.katermar.movierating.database.connection;

import org.testng.annotations.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Created by katermar on 2/3/2018.
 */
public class ConnectionPoolTest {
    private final static int MAX_WAIT_TIME = 10;
    private static final int NUMBER_OF_CONNECTIONS = 17;
    private static ConnectionPool dbConnectionPool;
    private List<Connection> connectionsFromPool = new ArrayList<>();

    @BeforeClass
    public static void initializeConnectionPool() {
        dbConnectionPool = ConnectionPool.getInstance();
    }

    @AfterClass
    public static void destroyConnectionPool() {
        dbConnectionPool.close();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
            connectionsFromPool.add(dbConnectionPool.getConnection());
        }
    }

    @Test
    public void connectToDBTest() {
        for (int i = 0; i < connectionsFromPool.size(); i++) {
            try {
                assertTrue(connectionsFromPool.get(i).isValid(MAX_WAIT_TIME));
            } catch (SQLException e) {
                fail("SQLException in connection from pool");
            }
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        for (int i = 0; i < NUMBER_OF_CONNECTIONS; i++) {
            connectionsFromPool.get(i).close();
        }
    }
}
