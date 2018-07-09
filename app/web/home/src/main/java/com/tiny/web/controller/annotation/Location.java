/**
 * Location.java
 *
 *
 */
package com.tiny.web.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tiny.common.enums.LocationEnum;

/**
 * @author e521907
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Location {

	LocationEnum nav();

	LocationEnum menu();
}
