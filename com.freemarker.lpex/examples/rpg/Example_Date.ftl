<#ftl strip_whitespace=true>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Test Date</name>
  <description>Demonstriert die Verwendung eines "Date" Prompts.</description>
  <promptgroups>
    <promptgroup name="input" repeatable="no">
      <prompt>
        <type dateFormat="yyyy-MM-dd">date</type>
        <name>date_iso</name>
        <description>Datum eingeben oder auswählen.</description>
        <label>Datum (ISO)</label>
      </prompt>
      <prompt>
        <type dateFormat="dd.MM.yyyy">date</type>
        <name>date_eur</name>
        <description>Datum eingeben oder auswählen.</description>
        <label>Datum (EUR)</label>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>-->          // ---------------------------------------------------------------
          //   Beispiel:  Demonstriert die Verwendung eines "date" 
          //              Prompts mit verschiedenen Formatierungen.
          // ---------------------------------------------------------------
          //   Eingegeben: ${input.date_iso}   (ISO)
          //               ${input.date_eur}   (EUR)
          // ---------------------------------------------------------------          