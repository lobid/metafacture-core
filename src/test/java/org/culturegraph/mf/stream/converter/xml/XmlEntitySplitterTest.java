/* Copyright 2013  hbz, Pascal Christoph.
 * Licensed under the Eclipse Public License 1.0 */
package org.culturegraph.mf.stream.converter.xml;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.antlr.runtime.RecognitionException;
import org.culturegraph.mf.Flux;
import org.junit.Test;

/**
 * @author Pascal Christoph (dr0i)
 *
 */
public final class XmlEntitySplitterTest {

	@Test
	public void testFlux() throws URISyntaxException, IOException, RecognitionException {
		final File fluxFile = new File("examples/read/xmlSplitter/xmlEntitySplitting.flux");
		Flux.main(new String[] { fluxFile.getAbsolutePath() });
	}
}