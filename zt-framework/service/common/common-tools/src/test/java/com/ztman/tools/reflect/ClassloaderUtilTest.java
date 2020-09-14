package com.ztman.tools.reflect;

import org.junit.Test;

public class ClassloaderUtilTest {

	@Test
	public void test() {
		ClassLoader loader = ClassLoaderUtil.getDefaultClassLoader();
		ClassLoaderUtil.isPresent("ClassUtil", loader);
	}
}
