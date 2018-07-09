/**
 * AssertUtils.java
 *
 *
 */
package com.tiny.common.util;

import org.springframework.util.Assert;

/**
 * @author e521907
 * @version 1.0
 *
 */
final public class AssertUtils {
	/**
	 * @param obj
	 * @param message
	 */
	public static void isNull(final Object obj, final String message) {
		check(new AssertTemplate() {

			@Override
			public void doAssert() {
				Assert.isNull(obj, message);
			}
		});
	}

	/**
	 * @param obj
	 * @param message
	 */
	public static void notNull(final Object obj, final String message) {
		check(new AssertTemplate() {

			@Override
			public void doAssert() {
				Assert.notNull(obj, message);
			}
		});
	}

	/**
	 * @param text
	 * @param message
	 */
	public static void hasText(final String text, final String message) {
		check(new AssertTemplate() {

			@Override
			public void doAssert() {
				Assert.hasText(text, message);
			}
		});
	}

	/**
	 * @param expression
	 * @param message
	 */
	public static void state(final boolean expression, final String message) {
		check(new AssertTemplate() {

			@Override
			public void doAssert() {
				Assert.state(expression, message);
			}
		});
	}

	/**
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * @param assertTemp
	 */
	public static void check(AssertTemplate assertTemp) {
		assertTemp.doAssert();
	}

	/**
	 * @author e521907
	 * @version 1.0
	 *
	 */
	public interface AssertTemplate {
		/**
		 * doAssert
		 */
		public void doAssert();
	}

}
