package com.lianglianglee.vertx

import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val vertx = Vertx.vertx()
            val httpServer = vertx.createHttpServer()
            val router = Router.router(vertx)
            router.get("/").handler { routingContext ->
                val response = routingContext.response()
                response.putHeader("content-type", "text/plain")
                        .setChunked(true)
                        .write("Hi Lianglianglee")
                        .end("Ended")
            }
            router.get("/json/:name").handler { routingContext ->
                val request = routingContext.request()
                var name=request.getParam("name")
                val response = routingContext.response()
                response.putHeader("content-type", "application/json")
                        .setChunked(true)
                        .write(Json.encodePrettily(ResponseObj(name)))
                        .end()
            }
            router.get("/json").handler { routingContext ->
                val response = routingContext.response()
                response.putHeader("content-type", "application/json")
                        .setChunked(true)
                        .write(Json.encodePrettily(ResponseObj("Lianglianglee")))
                        .end()
            }
            httpServer.requestHandler(router::accept).listen(8087)

        }
    }

    data class ResponseObj(var name: String = "")
}