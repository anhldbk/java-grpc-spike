package net.dehora.grpc.spike.service;

import io.grpc.stub.StreamObserver;
import net.dehora.grpc.spike.helloworld.GreeterGrpc;
import net.dehora.grpc.spike.helloworld.HelloRequest;
import net.dehora.grpc.spike.helloworld.HelloResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcGreeterService implements GreeterGrpc.Greeter {

    private static final Logger logger = LoggerFactory.getLogger(GrpcGreeterService.class);

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> observer) {
        observer.onValue(response(hello(request.getName())));
        observer.onCompleted();
    }

    private HelloResponse response(String message) {
        return HelloResponse.newBuilder().setMessage(message).buildPartial();
    }

    private String hello(String name) {
        return String.format("Hello, %s!", name);
    }
}
