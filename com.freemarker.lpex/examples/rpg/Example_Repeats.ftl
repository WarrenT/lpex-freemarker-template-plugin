<#ftl strip_whitespace=true>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Example Repeats</name>
  <description>Erstellt einen mehrzeiligen Kommentarblock im /FREE Format.</description>
  <promptgroups>
    <promptgroup name="comment" repeatable="yes" maxRepeats="10">
      <prompt>
        <type maxLength="50" uppercase="false">text</type>
        <name>line</name>
        <description>Kommentarzeile.</description>
        <label>Zeile</label>
        <hint>
        </hint>
      </prompt>
      <prompt>
        <type checkedValue="&quot;" uncheckedValue="">checkbox</type>
        <name>quoted</name>
        <description>Gibt an, ob die Zeile in Anführungszeichen gesetzt wird, oder nicht.</description>
        <label>Anführungszeichen</label>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>-->          // ---------------------------------------------------------------
          //   Beispiel:  Fügt einen Kommentarblock unter Verwendung
          //              eines "text" Prompts ein. Die PromptGroup
          //              ist hierbei als Mehrfachvorkommen deklariert.
          //              (Repeatable=true, MayRepeats=10)
          // ---------------------------------------------------------------
<#list comment.repeats as var>
          //  ${var.quoted}${var.line}${var.quoted}
</#list>