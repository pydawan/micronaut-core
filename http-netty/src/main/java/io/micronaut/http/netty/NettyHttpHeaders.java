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
package io.micronaut.http.netty;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.http.MutableHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpHeaders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Delegates to Netty's {@link io.netty.handler.codec.http.HttpHeaders}
 *
 * @author Graeme Rocher
 * @since 1.0
 */
@Internal
public class NettyHttpHeaders implements MutableHttpHeaders {

    final io.netty.handler.codec.http.HttpHeaders nettyHeaders;
    final ConversionService<?> conversionService;

    public NettyHttpHeaders(io.netty.handler.codec.http.HttpHeaders nettyHeaders, ConversionService conversionService) {
        this.nettyHeaders = nettyHeaders;
        this.conversionService = conversionService;
    }

    public NettyHttpHeaders() {
        this.nettyHeaders = new DefaultHttpHeaders(false);
        this.conversionService = ConversionService.SHARED;
    }

    /**
     * @return The underlying Netty headers
     */
    public io.netty.handler.codec.http.HttpHeaders getNettyHeaders() {
        return nettyHeaders;
    }

    @Override
    public <T> Optional<T> get(CharSequence name, ArgumentConversionContext<T> conversionContext) {
        String value = nettyHeaders.get(name);
        if (value != null) {
            return conversionService.convert(value, conversionContext);
        }
        return Optional.empty();

    }

    @Override
    public List<String> getAll(CharSequence name) {
        return nettyHeaders.getAll(name);
    }

    @Override
    public Set<String> names() {
        return nettyHeaders.names();
    }

    @Override
    public Collection<List<String>> values() {
        Set<String> names = names();
        List<List<String>> values = new ArrayList<>();
        for (String name : names) {
            values.add(getAll(name));
        }
        return Collections.unmodifiableList(values);
    }

    @Override
    public String get(CharSequence name) {
        return nettyHeaders.get(name);
    }

    @Override
    public MutableHttpHeaders add(CharSequence header, CharSequence value) {
        nettyHeaders.add(header, value);
        return this;
    }
}
