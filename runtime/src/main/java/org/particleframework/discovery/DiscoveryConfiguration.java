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
package org.particleframework.discovery;

import org.particleframework.core.util.Toggleable;

/**
 * Base class for common discovery configuration options
 *
 * @author graemerocher
 * @since 1.0
 */
public abstract class DiscoveryConfiguration implements Toggleable {

    public static final String PREFIX = "discovery";

    private boolean enabled = true;

    /**
     * @return Is discovery enabled? Defaults to true
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
