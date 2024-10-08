package org.example

import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking

object MainKotlinCoroutineVersion {
    @JvmStatic
    fun main(args: Array<String>) {
        val service = ExampleService()
        val server = ExampleServiceServer(service)
        server.start()

        val channel = ManagedChannelBuilder.forAddress("localhost", 8123)
            .usePlaintext()
            .build()
        val stub = ExampleServiceGrpcKt.ExampleServiceCoroutineStub(channel)
            .withMaxOutboundMessageSize(0) // Forces everything to exceed this limit

        println("Sending message")
        val response = runBlocking {
            stub.nothingDoer(Payload.newBuilder().setMessage("hello world").build())
        }
        println("Received message")
        println(response.message)

        server.stop()
        println("Exiting application")
    }
}