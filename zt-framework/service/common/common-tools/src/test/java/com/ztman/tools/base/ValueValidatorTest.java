package com.ztman.tools.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ValueValidatorTest {
	@Test
	public void testValidator() {
		assertThat(ValueValidator.checkAndGet(-1, 1, ValueValidator.Validator.INTEGER_GT_ZERO_VALIDATOR)).isEqualTo(1);
		assertThat(ValueValidator.checkAndGet("testUnEmpty", "isEmpty", ValueValidator.Validator.STRING_EMPTY_VALUE_VALIDATOR))
				.isEqualTo("testUnEmpty");
		assertThat(ValueValidator.checkAndGet("flase", "true", ValueValidator.Validator.STRICT_BOOL_VALUE_VALIDATOR))
				.isEqualTo("true");
	}
}
