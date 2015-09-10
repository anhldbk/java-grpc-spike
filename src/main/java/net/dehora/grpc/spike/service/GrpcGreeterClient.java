package net.dehora.grpc.spike.service;

import ch.qos.logback.classic.Level;
import com.google.inject.Guice;
import com.google.inject.Inject;
import io.grpc.ChannelImpl;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import net.dehora.grpc.spike.helloworld.GreeterGrpc;
import net.dehora.grpc.spike.helloworld.HelloRequest;
import net.dehora.grpc.spike.helloworld.HelloResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.concurrent.TimeUnit;

public class GrpcGreeterClient {

    private static final Logger logger = LoggerFactory.getLogger(GrpcGreeterClient.class);

    private final ChannelImpl channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public static void main(String[] args) throws Exception {
        setRootLoggerLevel();
        final GrpcGreeterClient client = Guice.createInjector(new GrpcServiceModule())
            .getInstance(GrpcGreeterClient.class);

        try {
            client.greet(name(args));
        } finally {
            client.shutdown();
        }
    }

    private static String name(String[] args) {
        String name = "wash";
        if (args.length > 0) {
            name = args[0];
        }
        return name;
    }

    @Inject
    public GrpcGreeterClient(@Named("GrpcHost") String host, @Named("GrpcPort") int port) {
        channel = NettyChannelBuilder
            .forAddress(host, port)
            .negotiationType(NegotiationType.PLAINTEXT)
            .build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        final String message = call(request(name)).getMessage();
        logger.info("The greeting is: " + message);
    }

    private HelloResponse call(HelloRequest request) {
        return blockingStub.sayHello(request);
    }

    private HelloRequest request(String name) {
        return HelloRequest.newBuilder().setName(name).build();
    }

    private static void setRootLoggerLevel() {
        /*
        grpc outputs a lot of stuff at debug
         */
        final ch.qos.logback.classic.Logger root =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("ROOT");
        root.setLevel(Level.INFO);
    }
}
