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
package org.culturegraph.mf.framework;

/**
 * Default implementation of {@link StreamReceiver} which
 * simply does nothing. Do not use this class as a base class
 * for modules which are implement {@link StreamPipe}; use 
 * {@link DefaultStreamPipe} for that.
 * 
 * @see DefaultStreamPipe
 * 
 * @author Markus Michael Geipel, Christoph Böhme
 * 
 */
public class DefaultStreamReceiver extends DefaultLifeCycle implements StreamReceiver {

	// CHECKSTYLE OFF: StrictDuplicateCode
	// Code duplication in DefaultStreamPipe and DefaultStreamReceiver
	// cannot be avoided. DefaultStreamPipe combines the logic 
	// from DefaultSender and DefaultStreamReceiver but can only
	// have one of these classes as its base class. Since 
	// DefaultSender was chosen as the base class, the logic
	// from DefaultStreamReceiver needs to be duplicated.

	@Override
	public void startRecord(final String identifier) {
		// Default implementation does nothing
	}

	@Override
	public void endRecord(){
		// Default implementation does nothing
	}

	@Override
	public void startEntity(final String name) {
		// Default implementation does nothing	
	}

	@Override
	public void endEntity() {
		// Default implementation does nothing
	}
	
	@Override
	public void literal(final String name, final String value) {
		// Default implementation does nothing
	}

	// CHECKSTYLE ON: StrictDuplicateCode

}
