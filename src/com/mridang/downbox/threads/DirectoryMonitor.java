package com.mridang.downbox.threads;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.mridang.downbox.helpers.DirectoryChecker;
import com.mridang.downbox.yooayes.FolderMenu;

public class DirectoryMonitor extends Thread {

   /**
    * This monitors the directory and raises events.
    */
    @Override
    public void run() {

        try {

            Path pthPath = Paths.get(System.getProperty("user.home"), "Downloads");

            WatchService watcher = pthPath.getFileSystem().newWatchService();
            pthPath.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {

                WatchKey watchKey = watcher.take();

                for (WatchEvent<?> event: watchKey.pollEvents()) {

                    if (pthPath.resolve(event.context().toString()).toFile().isDirectory()) {

                          if (DirectoryChecker.isTorrentDirectory(pthPath.resolve(event.context().toString()).toFile())) {
                              FolderMenu.addMenuItem(pthPath, true);
                          }

                    }

                    watchKey.reset();
                }

            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }

        return;

    }

}