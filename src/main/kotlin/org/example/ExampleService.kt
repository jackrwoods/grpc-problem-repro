package org.example

import io.grpc.BindableService
import io.grpc.stub.StreamObserver
import org.example.ExampleServiceGrpc.ExampleServiceImplBase
import org.example.Payload

class ExampleService: ExampleServiceImplBase() {
    override fun nothingDoer(request: Payload, responseObserver: StreamObserver<Payload>) {
        println(request.message)
        responseObserver.onNext(request)
    }
}