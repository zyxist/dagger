/*
 * Copyright (C) 2016 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dagger.internal.codegen;

import static com.google.common.base.StandardSystemProperty.JAVA_CLASS_PATH;
import static com.google.common.base.StandardSystemProperty.PATH_SEPARATOR;
import static com.google.testing.compile.Compiler.javac;
import static java.util.stream.Collectors.joining;

import com.google.common.base.Splitter;
import com.google.testing.compile.Compiler;

/** {@link Compiler} instances for testing Dagger. */
final class Compilers {

  /** Returns a compiler that runs the Dagger processor. */
  static Compiler daggerCompiler() {
    return javac().withProcessors(new ComponentProcessor());
  }

  static Compiler daggerCompilerWithoutGuava() {
    return daggerCompiler().withOptions("-classpath", classpathWithoutGuava());
  }

  private static final String GUAVA = "guava";

  private static String classpathWithoutGuava() {
    return Splitter.on(PATH_SEPARATOR.value())
        .splitToList(JAVA_CLASS_PATH.value())
        .stream()
        .filter(jar -> !jar.contains(GUAVA))
        .collect(joining(PATH_SEPARATOR.value()));
  }
}
