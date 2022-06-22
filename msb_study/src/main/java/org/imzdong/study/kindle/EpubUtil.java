package org.imzdong.study.kindle;

import pdf.converter.PdfConverter;

import java.io.File;

/**
 * https://www.dll-files.com/gsdll64.dll.html
 * 需要安装gsdll64.dll
 * @author zhoud
 * @since 2022/6/19 19:30
 */
public class EpubUtil {

    public static void main(String[] args) {
        String pdfPath = "D:\\WorkSpace\\study\\dzs\\";
        PdfConverter.convert(new File(pdfPath + "\\lsx.pdf"))
                .intoEpub("lsx-smb", new File(pdfPath +"\\lsx.epub"));

        PdfConverter.convert(new File(pdfPath + "\\table.pdf"))
            .intoEpub("table-fsmb", new File(pdfPath +"\\table.epub"));
    }
}

/**

 Step 1

 Open the zip-file you downloaded from DLL-files.com .
 Extract the DLL-file to a location on your computer.
 If you are unsure about how to extract your zip-file we recommend using File Viewer Plus 3. With it you can open your zip-file as well as over 300 other file formats (like video, music, images, pdf etc.) It’s a handy tool to keep around.

 After extracting your zip-file, place the extracted DLL-file in the directory of the program that is requesting the file. Make sure to use a 32bit DLL-file for a 32bit program, and a 64bit DLL-file for a 64bit program. Failure to do so will likely result in a 0xc000007b error.


 Step 2

 If that does not help your problem, place the file to your system directory.

 By default, this is

 C:\Windows\System (Windows 95/98/Me),

 C:\WINNT\System32 (Windows NT/2000), or

 C:\Windows\System32 (Windows XP, Vista, 7, 8, 8.1, 10).



 On a 64bit version of Windows, the default folder for 32bit DLL-files is C:\Windows\SysWOW64\ , and for 64bit dll-files C:\Windows\System32\ .

 Make sure to overwrite any existing files (but make a backup copy of the original file).



 Reboot your computer.



 Step 3

 If the problem still occurs, try the following to register the DLL-file:



 For 32bit DLL-files on a 32bit Windows, and for 64bit DLL-files on a 64bit Windows:

 Open an elevated command prompt.
 To do this, click Start, click All Programs, click Accessories, right-click "Command Prompt", and then click Run as administrator.
 In Windows 8/10, go to the Start screen. Start typing cmd and Windows will find "Command Prompt". Right click "Command Prompt" and choose "Run as administrator".
 If you are prompted for an administrator password or for a confirmation, type the password, or click Allow.
 Type regsvr32 "filename".dll and press Enter.
 Registering 32bit DLL-files on a 64bit Windows:

 Open an elevated command prompt, as instructed above.
 In the command prompt, start by typing following and press enter:
 cd c:\windows\syswow64\
 then type the following and press enter:
 regsvr32 c:\windows\syswow64\"filename".dll
 Finally, reboot your PC one last time to refresh the memory. That should do it!



 IF YOU HAVE ANY TROUBLE GETTING THIS TO WORK HERE ARE SOME ADDITIONAL RESOURCES:
 Check our forum if anyone has had similar trouble.
 If you are unsure where to place the file, systemexplorer.net usually has good intel on common locations on users' computers.
 If the problem you are facing is related to a specific program, and you know which one, get in touch with the original developer and try to get a fix from them.
 If the problem is related to an unknown program and/or significantly interferes with usage of your computer, there are only two options left:
 Do a system restore to restore to an eariler problem-free period, find out how to do it here.
 Last option, if all else fails, this is a real pain, I know, but a full Windows reinstall/reset may be necessary

 */
