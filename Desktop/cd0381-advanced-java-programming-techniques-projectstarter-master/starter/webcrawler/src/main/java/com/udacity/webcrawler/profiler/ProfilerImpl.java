package com.udacity.webcrawler.profiler;

import jdk.dynalink.StandardOperation;

import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

/**
 * Concrete implementation of the {@link Profiler}.
 */
final class ProfilerImpl implements Profiler {

  private final Clock clock;
  private final ProfilingState state = new ProfilingState();
  private final ZonedDateTime startTime;

  @Inject
  ProfilerImpl(Clock clock) {
    this.clock = Objects.requireNonNull(clock);
    this.startTime = ZonedDateTime.now(clock);
  }

  @Override
  public <T> T wrap(Class<T> klass, T delegate) {
    Objects.requireNonNull(klass);

    // TODO: Use a dynamic proxy (java.lang.reflect.Proxy) to "wrap" the delegate in a
    //       ProfilingMethodInterceptor and return a dynamic proxy from this method.
    //       See https://docs.oracle.com/javase/10/docs/api/java/lang/reflect/Proxy.html.
    if (!classContainsProfiled(klass)){
      throw new IllegalArgumentException();
    }
    Object proxy = Proxy.newProxyInstance(
            klass.getClassLoader(),
            new Class<?>[]{klass},
            new ProfilingMethodInterceptor(clock,delegate, state)
    );
    return (T) proxy;

  }

  @Override
  public void writeData(Path path) {
    // TODO: Write the ProfilingState data to the given file path. If a file already exists at that
    //       path, the new data should be appended to the existing file.
    if (Files.notExists(path)){
      try {
        Files.createFile(path);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
      try(Writer writer = Files.newBufferedWriter(path)) {
        writeData(writer);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

  }

  @Override
  public void writeData(Writer writer) throws IOException {
    writer.write("Run at " + RFC_1123_DATE_TIME.format(startTime));
    writer.write(System.lineSeparator());
    state.write(writer);
    writer.write(System.lineSeparator());
  }

  private Boolean classContainsProfiled(Class<?> klass){
    boolean containsProfiledAnnotation = false;
    for (Method m: klass.getDeclaredMethods()){
      if (m.getAnnotation(Profiled.class) != null){
        containsProfiledAnnotation = true;
        break;
      }
    }
    return containsProfiledAnnotation;
  }
}
