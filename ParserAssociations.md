Parser associations are the mappings between a file being edited in the LPEX editor and the name of the subfolder under the templates directory that contains the appropriate templates for that parser. This is handy for when you want to organize the templates by language. The default configuration does this.

The format is the parser (or file extension) of the current source code being edited followed by an equals sign then the name of the template subfolder.

## Example ##
```
sqlrpgle=rpg
```

This will map any `*`.sqlrpgle source member to the rpg template subfolder under the configured templates directory.