# Introduction #

The LPEX FreeMarker Template supports 4 different data types when defining the template form. Text, multi-line text, date, and checkbox are supported with radio button and drop down planned for future development.


## Text ##

The text prompt type will render a standard form field that only allows a single line of text. This prompt type is rather simple and does not accept any attributes on the type element.

```
<prompt>
  <type>text</type>
  <name>enterNameHere</name>
  <label>Label Here</label>
  <description>Label description here.</description>
  <hint>Enter a hint here</hint>
</prompt>
```


## Multiline ##

The multiline prompt type will render a text input field that allows for multiple lines of text to be entered. It will scale to fit the space available on the rendered form. Like the simple text prompt type, this does not accept any attributes on the type element.

```
<prompt>
  <type>multiline</type>
  <name>enterNameHere</name>
  <label>Label Here</label>
  <description>Label description here.</description>
  <hint>Enter a hint here</hint>
</prompt>
```


## Date ##

The date prompt type will render a calender select widget on the form for selecting the date while also allowing for it to be typed manually. The type element does accept a dateFormat attribute for specifying the date format the collected date should be formatted as before merging it into the template. The hint element is ignored for date prompt types and there is currently no way to override the default date value of today. The date format string should follow the rules of the  [java.text.SimpleDateFormat](http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html) class.

```
<prompt>
  <type dateFormat="MM/dd/yyyy">date</type>
  <name>enterNameHere</name>
  <label>Label Here</label>
  <description>Label description here.</description>
</prompt>
```


## Checkbox ##

The checkbox type will render a checkbox widget on the form. using the checkedValue and uncheckedValue attributes on the type element you can specify what strings to use when merging the value with the template.

```
<prompt>
  <type checkedValue="export" uncheckedValue="">checkbox</type>
  <name>enterNameHere</name>
  <label>Label Here</label>
  <description>Label description here.</description>
  <hint>Enter a hint here</hint>
</prompt>
```