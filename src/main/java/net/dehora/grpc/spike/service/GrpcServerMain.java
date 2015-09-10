package net.dehora.grpc.spike.service;

import com.google.inject.Guice;
import io.grpc.ServerImpl;
import io.grpc.netty.NettyServerBuilder;
import net.dehora.grpc.spike.helloworld.GreeterGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

public class GrpcServerMain {


    private static final Logger logger = LoggerFactory.getLogger(GrpcServerMain.class);

    public static void main(String[] args) throws Exception {
        Guice.createInjector(new GrpcServiceModule())
            .getInstance(GrpcServerMain.class)
            .start();
    }

    private final GrpcGreeterService service;
    private final int port;
    private ServerImpl server;

    @Inject
    public GrpcServerMain(GrpcGreeterService service, @Named("GrpcPort") Integer port) {
        this.service = service;
        this.port = port;
    }

    public void start() throws Exception {
        startServer();
        logger.info("Listening on " + port);
        startShutdownHook();
    }

    public void stop() {
        if (server != null) {
            logger.info("Shutting down " + port);
            server.shutdown();
        }
    }

    private void startServer() throws IOException {
        server = NettyServerBuilder.forPort(port)
            .addService(GreeterGrpc.bindService(service))
            .build()
            .start();
    }

    private void startShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                GrpcServerMain.this.stop();
            }
        });
    }

}
