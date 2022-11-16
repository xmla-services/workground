package org.eclipse.daanse.db.dialect.db.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

// TODO remove this class to common
    /**
     * Encloses a value in single-quotes, to make a SQL string value. Examples:
     * <code>singleQuoteForSql(null)</code> yields <code>NULL</code>;
     * <code>singleQuoteForSql("don't")</code> yields <code>'don''t'</code>.
     */
    public static String singleQuoteString(String val) {
        StringBuilder buf = new StringBuilder(64);
        singleQuoteString(val, buf);
        return buf.toString();
    }

    /**
     * Encloses a value in single-quotes, to make a SQL string value. Examples:
     * <code>singleQuoteForSql(null)</code> yields <code>NULL</code>;
     * <code>singleQuoteForSql("don't")</code> yields <code>'don''t'</code>.
     */
    public static void singleQuoteString(String val, StringBuilder buf) {
        buf.append('\'');

        String s0 = val.replaceAll("'", "''");
        buf.append(s0);

        buf.append('\'');
    }

    /**
     * Closes a JDBC result set, statement, and connection, ignoring any errors.
     * If any of them are null, that's fine.
     *
     * <p>If any of them throws a {@link SQLException}, returns the first
     * such exception, but always executes all closes.</p>
     *
     * @param resultSet Result set
     * @param statement Statement
     * @param connection Connection
     */
    public static SQLException close(
        ResultSet resultSet,
        Statement statement,
        Connection connection)
    {
        SQLException firstException = null;
        if (resultSet != null) {
            try {
                if (statement == null) {
                    statement = resultSet.getStatement();
                }
                resultSet.close();
            } catch (Throwable t) {
                firstException = new SQLException();
                firstException.initCause(t);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Throwable t) {
                if (firstException == null) {
                    firstException = new SQLException();
                    firstException.initCause(t);
                }
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Throwable t) {
                if (firstException == null) {
                    firstException = new SQLException();
                    firstException.initCause(t);
                }
            }
        }
        return firstException;
    }

}
