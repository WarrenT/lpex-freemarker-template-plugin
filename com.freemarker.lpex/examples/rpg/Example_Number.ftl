<#ftl strip_whitespace=true>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Example Number</name>
  <description>Demonstriert die Verwendung eines "Number" Prompts.</description>
  <promptgroups>
    <promptgroup name="input" repeatable="no">
      <prompt>
        <type maxLength="3">number</type>
        <name>integer</name>
        <description>Eingeben Integer Wert, maximal 3-stellig.</description>
        <label>Integer</label>
      </prompt>
      <prompt>
        <type maxLength="7" decimalPositions="2">number</type>
        <name>decimal</name>
        <description>Eingeben Dezimalwert, maximal 5 Vorkomma- und 2 Nachkommastellen.</description>
        <label>Dezimalwert</label>
      </prompt>
      <prompt>
        <type>number</type>
        <name>no_limit</name>
        <description>Eingeben Wert ohne Beschränkung.</description>
        <label>Ohne Beschränkung</label>
      </prompt>
      <prompt>
        <type maxLength="7" decimalPositions="2" defaultValue="123,45">number</type>
        <name>with_default</name>
        <description>Eingeben Dezimalwert, maximal 5 Vorkomma- und 2 Nachkommastellen.</description>
        <label>Wert mit Default</label>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>-->          // ---------------------------------------------------------------
          //   Beispiel:  Demonstriert die Verwendung eines "number" 
          //              Prompts.
          // ---------------------------------------------------------------
          //   Eingegeben: Integer:            ${input.integer}
          //               Dezimalwert:        ${input.decimal}
          //               Keine Beschränkung: ${input.no_limit}
          //               Mit Standardwert:   ${input.with_default}
          // ---------------------------------------------------------------