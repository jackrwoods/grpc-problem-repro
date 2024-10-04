package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Main {
    public static void main(String[] args) {
        ExampleService service = new ExampleService();
        ExampleServiceServer server = new ExampleServiceServer(service);
        server.start();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8123)
                .usePlaintext()
                .build();
        ExampleServiceGrpc.ExampleServiceBlockingStub stub = ExampleServiceGrpc.newBlockingStub(channel)
                .withMaxOutboundMessageSize(0); // Forces everything to exceed this limit

        System.out.println("Sending message");
        Payload response = stub.nothingDoer(Payload.newBuilder().setMessage("hello world").build());
        System.out.println("Received message");
        System.out.println(response.getMessage());

        server.stop();
        System.out.println("Exiting application");
    }
}