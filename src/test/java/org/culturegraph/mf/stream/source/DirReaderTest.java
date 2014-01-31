/* Copyright 2013 Pascal Christoph, hbz .
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
package org.culturegraph.mf.stream.source;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;

import org.culturegraph.mf.stream.sink.ObjectWriter;
import org.junit.Test;

/**
 * Tests {@link DirReader} in combination with {@link DirReader} and compressed
 * files. Especially, if there are files which are not (properly) compressed,
 * the other files should be computed nonetheless.
 * 
 * @author Pascal Christoph (dr0i)
 * 
 */
public final class DirReaderTest {

	@Test
	public void testUtf8IsDefaultEncoding() throws IOException {
		final DirReader dirReader = new DirReader();
		final FileOpener opener = new FileOpener();
		opener.setCompression("bzip2");
		dirReader.setReceiver(opener);
		opener.setReceiver(new ObjectWriter<Reader>("stdout"));
		dirReader.setRecursive(true);
		try {
			dirReader.process(new File(Thread.currentThread().getContextClassLoader()
					.getResource("./").toURI()).getAbsolutePath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		opener.closeStream();
	}

}
