package com.mridang.downbox.helpers;
/*
 *  (c) Copyright (c) 2012 Mridang Agarwalla
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
import java.io.File;
import java.util.HashSet;

import com.mridang.downbox.threads.FolderDatabase;

public class DirectoryChecker {

   /**
    * This checks the directory to see if it is a torrent directory
    *
    * @param    dirDirectory   the directory to check
    * @returns                 a boolean value indicating the result
    */
    public static Boolean isTorrentDirectory(File dirDirectory) {

        try {

            Long lngDirectorySize = DirectorySize.getFileSize(dirDirectory, null, new HashSet<String>());

            if (FolderDatabase.hasFolder(dirDirectory.getName()) == false && lngDirectorySize > 0L) {

                if ((DirectorySize.getSizeOfVideoFiles(dirDirectory) * 100L / lngDirectorySize) > 75) {
                    return true;
                }

                if ((DirectorySize.getSizeOfAudioFiles(dirDirectory) * 100L/ lngDirectorySize) > 75) {
                    return true;
                }

            }

            return false;

        } catch (Exception e) {
           return false;
        }

    }

}