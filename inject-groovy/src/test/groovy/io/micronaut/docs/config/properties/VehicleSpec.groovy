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
package io.micronaut.docs.config.properties

import io.micronaut.context.ApplicationContext
import io.micronaut.context.ApplicationContext
import io.micronaut.context.DefaultApplicationContext
import io.micronaut.context.DefaultBeanContext
import io.micronaut.context.env.MapPropertySource
import spock.lang.Specification

/**
 * @author Graeme Rocher
 * @since 1.0
 */
class VehicleSpec extends Specification {

    void "test start vehicle"() {


        when:
        // tag::start[]
        ApplicationContext applicationContext = ApplicationContext.run(
                ['my.engine.cylinders':'8'],
                "test"
        )

        Vehicle vehicle = applicationContext
                .getBean(Vehicle)
        println(vehicle.start())
        // end::start[]

        then:
        vehicle.start() == "Ford Engine Starting V8 [rodLength=6.0]"
    }
}
