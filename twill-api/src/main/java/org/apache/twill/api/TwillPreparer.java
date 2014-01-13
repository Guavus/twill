/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.twill.api;

import org.apache.twill.api.logging.LogHandler;

import java.net.URI;

/**
 * This interface exposes methods to set up the Twill runtime environment and start a Twill application.
 */
public interface TwillPreparer {

  /**
   * Adds a {@link LogHandler} for receiving an application log.
   * @param handler The {@link LogHandler}.
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer addLogHandler(LogHandler handler);

  /**
   * Sets the user name that runs the application. Default value is get from {@code "user.name"} by calling
   * {@link System#getProperty(String)}.
   * @param user User name
   * @return This {@link TwillPreparer}.
   *
   * @deprecated This method will be removed in future version.
   */
  @Deprecated
  TwillPreparer setUser(String user);

  /**
   * Sets the list of arguments that will be passed to the application. The arguments can be retrieved
   * from {@link TwillContext#getApplicationArguments()}.
   *
   * @param args Array of arguments.
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer withApplicationArguments(String... args);

  /**
   * Sets the list of arguments that will be passed to the application. The arguments can be retrieved
   * from {@link TwillContext#getApplicationArguments()}.
   *
   * @param args Iterable of arguments.
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer withApplicationArguments(Iterable<String> args);

  /**
   * Sets the list of arguments that will be passed to the {@link TwillRunnable} identified by the given name.
   * The arguments can be retrieved from {@link TwillContext#getArguments()}.
   *
   * @param runnableName Name of the {@link TwillRunnable}.
   * @param args Array of arguments.
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer withArguments(String runnableName, String...args);

  /**
   * Sets the list of arguments that will be passed to the {@link TwillRunnable} identified by the given name.
   * The arguments can be retrieved from {@link TwillContext#getArguments()}.
   *
   * @param runnableName Name of the {@link TwillRunnable}.
   * @param args Iterable of arguments.
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer withArguments(String runnableName, Iterable<String> args);

  /**
   * Adds extra classes that the application is dependent on and is not traceable from the application itself.
   * @see #withDependencies(Iterable)
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer withDependencies(Class<?>...classes);

  /**
   * Adds extra classes that the application is dependent on and is not traceable from the application itself.
   * E.g. Class name used in {@link Class#forName(String)}.
   * @param classes set of classes to add to dependency list for generating the deployment jar.
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer withDependencies(Iterable<Class<?>> classes);

  /**
   * Adds resources that will be available through the ClassLoader of the {@link TwillRunnable runnables}.
   * @see #withResources(Iterable)
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer withResources(URI...resources);

  /**
   * Adds resources that will be available through the ClassLoader of the {@link TwillRunnable runnables}.
   * Useful for adding extra resource files or libraries that are not traceable from the application itself.
   * If the URI is a jar file, classes inside would be loadable by the ClassLoader. If the URI is a directory,
   * everything underneath would be available.
   *
   * @param resources Set of URI to the resources.
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer withResources(Iterable<URI> resources);

  /**
   * Adds the set of paths to the classpath on the target machine for all runnables.
   * @see #withClassPaths(Iterable)
   * @return This {@link TwillPreparer}
   */
  TwillPreparer withClassPaths(String... classPaths);

  /**
   * Adds the set of paths to the classpath on the target machine for all runnables.
   * Note that the paths would be just added without verification.
   * @param classPaths Set of classpaths
   * @return This {@link TwillPreparer}
   */
  TwillPreparer withClassPaths(Iterable<String> classPaths);

  /**
   * Adds security credentials for the runtime environment to gives application access to resources.
   *
   * @param secureStore Contains security token available for the runtime environment.
   * @return This {@link TwillPreparer}.
   */
  TwillPreparer addSecureStore(SecureStore secureStore);

  /**
   * Starts the application.
   * @return A {@link TwillController} for controlling the running application.
   */
  TwillController start();
}