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

object MySQLconnection {

    internal var conn: Connection? = null
    internal val username = "dongledbadmin"
    internal val password =  "dongledbadmin!@"

    @JvmStatic fun main(args: Array<String>) {
        // make a connection to MySQL Server
        getConnection()
        val test = Mysqlfuctions()
        test.showDatabase(conn)
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

