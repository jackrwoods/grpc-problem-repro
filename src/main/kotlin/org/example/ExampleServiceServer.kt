package org.example

import io.grpc.netty.NettyServerBuilder
import io.grpc.Server
import java.net.InetSocketAddress

val hostname = "localhost"
val port = 8123

/** The gRPC interface. This serves as the primary interface for this cache service. */
internal class ExampleServiceServer(private val service: ExampleService) {
    private var server: Server? = null

    fun start(): Int {
        server = NettyServerBuilder
            .forAddress(InetSocketAddress(hostname, port))
            .maxInboundMessageSize(1024)
            .addService(service)
            .build()


        return server!!.start().port
    }

    fun stop() {
        server?.run {
            shutdown()
            awaitTermination()
        }
        server = null
    }

}