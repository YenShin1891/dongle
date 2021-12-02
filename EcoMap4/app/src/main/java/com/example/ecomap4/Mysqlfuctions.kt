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



/*
fun main() {
    val sql = Mysqlfunctions() //1

    sql.getConnection()
    sql.showDatabase()

    sql.getConnection() //2
    var locationList = sql.getPinLocation()  //3
    println(locationList)  //4

}
*/

class Mysqlfunctions() {

    var stmt: Statement? = null
    var rs: ResultSet? = null
    var conn: Connection? = null
    val username = "dongledbadmin"
    val password = "dongledbadmin!@"

    var listofrs = mutableListOf<Triple<String, Double, Double>>()

    fun getConnection() {
        Log.d("connection!", "in get connection")
        val connectionProps = Properties()
        connectionProps.put("user", this.username)
        connectionProps.put("password", this.password)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance()

            /*var driver = DriverManager.getDriver("jdbc:" + "mysql" + "://" +
                    "tsclouddb.cn3e2kgsuevi.ap-northeast-2.rds.amazonaws.com" +
                    ":" + "3306" + "/")
            Log.d("driverconnection", "${driver}")
            Log.d("driverconnection", "${connectionProps}")*/

            this.conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "tsclouddb.cn3e2kgsuevi.ap-northeast-2.rds.amazonaws.com" +
                        ":" + "3306" + "/" +
                        "",
                connectionProps
            )

        } catch (ex: SQLException) {
            // handle any errors
            //ex.printStackTrace()
            Log.d("connection!", "sqlexception")
            Log.d("connection!","SQLException: ${ex.message}")
            Log.d("connection!","SQLState: ${ex.getSQLState()}")
            Log.d("connection!","VendorError: ${ex.getErrorCode()}")
            Log.d("connection!","casue: ${ex.cause}")
            Log.d("connection!","stackTrace: ${ex.stackTrace}")
            Log.d("connection!","nextException: ${ex.nextException}")
            Log.d("connection!","localizedMessage: ${ex.localizedMessage}")
            Log.d("connection!","suppressed: ${ex.suppressed}")
        }
        catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
            Log.d("connection!", "geunyang Exception")
        }

        Log.d("connection!", "in get connection after catch")
        Log.d("connection!", "${this.conn}")
    }


    fun showDatabase() {

        try {
            println("#In show database#")
            //println(conn == null)
            this.stmt = this.conn!!.createStatement()
            this.rs = this.stmt!!.executeQuery("SHOW DATABASES;")

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (this.stmt!!.execute("SHOW DATABASES;")) {
                this.rs = this.stmt!!.resultSet
            }
            while (this.rs!!.next()) {
                println(this.rs!!.getString("Database"))
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

            if (this.rs != null) {
                try {
                    this.rs!!.close()
                } catch (sqlEx: SQLException) {
                } // ignore

                this.rs = null
            }

            if (this.stmt != null) {
                try {
                    this.stmt!!.close()
                } catch (sqlEx: SQLException) {
                } // ignore

                this.stmt = null
            }


            if (this.conn != null) {
                try {
                    this.conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                this.conn = null
            }


        }
    }

    fun getPinLocation(): MutableList<Triple<String, Double, Double>> { //: MutableList<Triple<String, Double, Double>>
        var listofrs = mutableListOf<Triple<String, Double, Double>>()
        try {
            //println("#In pin location#")
            Log.d("connection!getpin", "${this.conn == null}")
            //Log.d("connection","${conn == null}")
            this.stmt = this.conn!!.createStatement() //error
            this.rs = this.stmt!!.executeQuery("SELECT PinID, GPS FROM dongledb.Main;")
            var triple: Triple<String, String, String>

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            if (this.stmt!!.execute("SELECT PinID, Lati, Longi FROM dongledb.Main;")) {
                this.rs = this.stmt!!.resultSet
            }

            while (this.rs!!.next()) {
                val pinID = (this.rs!!.getString("PinID"))
                val latitude = this.rs!!.getDouble("Lati")
                val longitude = this.rs!!.getDouble("Longi")

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

            if (this.rs != null) {
                try {
                    this.rs!!.close()
                } catch (sqlEx: SQLException) {
                } // ignore

                this.rs = null
            }

            if (this.stmt != null) {
                try {
                    this.stmt!!.close()
                } catch (sqlEx: SQLException) {
                } // ignore

                this.stmt = null
            }


            if (this.conn != null) {
                try {
                    this.conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                this.conn = null
            }


        }

        //print(listofrs)
        return listofrs

    }
}
