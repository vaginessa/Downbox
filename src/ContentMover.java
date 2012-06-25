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
import java.nio.file.*;

/**
 * Class to fetch subtitles for a the video files in a directory
 */
public class ContentMover {

   /** The logger instance that will provide all the logger functionality
    */
    private static Logger logger = Logger.getLogger("janitor");

   /**
    * Moves all the media files in the directory to the other directory.
    *
    * @param  dirSourceDirectory       the source directory
    * @param  dirDestinationDirectory  the destination directory
    */
    public static void moveFiles(File dirSource, File dirDestination, File dirReference) throws Exception {

         try {

            logger.info(String.format("Moving all media files in: %s", dirSource));

            for (File filItem : dirSource.listFiles()) {
                if (filItem.isDirectory()) {
                    moveFiles(filItem, dirDestination, dirReference);
                } else {
                    if (FileExtention.isVideoFile(filItem) || FileExtention.isAudioFile(filItem) 
                    || FileExtention.isSubtitleFile(filItem) || FileExtention.isPlaylistFile(filItem)) {
                        logger.info(String.format("Moving the media file: %s", filItem.getName()));
                        dirDestination.toPath().resolve(dirReference.toPath().relativize(filItem.toPath())).toFile().getParentFile().mkdirs();
                        if (!dirDestination.toPath().resolve(dirReference.toPath().relativize(filItem.toPath())).toFile().exists()) {
                            filItem.renameTo(dirDestination.toPath().resolve(dirReference.toPath().relativize(filItem.toPath())).toFile());
                            logger.fine("Moved.");
                        } else {
                            logger.fine("Skipped.");
                            continue;
                        }
                    }
                }
            }

            logger.fine("Moved.");

        } catch (Exception e) {
            logger.warning("There was an error moving the files");
            throw e;
        }

    }

}