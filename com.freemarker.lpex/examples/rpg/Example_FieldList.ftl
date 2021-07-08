<#ftl strip_whitespace=true>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Example Field List</name>
  <description>Retrieves the columns of a given file.</description>
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
    </promptgroup>
    <promptgroup name="Comment" repeatable="no">
      <prompt>
        <type defaultValue="Creates a list of fields of a given a given file. Demonstrates 
how to directly use the items returned or to load the items into an array to 
process the array multiple times.">multiline</type>
        <name>text</name>
        <description>Specify a comment that describes the example template.</description>
        <label>Comment</label>
        <hint>comment</hint>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>-->          // ---------------------------------------------------------------
          //   Example:   Creates a list of fields of a given file.
          //              The following parameters must be specified:
          //                file      - File, whose columns are retrieved.
          //                lib       - Library that contains "file".
          //                            Default: *LIBL
          //                rcdFmt    - Name of the record format.
          //                            Default: *FIRST
          //                prefix    - Prefix used to further qualify the 
          //                            the field properties. It is recommended
          //                            to use a prefix in order to avoid ambiguous
          //                            variable names in the template.
          //                            Default: no prefix
          //
          //              The following variables are available within the 
          //              "fieldList" directive:
          //
          //                file      - Name of the file.
          //                lib       - Name of the library that contains the file.
          //                rcdFmt    - Name of the record format.
          //
          //                name      - Name of the column.
          //                text      - Description.
          //                type      - Data type: A, B, P, S, L, T, Z, F, G
          //                length    - Field length.
          //                decPos    - Number of decimal positions.
          //              Only available for physical file, no display or printer files:
          //                refFile   - Name of the reference file.
          //                refLib    - Name of the library that contains the reference file.
          //                refRcdFmt - Record format of the reference file.
          //                refFld    - Name of the reference field.
          // ---------------------------------------------------------------

          // Using the "fieldList" directive:
<@fieldList file=fields.file lib=fields.library rcdFmt=fields.recordFormat prefix="field" ; cnt>
<#if cnt == 1>
          //  Datei     : ${field.lib}/${field.file}
          //  Satzformat: ${field.rcdFmt}
</#if>
          //  ${cnt?string?left_pad(3)}.  ${field.name?right_pad(10)}  ${field.type}(${field.length?left_pad(5)},${field.decPos?left_pad(2)}) Ref: ${field.refLib}/${field.refFile}(${field.refRcdFmt}).${field.refFld}
</@fieldList>

          // Optionally the field list can be copied to an array in order
          // to spin through the list several times. Using an array improves
          // performance:
<#assign dbFields = [] >
<@fieldList file=fields.file lib=fields.library rcdFmt=fields.recordFormat prefix="dbField" ; cnt>
  <#assign dbFields = dbFields + [dbField] >
</@fieldList>

          // Once the array is loaded you can spin through its items
          // like that:
<#list dbFields as field >
          //  ${field.name?right_pad(10)}  ${field.type}(${field.length?left_pad(5)},${field.decPos?left_pad(2)}) Ref: ${field.refLib}/${field.refFile}(${field.refRcdFmt}).${field.refFld}
</#list>

          // At the end, insert the comment block.
<@multiline text=Comment.text width=45	 >
          //  %{i}. %{text}
</@multiline>
          