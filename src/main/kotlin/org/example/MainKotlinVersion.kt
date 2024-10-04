package org.example

import io.grpc.ManagedChannelBuilder

object MainKotlinVersion {
    @JvmStatic
    fun main(args: Array<String>) {
        val service = ExampleService()
        val server = ExampleServiceServer(service)
        server.start()

        val channel = ManagedChannelBuilder.forAddress("localhost", 8123)
            .usePlaintext()
            .build()
        val stub = ExampleServiceGrpc.newBlockingStub(channel)
            .withMaxOutboundMessageSize(0) // Forces everything to exceed this limit

        println("Sending message")
        val response = stub.nothingDoer(Payload.newBuilder().setMessage("hello world").build())
        println("Received message")
        println(response.message)

        server.stop()
        println("Exiting application")
    }
}