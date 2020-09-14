package com.ztman.tools.concurrent;

import static org.assertj.core.api.Assertions.*;

import com.ztman.tools.base.RuntimeUtil;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.ztman.tools.base.ObjectUtil;

public class ThreadUtilTest {
	@Test
	public void testCaller() {
		hello();
		new MyClass().hello();
		Assertions.assertThat(RuntimeUtil.getCurrentClass()).isEqualTo("ThreadUtilTest");
		assertThat(RuntimeUtil.getCurrentMethod())
				.isEqualTo("ThreadUtilTest.testCaller()");

	}

	private void hello() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		System.out.println(ObjectUtil.toPrettyString(stacktrace));

		assertThat(RuntimeUtil.getCallerClass()).isEqualTo("ThreadUtilTest");
		assertThat(RuntimeUtil.getCallerMethod())
				.isEqualTo("ThreadUtilTest.testCaller()");
	}

	public static class MyClass {
		public void hello() {
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			System.out.println(ObjectUtil.toPrettyString(stacktrace));

			assertThat(RuntimeUtil.getCallerClass()).isEqualTo("ThreadUtilTest");
			assertThat(RuntimeUtil.getCallerMethod())
					.isEqualTo("ThreadUtilTest.testCaller()");
		}
	}
}
