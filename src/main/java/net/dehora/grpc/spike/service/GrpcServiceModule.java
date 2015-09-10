package net.dehora.grpc.spike.service;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import net.dehora.grpc.spike.helloworld.GreeterGrpc;

public class GrpcServiceModule extends AbstractModule {

    public GrpcServiceModule() {
    }

    @Override
    protected void configure() {
        bind(GrpcServerMain.class).asEagerSingleton();
        bind(GrpcGreeterClient.class).asEagerSingleton();
        bind(GreeterGrpc.Greeter.class).to(GrpcGreeterService.class);
        bind(Integer.class).annotatedWith(Names.named("GrpcPort")).toInstance(50052);
        bind(String.class).annotatedWith(Names.named("GrpcHost")).toInstance("localhost");
    }
}