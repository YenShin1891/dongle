package com.example.ecomap4

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.sql.ResultSet

// assume that conn is an already created JDBC connection (see previous examples)



/*
fun main() {
    MySQLconnection
    val test = Mysqlfuctions()
    test.showDatabase(conn)
}*/


class Mysqlfuctions() {

    var stmt: Statement? = null
    var rs: ResultSet? = null


    fun showDatabase(conn: Connection?){
        try {
            println("#In show database#")
            println(conn == null)
            stmt = conn!!.createStatement()
            rs = stmt!!.executeQuery("SHOW DATABASES;")

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt!!.execute("SHOW DATABASES;")) {
                rs = stmt!!.resultSet
            }
            while (rs!!.next()) {
                println(rs!!.getString("Database"))
            }

        }


        catch (ex : SQLException)
        {
            // handle any errors
            println("SQLException: " + ex.message)
            println("SQLState: " + ex.getSQLState())
            println("VendorError: " + ex.getErrorCode())
        }
        finally
        {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs!!.close()
                } catch (sqlEx: SQLException) {
                } // ignore

                rs = null
            }

            if (stmt != null) {
                try {
                    stmt!!.close()
                } catch (sqlEx: SQLException) {
                } // ignore

                stmt = null
            }

//            if (conn != null) {
//                try{
//                    conn!!.close()
//                } catch (sqlEx: SQLException){
//                }
//
//                conn = null
//            }
        }
    }
}
