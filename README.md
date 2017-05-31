# Error In Cleanup file input plugin for Embulk

I found Embulk an error that, when `taskSource.loadTask(PluginTask.class)` is called, raises `java.lang.RuntimeException: com.fasterxml.jackson.databind.JsonMappingException: Field 'XXXX' is required but not set at [Source: N/A; line: -1, column: -1]` at `cleanup` method in Java FileInputPlugin.
This repository is for reproduce the error.

# Verified version

- v0.8.18
- v0.8.22


# How to raise the error

```
./gradlew classpath
embulk run example/config.yml -Ilib
```

# Log

```
[embulk-input-error_in_cleanup] embulk run example/config.yml -Ilib
2017-05-31 22:29:20.105 +0900: Embulk v0.8.22
2017-05-31 22:29:21.913 +0900 [INFO] (0001:transaction): Loaded plugin embulk/input/error_in_cleanup from a load path
2017-05-31 22:29:21.994 +0900 [INFO] (0001:transaction): Using local thread executor with max_threads=16 / output tasks 8 = input tasks 1 * 8
2017-05-31 22:29:22.000 +0900 [INFO] (0001:transaction): {done:  0 / 1, running: 0}
2017-05-31 22:29:22.077 +0900 [INFO] (0001:transaction): {done:  1 / 1, running: 0}
2017-05-31 22:29:22.085 +0900 [WARN] (0001:transaction): Commit succeeded but cleanup failed. Ignoring this exception.
java.lang.RuntimeException: com.fasterxml.jackson.databind.JsonMappingException: Field 'Option3' is required but not set
 at [Source: N/A; line: -1, column: -1]
        at com.google.common.base.Throwables.propagate(Throwables.java:160) ~[embulk:0.8.22]
        at org.embulk.config.ModelManager.readObject(ModelManager.java:51) ~[embulk:0.8.22]
        at org.embulk.config.DataSourceImpl.loadTask(DataSourceImpl.java:214) ~[embulk:0.8.22]
        at org.embulk.input.error_in_cleanup.ErrorInCleanupFileInputPlugin.cleanup(ErrorInCleanupFileInputPlugin.java:115) ~[na:na]
        at org.embulk.spi.FileInputRunner.cleanup(FileInputRunner.java:139) ~[embulk:0.8.22]
        at org.embulk.exec.BulkLoader.doCleanup(BulkLoader.java:505) [embulk:0.8.22]
        at org.embulk.exec.BulkLoader.cleanupCommittedTransaction(BulkLoader.java:723) [embulk:0.8.22]
        at org.embulk.exec.BulkLoader.doRun(BulkLoader.java:578) [embulk:0.8.22]
        at org.embulk.exec.BulkLoader.access$000(BulkLoader.java:33) [embulk:0.8.22]
        at org.embulk.exec.BulkLoader$1.run(BulkLoader.java:389) [embulk:0.8.22]
        at org.embulk.exec.BulkLoader$1.run(BulkLoader.java:385) [embulk:0.8.22]
        at org.embulk.spi.Exec.doWith(Exec.java:25) [embulk:0.8.22]
        at org.embulk.exec.BulkLoader.run(BulkLoader.java:385) [embulk:0.8.22]
        at org.embulk.EmbulkEmbed.run(EmbulkEmbed.java:180) [embulk:0.8.22]
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_131]
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_131]
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_131]
        at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_131]
        at org.jruby.javasupport.JavaMethod.invokeDirectWithExceptionHandling(JavaMethod.java:453) [embulk:0.8.22]
        at org.jruby.javasupport.JavaMethod.invokeDirect(JavaMethod.java:314) [embulk:0.8.22]
        at org.jruby.java.invokers.InstanceMethodInvoker.call(InstanceMethodInvoker.java:46) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.cacheAndCall(CachingCallSite.java:338) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.call(CachingCallSite.java:163) [embulk:0.8.22]
        at org.jruby.ir.interpreter.InterpreterEngine.processCall(InterpreterEngine.java:315) [embulk:0.8.22]
        at org.jruby.ir.interpreter.StartupInterpreterEngine.interpret(StartupInterpreterEngine.java:73) [embulk:0.8.22]
        at org.jruby.ir.interpreter.InterpreterEngine.interpret(InterpreterEngine.java:90) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.MixedModeIRMethod.INTERPRET_METHOD(MixedModeIRMethod.java:214) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.MixedModeIRMethod.call(MixedModeIRMethod.java:200) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.DynamicMethod.call(DynamicMethod.java:205) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.cacheAndCall(CachingCallSite.java:358) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.call(CachingCallSite.java:195) [embulk:0.8.22]
        at org.jruby.ir.interpreter.InterpreterEngine.processCall(InterpreterEngine.java:324) [embulk:0.8.22]
        at org.jruby.ir.interpreter.StartupInterpreterEngine.interpret(StartupInterpreterEngine.java:73) [embulk:0.8.22]
        at org.jruby.ir.interpreter.InterpreterEngine.interpret(InterpreterEngine.java:84) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.MixedModeIRMethod.INTERPRET_METHOD(MixedModeIRMethod.java:179) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.MixedModeIRMethod.call(MixedModeIRMethod.java:165) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.DynamicMethod.call(DynamicMethod.java:197) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.cacheAndCall(CachingCallSite.java:338) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.call(CachingCallSite.java:163) [embulk:0.8.22]
        at org.jruby.ir.interpreter.InterpreterEngine.processCall(InterpreterEngine.java:315) [embulk:0.8.22]
        at org.jruby.ir.interpreter.StartupInterpreterEngine.interpret(StartupInterpreterEngine.java:73) [embulk:0.8.22]
        at org.jruby.ir.interpreter.Interpreter.INTERPRET_ROOT(Interpreter.java:112) [embulk:0.8.22]
        at org.jruby.ir.interpreter.Interpreter.execute(Interpreter.java:99) [embulk:0.8.22]
        at org.jruby.ir.interpreter.Interpreter.execute(Interpreter.java:35) [embulk:0.8.22]
        at org.jruby.ir.IRTranslator.execute(IRTranslator.java:42) [embulk:0.8.22]
        at org.jruby.Ruby.runInterpreter(Ruby.java:850) [embulk:0.8.22]
        at org.jruby.Ruby.loadFile(Ruby.java:2976) [embulk:0.8.22]
        at org.jruby.runtime.load.LibrarySearcher$ResourceLibrary.load(LibrarySearcher.java:243) [embulk:0.8.22]
        at org.jruby.runtime.load.LibrarySearcher$FoundLibrary.load(LibrarySearcher.java:34) [embulk:0.8.22]
        at org.jruby.runtime.load.LoadService.tryLoadingLibraryOrScript(LoadService.java:885) [embulk:0.8.22]
        at org.jruby.runtime.load.LoadService.smartLoadInternal(LoadService.java:525) [embulk:0.8.22]
        at org.jruby.runtime.load.LoadService.require(LoadService.java:396) [embulk:0.8.22]
        at org.jruby.RubyKernel.requireCommon(RubyKernel.java:963) [embulk:0.8.22]
        at org.jruby.RubyKernel.require19(RubyKernel.java:956) [embulk:0.8.22]
        at org.jruby.RubyKernel$INVOKER$s$1$0$require19.call(RubyKernel$INVOKER$s$1$0$require19.gen) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.JavaMethod$JavaMethodOneOrNBlock.call(JavaMethod.java:383) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.AliasMethod.call(AliasMethod.java:61) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.call(CachingCallSite.java:161) [embulk:0.8.22]
        at org.jruby.ir.interpreter.InterpreterEngine.processCall(InterpreterEngine.java:315) [embulk:0.8.22]
        at org.jruby.ir.interpreter.StartupInterpreterEngine.interpret(StartupInterpreterEngine.java:73) [embulk:0.8.22]
        at org.jruby.ir.interpreter.InterpreterEngine.interpret(InterpreterEngine.java:84) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.MixedModeIRMethod.INTERPRET_METHOD(MixedModeIRMethod.java:179) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.MixedModeIRMethod.call(MixedModeIRMethod.java:165) [embulk:0.8.22]
        at org.jruby.internal.runtime.methods.DynamicMethod.call(DynamicMethod.java:197) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.cacheAndCall(CachingCallSite.java:338) [embulk:0.8.22]
        at org.jruby.runtime.callsite.CachingCallSite.call(CachingCallSite.java:163) [embulk:0.8.22]
        at Users.takahiro_dot_nakayama.bin.embulk.embulk.command.embulk_bundle.invokeOther66:require(file:/Users/takahiro.nakayama/bin/embulk!/embulk/command/embulk_bundle.rb:51) [embulk:na]
        at Users.takahiro_dot_nakayama.bin.embulk.embulk.command.embulk_bundle.RUBY$script(file:/Users/takahiro.nakayama/bin/embulk!/embulk/command/embulk_bundle.rb:51) [embulk:na]
        at java.lang.invoke.MethodHandle.invokeWithArguments(MethodHandle.java:627) [na:1.8.0_131]
        at org.jruby.ir.Compiler$1.load(Compiler.java:111) [embulk:0.8.22]
        at org.jruby.Ruby.runScript(Ruby.java:834) [embulk:0.8.22]
        at org.jruby.Ruby.runNormally(Ruby.java:749) [embulk:0.8.22]
        at org.jruby.Ruby.runNormally(Ruby.java:767) [embulk:0.8.22]
        at org.jruby.Ruby.runFromMain(Ruby.java:580) [embulk:0.8.22]
        at org.jruby.Main.doRunFromMain(Main.java:425) [embulk:0.8.22]
        at org.jruby.Main.internalRun(Main.java:313) [embulk:0.8.22]
        at org.jruby.Main.run(Main.java:242) [embulk:0.8.22]
        at org.jruby.Main.main(Main.java:204) [embulk:0.8.22]
        at org.embulk.cli.Main.main(Main.java:23) [embulk:0.8.22]
Caused by: com.fasterxml.jackson.databind.JsonMappingException: Field 'Option3' is required but not set
 at [Source: N/A; line: -1, column: -1]
        at org.embulk.config.TaskSerDe$TaskDeserializer.deserialize(TaskSerDe.java:181) ~[embulk:0.8.22]
        at com.fasterxml.jackson.databind.ObjectMapper._readValue(ObjectMapper.java:3708) ~[embulk:0.8.22]
        at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2005) ~[embulk:0.8.22]
        at org.embulk.config.ModelManager.readObject(ModelManager.java:49) ~[embulk:0.8.22]
        ... 77 common frames omitted
2017-05-31 22:29:22.090 +0900 [INFO] (main): Committed.
2017-05-31 22:29:22.090 +0900 [INFO] (main): Next config diff: {"in":{},"out":{}}
```
