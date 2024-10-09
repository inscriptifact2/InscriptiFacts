/*
 * PTMReader.java
 *
 * Created on July 3, 2004, 11:38 PM
 */

package isf.ptm.fileio;

import isf.ptm.formats.PTM;

/**
 * 
 * @author Default
 */
public interface PTMReader {

	public PTM readPTM() throws java.io.IOException;
        public void reset(boolean b);
 
}
