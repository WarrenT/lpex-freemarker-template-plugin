# Introduction #

Decompress the binary into the droppins folder of your RDP install. Typically this will be "C:\Program Files\IBM\SDP\dropins". In the dropins folder, create a folder for this plugin, then put the two folders from the zip file inside of that new folder.

The folder structure when you're done should be:

```
C:\Program Files\IBM\SDP\dropins\
  LPEX FreeMarker Template Plugin
    eclipse
    examples
      rpg
      unknown
```


# Usage #

Go immediately to the preferences and configure a directory for your templates and the debug log file. Point your template folder at the included examples folder. Open a source file in the LPEX editor and hit ctrl-enter to activate the prompt. Choose one of the templates and fill out the prompt fields. The merged template should be inserted where your cursor was when you started.