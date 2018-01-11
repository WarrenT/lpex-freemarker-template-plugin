<#ftl strip_whitespace=false>
<#--<?xml version="1.0" encoding="utf-8"?>
<template>
  <name>Public Procedure Template</name>
  <description>Use this template when you need to create a public facing procedure for an API.</description>
  <promptgroups>
    <promptgroup name="procedure" repeatable="no">
      <prompt>
        <type checkedValue="export" uncheckedValue="">checkbox</type>
        <name>export</name>
        <description>Check this box if the procedure should be exported from this module.</description>
        <label>Export?</label>
      </prompt>
      <prompt>
        <type uppercase="false">text</type>
        <name>name</name>
        <description>Enter a procedure name</description>
        <label>Procedure Name</label>
        <hint>exampleProcedureName</hint>
      </prompt>
      <prompt>
        <type uppercase="false">text</type>
        <name>description</name>
        <description>Enter a procedure description</description>
        <label>Procedure Description</label>
        <hint>Example procedure</hint>
      </prompt>
      <prompt>
        <type>multiline</type>
        <name>documentation</name>
        <description>Documentation for this procedure to describe what it's function is.</description>
        <label>Procedure Documentation</label>
        <hint>This procedure will...</hint>
      </prompt>
      <prompt>
        <type uppercase="false">text</type>
        <name>returnDescription</name>
        <description>Return parameter description</description>
        <label>Return Description</label>
        <hint>This procedure returns...</hint>
      </prompt>
    </promptgroup>
    <promptgroup name="parameter" repeatable="yes" maxRepeats="10">
      <prompt>
        <type uppercase="false">text</type>
        <name>name</name>
        <description>Name of the parameter (pr_ will be added automatically)</description>
        <label>Parameter Name</label>
        <hint>exampleParameterName</hint>
      </prompt>
      <prompt>
        <type uppercase="false">text</type>
        <name>description</name>
        <description>Description of the parameter</description>
        <label>Parameter Description</label>
        <hint>Parameter description...</hint>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>-->
     /**
      *--------------------------------------------------------------------
      * @brief ${procedure.description}
      *
      * ${procedure.documentation}
      *
      * @author ${author}
      * @date   ${date}
      *
      <#list parameter.repeats as parm>
      * @param  ${parm.description}
      </#list>  
      *
      * @return ${procedure.returnDescription}
      *--------------------------------------------------------------------
      */
 
      // BEGIN PROCEDURE
     p ${procedure.name}...
     p                 b                   ${procedure.export}
 
      // Procedure Interface
      /////////////////////////////////
     d ${procedure.name}...
     d                 pi              n
     <#list parameter.repeats as parm>
     d  pr_${parm.name}...
     d                                 n
     </#list>  
 
      // Define Local Vars
      /////////////////////////////////
      // *None
 
      //Main
      /free
      
       //TODO: Perform some business logic here
 
       return true;
      /end-free
 
     p ${procedure.name}...
     p                 e
      // END PROCEDURE

