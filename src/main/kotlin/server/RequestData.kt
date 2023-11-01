package server

data class RequestData(val method: String, val url: String, val body: String)