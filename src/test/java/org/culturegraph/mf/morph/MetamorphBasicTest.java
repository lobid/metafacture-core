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
package org.culturegraph.mf.morph;

import java.util.HashMap;
import java.util.Map;


import org.culturegraph.mf.framework.DefaultStreamReceiver;
import org.culturegraph.mf.framework.StreamReceiver;
import org.culturegraph.mf.morph.Data;
import org.culturegraph.mf.morph.Morph;
import org.culturegraph.mf.morph.NamedValueReceiver;
import org.culturegraph.mf.morph.NamedValueSource;
import org.culturegraph.mf.types.MultiMap;
import org.culturegraph.mf.types.NamedValue;
import org.junit.Assert;
import org.junit.Test;


/**
 * tests {@link Morph}
 * 
 * @author Markus Michael Geipel
 */
public final class MetamorphBasicTest implements NamedValueReceiver {
	
	private static final String NAME = "name";
	private static final String VALUE = "s234234ldkfj";
	private static final String ENTITY_NAME = "dfsdf";
	private static final String LITERAL_NAME = "fghgh";
	private static final String MATCHING_PATH = ENTITY_NAME + '.' + LITERAL_NAME;
	private static final String NON_MATCHING_PATH1 = "s234234";
	private static final String NON_MATCHING_PATH2 = ENTITY_NAME + ".lskdj";
	private static final StreamReceiver EMPTY_RECEIVER = new DefaultStreamReceiver() {
		@Override
		public void literal(final String name, final String value) {
			// nothing
		}
	};
	private static final String FEEDBACK_VAR = "@var";
	private static final String MAP_NAME = "sdfklsjef";

	private NamedValue namedValue;
	
	private static Morph newMetamorphWithData(final NamedValueReceiver receiver){
		final Morph metamorph = new Morph();
		metamorph.setReceiver(EMPTY_RECEIVER);
		final Data data = new Data(MATCHING_PATH);
		data.setName(NAME);
		data.setNamedValueReceiver(receiver);
		metamorph.registerData(data);
		return metamorph;
	}
	
	@Test
	public void testSimpleMapping() {
		final Morph metamorph = newMetamorphWithData(this);
		namedValue = null;
		metamorph.startRecord(null);
		
		//simple mapping without entity
		metamorph.literal(NON_MATCHING_PATH1, VALUE);
		Assert.assertNull(namedValue);
		
		metamorph.literal(MATCHING_PATH, VALUE);
		Assert.assertNotNull(namedValue);
		Assert.assertEquals(VALUE, namedValue.getValue());
		namedValue = null;
		
		// mapping with entity
		metamorph.startEntity(ENTITY_NAME);
		metamorph.literal(LITERAL_NAME, VALUE);
		Assert.assertNotNull(namedValue);
		Assert.assertEquals(VALUE, namedValue.getValue());
		namedValue = null;
		
		metamorph.literal(NON_MATCHING_PATH2, VALUE);
		Assert.assertNull(namedValue);
		
		metamorph.endEntity();
		metamorph.literal(LITERAL_NAME, VALUE);
		Assert.assertNull(namedValue);
	}
	
	@Test
	public void testMultiMap(){
		final Morph metamorph = new Morph();
		final Map<String, String> map = new HashMap<String, String>();
		map.put(NAME, VALUE);
		
		metamorph.putMap(MAP_NAME, map);
		Assert.assertNotNull(metamorph.getMap(MAP_NAME));
		Assert.assertNotNull(metamorph.getValue(MAP_NAME,NAME));
		Assert.assertEquals(VALUE, metamorph.getValue(MAP_NAME,NAME));
		
		map.put(MultiMap.DEFAULT_MAP_KEY, VALUE);
		Assert.assertNotNull(metamorph.getValue(MAP_NAME,"sdfadsfsdf"));
		Assert.assertEquals(VALUE, metamorph.getValue(MAP_NAME,"sdfsdf"));
		
	}
	
	@Test
	public void testFeedback() {
	
		final Morph metamorph = new Morph();
		metamorph.setReceiver(EMPTY_RECEIVER);
		Data data;
		
		data = new Data(MATCHING_PATH);
		data.setName(FEEDBACK_VAR);
		data.setNamedValueReceiver(metamorph);
		metamorph.registerData(data);
		
		data = new Data(FEEDBACK_VAR);
		data.setName(NAME);
		data.setNamedValueReceiver(this);
		metamorph.registerData(data);
		
		namedValue = null;
		
		metamorph.startRecord(null);
		metamorph.literal(MATCHING_PATH, VALUE);
		Assert.assertNotNull(namedValue);
		Assert.assertEquals(VALUE, namedValue.getValue());
		namedValue = null;
		
		
	}
	

	@Test(expected=IllegalStateException.class)
	public void testEntityBorderBalanceCheck1(){
		final Morph metamorph = new Morph();
		metamorph.setReceiver(EMPTY_RECEIVER);
		
		metamorph.startRecord(null);
		metamorph.startEntity(ENTITY_NAME);
		metamorph.startEntity(ENTITY_NAME);
		metamorph.endEntity();
		metamorph.endRecord();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testEntityBorderBalanceCheck2(){
		final Morph metamorph = new Morph();
		metamorph.setReceiver(EMPTY_RECEIVER);
		
		metamorph.startRecord(null);
		metamorph.startEntity(ENTITY_NAME);
		metamorph.endEntity();
		metamorph.endEntity();
		metamorph.endRecord();
	}
	



	@Override
	public void receive(final String name, final String value, final NamedValueSource source, final int recordCount, final int entityCount) {
		this.namedValue = new NamedValue(name, value);
		
	}
}