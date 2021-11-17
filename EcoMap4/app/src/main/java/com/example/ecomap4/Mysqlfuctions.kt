package com.example.ecomap4

import android.util.Log
import java.lang.System.out
import java.util.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.ResultSet
import java.sql.Statement

// assume that conn is an already created JDBC connection (see previous examples)




fun main() {
    val sql = Mysqlfunctions() //1

    sql.getConnection()
    sql.showDatabase()

    sql.getConnection() //2
    var locationList = sql.getPinLocation()  //3
    println(locationList)  //4

}


class Mysqlfunctions() {

    var stmt: Statement? = null
    var rs: ResultSet? = null
    var conn: Connection? = null
    val username = "dongledbadmin"
    val password = "dongledbadmin!@"

    //var listofrs = mutableListOf<Triple<String, Double, Double>>()

    fun getConnection() {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
            conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "tsclouddb.cn3e2kgsuevi.ap-northeast-2.rds.amazonaws.com" +
                        ":" + "3306" + "/" +
                        "", // &serverTimezone=UTC
                connectionProps
            )

        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
            //Log.v("connection", "sqlexception")
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
            //Log.v("connection", "geunyang Exception")
        }
        //Log.v("connection", "${conn}")
    }


    fun showDatabase() {

        try {
            println("#In show database#")
            //println(conn == null)
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

        } catch (ex: SQLException) {
            // handle any errors
            println("SQLException: " + ex.message)
            println("SQLState: " + ex.getSQLState())
            println("VendorError: " + ex.getErrorCode())
        } finally {
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


            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                conn = null
            }


        }
    }

    fun getPinLocation(): MutableList<Triple<String, Double, Double>> { //: MutableList<Triple<String, Double, Double>>
        var listofrs = mutableListOf<Triple<String, Double, Double>>()
        try {
            println("#In pin location#")
            println(conn == null)
            //Log.d("connection","${conn == null}")
            stmt = conn!!.createStatement() //error
            rs = stmt!!.executeQuery("SELECT PinID, GPS FROM dongledb.Main;")
            var triple: Triple<String, String, String>

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (stmt!!.execute("SELECT PinID, Lati, Longi FROM dongledb.Main;")) {
                rs = stmt!!.resultSet
            }




            while (rs!!.next()) {
                val pinID = (rs!!.getString("PinID"))
                val latitude = rs!!.getDouble("Lati")
                val longitude = rs!!.getDouble("Longi")

                //val triple = Triple(pinID, latitude, longitude)
                listofrs.add(Triple(pinID, latitude, longitude))
                //print(listofrs)

            }

        } catch (ex: SQLException) {
            // handle any errors
            println("SQLException: " + ex.message)
            println("SQLState: " + ex.getSQLState())
            println("VendorError: " + ex.getErrorCode())
        } finally {
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


            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                conn = null
            }


        }

        //print(listofrs)
        return listofrs

    }
}
