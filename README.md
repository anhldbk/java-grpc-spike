# java-grpc-spike

A simple standalone gRPC client/server spike based on the hello world example 
in [grpc-java](https://github.com/grpc/grpc-java/tree/master/examples), just 
to get sense of how to layout a project.
 
The client and server scripts are the same as the example project, to build them
 
```bash
     ./wgradle clean installDist
```
 
And to run them,
 
```bash
 ./build/install/java-grpc-spike/bin/hello-world-server
 
 ./build/install/java-grpc-spike/bin/hello-world-client yuh 
```
 
 
