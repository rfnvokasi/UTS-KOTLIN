package com.example.utskotlin

class ApiEndPoint {

    companion object {

        private val SERVER = "http://192.168.42.160/utskot/"
        val CREATE = SERVER+"create.php"
        val READ = SERVER+"read.php"
    }

}