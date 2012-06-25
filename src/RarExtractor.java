/*
 *  (c) Copyright (c) 2010 Mridang Agarwalla
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
import java.io.*;
import java.util.logging.Logger;
import java.nio.*;
import java.nio.file.*;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;

/**
 * Class to extract RAR archives from a directory
 */
public class RarExtractor {

   /** The logger instance that will provide all the logger functionality
    */
    private static Logger logger = Logger.getLogger("janitor");

   /**
    * Uncompresses all the RAR archives in a given directory
    *
    * @param  objSourceDirectory        the source directory
    * @param  objDestinationDirectory   the destination directory
    */
    public static void extractFiles(File objArchive, File objDestinationDirectory) throws Exception {

        try {

            logger.info(String.format("Extracting the compressed file: %s", objArchive.getName()));

            Archive archive = new Archive(objArchive);
            FileHeader objHeader = archive.nextFileHeader();

            while (objHeader != null) {

                if (!objHeader.isDirectory()) {

                    File objFile = new File(objDestinationDirectory, objHeader.isUnicode() ? objHeader.getFileNameW() : objHeader.getFileNameString());
                    objFile.getParentFile().mkdirs();
                    objFile.createNewFile();

                    OutputStream stream = new FileOutputStream(objFile);
                    archive.extractFile(objHeader, stream);
                    stream.close();

                }

                objHeader = archive.nextFileHeader();

            }
            
            logger.fine("Extracted.");

        } catch (RarException e) {
            throw new Exception();
        } catch (SecurityException e) {
            throw new Exception();
        } catch (IOException e) {
            throw new Exception();
        }

    }

}