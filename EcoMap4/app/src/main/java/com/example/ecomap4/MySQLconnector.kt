package com.example.ecomap4

//import java.sql.*
import java.util.Properties
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.ResultSet
import java.sql.Statement

/**
 * Program to list databases in MySQL using Kotlin
 */


object MySQLDatabaseExampleKotlin {

    internal var conn: Connection? = null
//    internal var username = "jiheun"
//    internal var password =  "eksdjtlgja20!"

    internal var username = "dongledbadmin"
    internal var password =  "dongledbadmin!@"

    @JvmStatic fun main(args: Array<String>) {
        // make a connection to MySQL Server
        getConnection()
        // execute the query via connection object
        executeMySQLQuery()
    }

    fun executeMySQLQuery() {
        var stmt: Statement? = null
        var rs: ResultSet? = null

        try {
            stmt = conn!!.createStatement()
            rs = stmt!!.executeQuery("SHOW DATABASES;") /*SHOW schema makes error*/


            if (stmt.execute("SHOW DATABASES;")) {
                rs = stmt.resultSet
            }

            while (rs!!.next()) {
                println(rs.getString("Database"))
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } finally {
            // release resources
            if (rs != null) {
                try {
                    rs.close()
                } catch (sqlEx: SQLException) {
                }

                rs = null
            }

            if (stmt != null) {
                try {
                    stmt.close()
                } catch (sqlEx: SQLException) {
                }

                stmt = null
            }

            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                conn = null
            }
        }
    }


/**
     * This method makes a connection to MySQL Server
     */

    fun getConnection() {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
//                        "127.0.0.1" +
                        "tsclouddb.cn3e2kgsuevi.ap-northeast-2.rds.amazonaws.com" +
                        ":" + "3306" + "/" +
                        "",
                connectionProps)

        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
    }
}
