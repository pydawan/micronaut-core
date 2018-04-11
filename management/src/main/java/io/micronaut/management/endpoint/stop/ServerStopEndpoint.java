/*
 * Copyright 2017-2018 original authors
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
package io.micronaut.management.endpoint.stop;

import io.micronaut.context.ApplicationContext;
import io.micronaut.management.endpoint.Endpoint;
import io.micronaut.management.endpoint.Write;

import java.util.LinkedHashMap;
import java.util.Map;

@Endpoint(id = "stop", defaultEnabled = false)
public class ServerStopEndpoint {

    private final ApplicationContext context;
    private final Map<String, String> message;

    ServerStopEndpoint(ApplicationContext context) {
        this.context = context;
        this.message = new LinkedHashMap<>(1);
        this.message.put("message", "Server shutdown started");
    }

    @Write(consumes = {})
    public Object stop() {
        try {
            return message;
        } finally {
            Thread thread = new Thread(this::stopServer);
            thread.setContextClassLoader(getClass().getClassLoader());
            thread.start();
        }
    }

    private void stopServer() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        this.context.stop();
    }
}
