# lpex-freemarker-template-plugin
This is a FreeMarker template engine plugin for the LPEX Editor in IBM Rational Developer for IBM i (RDi).

The project had been forked from WarrenT/lpex-freemarker-template-plugin for fixing bugs and adding enhancements.

The main enhancements added so far are:

* New directive for listing fields of physical, display and printer files.
* New directive for listing key fields of physical or logical files.
* New directive for creating multiline text with bullets, e.g. for creating comments.
* New directive for word-wrapping text.
* Various enhancements of the C# Template Builder editor.

Refer to file 'build/create_package.txt' of project 'com.freemarker.lpex.updatesite'
to see how to build the project. Building the Plug-in, including building the
Template Builder exe file, are driven by Ant.
