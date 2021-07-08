<#ftl strip_whitespace=true>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Example Multiline</name>
  <description>Erstellt einen mehrzeiligen Kommentarblock im /FREE Format.</description>
  <promptgroups>
    <promptgroup name="comment" repeatable="no">
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
</template>-->          // ---------------------------------------------------------------
          //   Beispiel:  Fügt einen Kommentarblock unter Verwendung
          //              eines "multiline" Prompts ein. Hierbei müssen
          //              folgende Parameter an die Direktive übergeben 
          //              werden:
          //                text      - umzubrechender Text
          //                width     - Textbreite
          //
          //              Innerhalb der "multiline" Direktive stehen folgende
          //              Variablen zur Verfügung:
          //                %{i}      - Laufende Zeilennummer
          //                %{text}   - Textzeile und Anfangsposition
          //
          //              Der umzubrechende Text wird zwischen %{text}
          //              und "maxColumn" umgebrochen.
          // ---------------------------------------------------------------
<@multiline text=comment.text width=45	 >
          //  %{i}. %{text}
</@multiline>
