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

/**
 * just a combination of both {@link NamedValueReceiver} and {@link NamedValueSource}
 * 
 * @author Markus Michael Geipel
 *
 */
public interface NamedValuePipeHead extends NamedValuePipe{
	void appendPipe(NamedValuePipe namedValuePipe);
	void endPipe(NamedValueReceiver lastReceiver);
}
