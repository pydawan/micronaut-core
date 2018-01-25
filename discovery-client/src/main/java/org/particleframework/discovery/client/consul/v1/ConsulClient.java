/*
 * Copyright 2018 original authors
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
package org.particleframework.discovery.client.consul.v1;

import org.particleframework.http.HttpStatus;
import org.particleframework.http.annotation.Body;
import org.particleframework.http.annotation.Get;
import org.particleframework.http.annotation.Put;
import org.particleframework.http.client.Client;
import org.reactivestreams.Publisher;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * A non-blocking HTTP client for consul
 *
 * @author Graeme Rocher
 * @since 1.0
 */
@Client(id = ConsulClient.SERVICE_ID, path = "/v1")
public interface ConsulClient {
    /**
     * The default ID of the consul service
     */
    String SERVICE_ID = "consul";
    /**
     * Register a new {@link CatalogEntry}. See https://www.consul.io/api/catalog.html
     * @param entry The entry to register
     *
     * @return A {@link Publisher} that emits a boolean true if the operation was successful
     */
    @Put("/catalog/register")
    Publisher<Boolean> register(@NotNull @Body CatalogEntry entry);

    /**
     * Register a new {@link CatalogEntry}. See https://www.consul.io/api/catalog.html
     * @param entry The entry to register
     *
     * @return A {@link Publisher} that emits a boolean true if the operation was successful
     */
    @Put("/catalog/deregister")
    Publisher<Boolean> deregister(@NotNull @Body CatalogEntry entry);
    /**
     * Register a new {@link CatalogEntry}. See https://www.consul.io/api/catalog.html
     * @param entry The entry to register
     *
     * @return A {@link Publisher} that emits a boolean true if the operation was successful
     */
    @Put("/agent/service/register")
    Publisher<HttpStatus> register(@NotNull @Body NewServiceEntry entry);


    /**
     * Register a new {@link CatalogEntry}. See https://www.consul.io/api/catalog.html
     * @param service The service to register
     *
     * @return A {@link Publisher} that emits a boolean true if the operation was successful
     */
    @Put("/agent/service/deregister/{service}")
    Publisher<HttpStatus> deregister(@NotNull String service);

    /**
     * Gets all of the registered services
     *
     * @return The {@link NewServiceEntry} instances
     */
    @Get("/agent/services")
    Publisher<Map<String,ServiceEntry>> getServices();

    /**
     * Gets the healthy services that are passing health checks
     *
     * @return The {@link HealthEntry} instances
     */
    @Get("/health/service/{service}")
    Publisher<List<HealthEntry>> getHealthyServices(@NotNull String service);


    /**
     * Gets all of the registered nodes
     *
     * @return All the nodes
     */
    @Get("/catalog/nodes")
    Publisher<List<CatalogEntry>> getNodes();

    /**
     * Gets all the nodes for the given data center
     *
     * @param datacenter The data center
     * @return A publisher that emits the nodes
     */
    @Get("/catalog/nodes?dc={datacenter}")
    Publisher<List<CatalogEntry>> getNodes(@NotNull String datacenter);

    /**
     * Gets all of the service names and optional tags
     *
     * @return A Map where the keys are service names and the values are service tags
     */
    @Get("/catalog/services")
    Publisher<Map<String, List<String>>> getServiceNames();
}