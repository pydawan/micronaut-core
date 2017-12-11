/*
 * Copyright 2017 original authors
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
package org.particleframework.session

import org.particleframework.context.ApplicationContext
import org.particleframework.context.event.ApplicationEventListener
import org.particleframework.session.event.AbstractSessionEvent
import org.particleframework.session.event.SessionCreatedEvent
import org.particleframework.session.event.SessionDeletedEvent
import org.particleframework.session.event.SessionDestroyedEvent
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.inject.Singleton
import java.time.Instant

/**
 * @author Graeme Rocher
 * @since 1.0
 */
class InMemorySessionStoreSpec extends Specification {

    void "test in-memory session store read and write"() {
        when:
        ApplicationContext applicationContext = ApplicationContext.run()
        SessionStore sessionStore = applicationContext.getBean(SessionStore)
        TestListener listener = applicationContext.getBean(TestListener)
        Session session = sessionStore.newSession()

        session.put("foo", "bar")

        then:
        session != null
        session.id
        !session.expired
        session.creationTime
        session.lastAccessedTime

        when:
        sessionStore.save(session)
        def lastAccessedTime = session.lastAccessedTime

        then:
        listener.events.size() == 1
        listener.events[0] instanceof SessionCreatedEvent

        when:
        session == sessionStore.findSession(session.id).get().get()

        then:
        session.lastAccessedTime > lastAccessedTime
        session.get("foo").isPresent()
        session.get("foo").get() == "bar"

        when:
        listener.events.clear()
        sessionStore.deleteSession(session.id)
        def conditions = new PollingConditions(timeout: 10)

        then:
        conditions.eventually {
            assert listener.events.size() == 1
            assert listener.events[0] instanceof SessionDeletedEvent
            assert !sessionStore.findSession(session.id).get().isPresent()
        }

    }

    @Singleton
    static class TestListener implements ApplicationEventListener<AbstractSessionEvent> {
        List<AbstractSessionEvent> events = []
        @Override
        void onApplicationEvent(AbstractSessionEvent event) {
            events.add(event)
        }
    }
}
