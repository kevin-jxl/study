package com.ztman.tools.concurrent;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.ztman.tools.concurrent.jsr166e.LongAdder;

public class ConcurrentsTest {

	@Test
	public void longAdder() {
		LongAdder counter = Concurrents.longAdder();
		counter.increment();
		counter.add(2);
		assertThat(counter.longValue()).isEqualTo(3L);
	}

}
