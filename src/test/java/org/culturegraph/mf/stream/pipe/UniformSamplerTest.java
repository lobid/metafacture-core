/*
 *  Copyright 2013 Deutsche Nationalbibliothek
 *
 *  Licensed under the Apache License, Version 2.0 the "License";
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.culturegraph.mf.stream.pipe;

import java.util.Arrays;

import org.culturegraph.mf.stream.pipe.ObjectBuffer;
import org.culturegraph.mf.stream.pipe.UniformSampler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 * Tests for {@link UniformSampler}
 * 
 * @author Christoph Böhme
 *
 */
@RunWith(Parameterized.class)
public final class UniformSamplerTest {

	private static final long SEED = 1;  // Use a fixed random seed to make the test repeatable.
	private static final int SAMPLE_SIZE = 5;
	
	private static final int LARGE_SET = 100;
	private static final int SMALL_SET = 3;
	
	private final int setSize;
	private final String[] expected;
	
	public UniformSamplerTest(final int setSize, final String[] expected) {
		this.setSize = setSize;
		this.expected = expected.clone();
	}

	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { 
				{ LARGE_SET, new String[] { "93", "43", "78", "35", "42" } },
				{ SMALL_SET, new String[] { "0", "1", "2" } },
			});
	}
	
	@Test
	public void test() {
		final UniformSampler<String> sampler = new UniformSampler<String>(SAMPLE_SIZE);
		sampler.setSeed(SEED);
		final ObjectBuffer<String> buffer = new ObjectBuffer<String>();
		
		sampler.setReceiver(buffer);
		
		for(int i = 0; i < setSize; ++i) {
			sampler.process(Integer.toString(i));
		}
		sampler.closeStream();
		
		for(int i = 0; i < expected.length; ++i) {
			Assert.assertEquals(expected[i], buffer.pop());
		}
		Assert.assertNull("The sample contains more than " + expected.length + " records", buffer.pop());		
	}
	
}