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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import java.io.*;

/**
 * Class containing methods for showing the processing status
 */
public class ProcessingStatus {

   /**
    * This updates the status content menu item to show that the application
    * is currently processing a directory
    */
    public static void startProcessing(String strDirectory) {

        TrayNotifications.showNotification(LocalStrings.getText("processingItem"), String.format(LocalStrings.getText("itemUnderProcessingByApplication"), strDirectory));

        Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
                try {

                    InputStream istIcon = Downbox.class.getClassLoader().getResourceAsStream("traywork.ico");
                    Image imgTrayIcon = new Image(SysTray.shell.getDisplay(), istIcon);

                    SysTray.itmTrayItem.setImage(imgTrayIcon);
                    SysTray.mimStatus.setText(LocalStrings.getText("doingWork"));

                } catch (Exception e) {
                    e.printStackTrace(); // Nothing to be handled here.
                }

            }

        });

    }


   /**
    * This updates the status content menu item to show that the application
    * is currently idle and waiting.
    */
    public static void stopProcessing(String strDirectory) {

        Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
                try {

                    InputStream istIcon = Downbox.class.getClassLoader().getResourceAsStream("traytick.ico");
                    Image imgTrayIcon = new Image(SysTray.shell.getDisplay(), istIcon);

                    SysTray.itmTrayItem.setImage(imgTrayIcon);
                    SysTray.mimStatus.setText(LocalStrings.getText("idleWaiting"));

                } catch (Exception e) {
                    e.printStackTrace(); // Nothing to be handled here.
                }

            }

        });

        TrayNotifications.showNotification(LocalStrings.getText("itemProcessed"), String.format(LocalStrings.getText("itemProcessedByApplication"), strDirectory));

    }

}