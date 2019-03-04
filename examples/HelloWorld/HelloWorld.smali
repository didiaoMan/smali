# HelloWorld.java对应的smali汇编文件
# public class HelloWorld {
# 
#     public static void main(String[] args) {
#         System.out.println("Hello World!");
#     }
#
# }

.class public LHelloWorld;

.super Ljava/lang/Object;

.method public static main([Ljava/lang/String;)V
    .registers 2

    sget-object v0, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string	v1, "Hello World!"

    invoke-virtual {v0, v1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    return-void
.end method
