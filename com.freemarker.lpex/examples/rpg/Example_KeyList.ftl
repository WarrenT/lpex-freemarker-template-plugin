<#ftl strip_whitespace=true>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Example Key Field List</name>
  <description>Retrieves the key columns of a given file.</description>
  <promptgroups>
    <promptgroup name="fields" repeatable="no">
      <prompt>
        <type maxLength="10" uppercase="true">text</type>
        <name>file</name>
        <description>Specify the name of the file.</description>
        <label>File</label>
        <hint>file name</hint>
      </prompt>
      <prompt>
        <type maxLength="10" defaultValue="*LIBL" uppercase="true">text</type>
        <name>library</name>
        <description>Specify the name of the library that contains the file.</description>
        <label>Library</label>
        <hint>library name</hint>
      </prompt>
      <prompt>
        <type maxLength="10" defaultValue="*FIRST" uppercase="true">text</type>
        <name>recordFormat</name>
        <description>Specify the name of the record format or *FIRST.</description>
        <label>Record format</label>
        <hint>record format name</hint>
      </prompt>
      <prompt>
        <type maxLength="3" defaultValue="-1" uppercase="false">text</type>
        <name>numFields</name>
        <description>Specify the number of key fields or -1.</description>
        <label>Number of fields</label>
        <hint>number of key fields</hint>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>-->          // ---------------------------------------------------------------
          //   Example:   Produces a list of key fields of a given file
          //              The following parameters must be specified:
          //                file      - File, whose columns are retrieved.
          //                lib       - Library that contains "file".
          //                            Default: *LIBL
          //                rcdFmt    - Name of the record format.
          //                            Default: *FIRST
          //                numFields - Anzahl abzurufender Schlüsselfelder.
          //                            Default: -1 = alle Felder
          //                prefix    - Prefix used to further qualify the 
          //                            the field properties. It is recommended
          //                            to use a prefix in order to avoid ambiguous
          //                            variable names in the template.
          //                            Default: no prefix
          //
          //              The following variables are available within the 
          //              "keyList" directive:
          //                file      - Name of the file.
          //                lib       - Name of the library that contains the file.
          //                rcdFmt    - Name of the record format.
          //
          //                name      - Name of the key column.
          //                orderBy   - Ordering. 
          //                              A=ascending
          //                              D=descending
          // ---------------------------------------------------------------
<@keyList file=fields.file lib=fields.library rcdFmt=fields.recordFormat numFields=fields.numFields  prefix="keyField"; cnt>
<#if cnt == 1>
          //  Datei     : ${keyField.lib}/${keyField.file}
          //  Satzformat: ${keyField.rcdFmt}
</#if>
          //  ${cnt?string?left_pad(3)}.  ${keyField.name?right_pad(10)}  -  (${keyField.orderBy})
</@keyList>

          // Optionally the field list can be copied to an array in order
          // to spin through the list several times. Using an array improves
          // performance:
<#assign keyFields = [] >
<@keyList file=fields.file lib=fields.library rcdFmt=fields.recordFormat numFields=fields.numFields  prefix="keyField">
  <#assign keyFields = keyFields + [keyField] >
</@keyList>

          // Once the array is loaded you can spin through its items
          // like that:
<#list keyFields as keyField >
          //  ${keyField.name?right_pad(10)}  ${keyField.orderBy}
</#list>
