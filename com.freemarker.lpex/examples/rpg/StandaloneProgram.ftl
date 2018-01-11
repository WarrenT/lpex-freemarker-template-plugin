<#ftl strip_whitespace=false>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Public Module Template</name>
  <description>Use this template when you need to create a public facing procedure for an API.</description>
  <promptgroups>
    <promptgroup name="ModuleInfo" repeatable="no">
      <prompt>
        <type uppercase="false">text</type>
        <name>namespace</name>
        <description>Enter a namespace for this module</description>
        <label>Namespace</label>
        <hint>exampleProcedureName</hint>
      </prompt>
      <prompt>
        <type uppercase="false">text</type>
        <name>brief</name>
        <description>Enter a breif description (this typically matches the description on the object)</description>
        <label>Brief Description</label>
        <hint>Example Module Brief</hint>
      </prompt>
      <prompt>
        <type>multiline</type>
        <name>documentation</name>
        <description>Documentation for this module to describe what it's purpose is.</description>
        <label>Module Documentation</label>
        <hint>This module will...</hint>
      </prompt>
      <prompt>
        <type uppercase="false">text</type>
        <name>sharedElementsCopybook</name>
        <description>Shared elements copy book name</description>
        <label>Shared Elements Copybook</label>
        <hint>AAA10Y001</hint>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>-->     /**********************************************************************
      * @brief ${ModuleInfo.namespace} - ${ModuleInfo.brief}
      *
      * ${ModuleInfo.documentation}
      *
      * @author ${author}
      * @date   ${date}
      *
      * @category ${ModuleInfo.namespace}
      **********************************************************************
      */

     h dftactgrp(*no) actgrp(*caller) bnddir('${ModuleInfo.namespace?upper_case}')
     h option(*NoDebugIO : *SrcStmt : *ShowCpy : *xRef)
     h copyright('Copyright (c) 2012 Estes Express. All rights reserved.')


      // Includes
      /////////////////////////////////
      /define ${ModuleInfo.namespace}_PrivateAll
      /copy *libl/qrpglesrc,${ModuleInfo.sharedElementsCopybook}
 
      // Files and Screens
      /////////////////////////////////
      // *None
 
      // Includes
      /////////////////////////////////
      // *None
 
      // Private Procedures
      /////////////////////////////////
      // *None
 
      // Global Variables
      /////////////////////////////////
      // *None
 
      //Main
      /////////////////////////////////
      /free
 
       //@TODO: Enter business logic here
 
       *inlr = *on;
       return;
      /end-free
