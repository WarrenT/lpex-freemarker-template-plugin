# Introduction #

Repeatable prompt groups will present the group of prompts within it until the user does not supply any data for the prompts or the max number of repeats is met, whichever comes first.


## Accessing Collected Data ##

Just like with any other prompt group, you will access it by name in your template. However, since this prompt group is repeatable, we must use the list directive to loop through each entry. You can read more about the list directive on the FreeMarker site: http://freemarker.sourceforge.net/docs/ref_directive_list.html

```
<#list sequence as item>
    ...
</#list>
```

You must specify the name of the collection to loop through as the sequence. The form engine uses the standard name "repeats" for the name of repeatable prompt group collections. So to loop through the collection you just create a list with the promptGroupName.repeats as the sequence, then a name of your choosing for each element in the collection as the item.

```
<#list promptGroupName as item>
    ${item.promptName}
</#list>
```

Each element in the collection will be an instance of the prompt group with each prompt referenced by name. For instance, a prompt group name "parameters" that collects a name and description and repeats up to 10 times will result in a collection of objects that have a name element and a description element.

```
<#list parameters as parameter>
    ${parameter.name}
</#list>
```


## Example ##

You can see the example of getting multiple parameters in the form definition below then after that you can see how to access the collected information in the template example.

```
<template>
  <name>Template Name Here</name>
  <description>Template description here.</description>
  <promptgroups>
    <promptgroup name="parameter" repeatable="yes" maxRepeats="10">
      <prompt>
        <type>text</type>
        <name>name</name>
        <label>Parameter Name</label>
        <description>Name of the parameter</description>
        <hint>exampleParameterName</hint>
      </prompt>
      <prompt>
        <type>text</type>
        <name>description</name>
        <label>Parameter Description</label>
        <description>Description of the parameter</description>
        <hint>Parameter description...</hint>
      </prompt>
    </promptgroup>
  </promptgroups>
</template>
```

### Template ###

```
<#list parameter.repeats as parm>
ParameterName:        ${parm.name}
ParameterDescription: ${parm.description}
</#list>  
```

### Merged Result ###

If the user entered 3 sets then stopped, the merged template would look like this.

```
ParameterName:        name 1
ParameterDescription: description 1
ParameterName:        name 2
ParameterDescription: description 2
ParameterName:        name 3
ParameterDescription: description 3
```