/**
 * AbstractPublisher.java
 *
 *
 */
package com.tiny.core.service.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.PayloadApplicationEvent;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class AbstractPublisher implements ApplicationEventPublisherAware {

	/**
	 * publisher
	 */
	@Autowired
	private static ApplicationEventPublisher	publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		publisher = applicationEventPublisher;
	}

	/**
	 * Notify all <strong>matching</strong> listeners registered with this
	 * application of an application event. Events may be framework events
	 * (such as RequestHandledEvent) or application-specific events.
	 * @param event the event to publish
	 * @see org.springframework.web.context.support.RequestHandledEvent
	 */
	public static void publishEvent(ApplicationEvent event) {
		publisher.publishEvent(event);
	}

	/**
	 * Notify all <strong>matching</strong> listeners registered with this
	 * application of an event.
	 * <p>If the specified {@code event} is not an {@link ApplicationEvent},
	 * it is wrapped in a {@link PayloadApplicationEvent}.
	 * @param event the event to publish
	 * @since 4.2
	 * @see PayloadApplicationEvent
	 */
	public static void publishEvent(Object event) {
		publisher.publishEvent(event);
	}

}
