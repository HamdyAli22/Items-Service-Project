package com.item.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcUtil {

    public static void close(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null) stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn, Statement stmt) {
        close(stmt);
        close(conn);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(conn);
    }
}
