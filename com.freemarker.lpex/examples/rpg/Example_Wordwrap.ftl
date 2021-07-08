<#ftl strip_whitespace=true>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Example Multiline</name>
  <description>Erstellt einen mehrzeiligen Kommentarblock im /FREE Format.</description>
  <promptgroups>
    <promptgroup name="comment" repeatable="no">
      <prompt>
        <type maxLength="12" defaultValue="" uppercase="false">text</type>
        <name>name</name>
        <description>Geben Sie hier ihren Namen ein.</description>
        <label>Name</label>
        <hint>
        </hint>
      </prompt>
      <prompt>
        <type dateFormat="dd.MM.yyyy">date</type>
        <name>date</name>
        <description>Geben Sie hier das Datum der Änderung ein.</description>
        <label>
        </label>
      </prompt>
      <prompt>
        <type>multiline</type>
        <name>text</name>
        <description>Eingeben Kommentarblock.</description>
        <label>Kommentar</label>
        <hint>
        </hint>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>-->          // ------------------------------------------------------------------
          //   Beispiel:  Fügt einen Kommentarblock unter Verwendung
          //              der "wordwrap" Direktive ein. Hierbei müssen
          //              folgende Parameter an die Direktive übergeben 
          //              werden:
          //                text      - umzubrechender Text
          //                width     - Textbreite
          //                return    - Name der Ergebnisvariablen
          // ------------------------------------------------------------------
          //  Date        Name          Description                              
          // -----------  ------------  ---------------------------------------  
<@wordwrap text=comment.text width=38 return="lines" />
<#assign i=0 />
<#list lines as line>
 <#assign i=i+1 />
  <#if i == 1>
          //  ${comment.date?right_pad(10)}  ${comment.name?right_pad(12)}  ${line}
  <#else>
          //                            ${line}
  </#if> 
</#list>
          // ------------------------------------------------------------------
          