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

     h nomain
     h option(*NoDebugIO : *SrcStmt : *ShowCpy : *xRef)
     h copyright('Copyright (c) 2012 Estes Express. All rights reserved.')


      // Includes
      /////////////////////////////////
      /define ${ModuleInfo.namespace}_PrivateAll
      /define ${ModuleInfo.namespace}_PublicConstants
      /define ${ModuleInfo.namespace}_PublicStructs
      /define ${ModuleInfo.namespace}_PublicProcedures 
      /copy *libl/qrpglesrc,${ModuleInfo.sharedElementsCopybook}


      // Private Procedures
      /////////////////////////////////
      // *None


      // Define Global Variables
      /////////////////////////////////
      // *None



      //////////////////////////////////////////////////////////////////////
      // PUBLIC PROCEDURES                                                //
      //////////////////////////////////////////////////////////////////////
      
      //REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE 
      //
      //  Define public procedures here with 3 lines between each one
      //
      //REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE 








      //////////////////////////////////////////////////////////////////////
      // PRIVATE PROCEDURES                                               //
      //////////////////////////////////////////////////////////////////////
      
      //REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE 
      //
      //  Define private procedures here with 3 lines between each one
      //
      //REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE REMOVE 

