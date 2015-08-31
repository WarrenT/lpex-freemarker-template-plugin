These notes are for me so I don't lose track of some of the ideas I had in how the dynamic data model will work.


---


Add new XML attribute to the promptGroup definition for promptGroup
name to be used in the template as a reference.

**HINT**
The repeats arraylist below should be defined as an arraylist of
Hashes like so:

```
     ArrayList<Hash> repeats = new ArrayList<Hash>();
```

The form data object should be structured like this

```
                          formData
                                |
                   Hash promptGroups
                   /                       \
  ArrayList<Hash> repeats      Hash values
                |
        Hash values
```

**IDEA**
Could we actually always store in repeats array but just provide a
shortcut to the first element to use if not repeatable? Would it work
to add the formHash at repeats(0) then have the hash values at the
same level as repeats just equal repeats(0)?

```
     repeats(repeatIndex) = formHash;
     if (repeatIndex == 0) {
         //Assign the shortcut to the first element in repeats
         HashValues = repeats(0);
     }
```

**When form is parsed:**
For each promptGroup formData.getPromptGroup("promptGroupName").add
(new ArrayList repeats)

For each repeat, keep track of the index of the repeat. 0 is the default

When form is rendered, the capture of the data will build a hash from
the fields on screen (all of the prompts in the group) using the name
of each prompt as the hash key. It will then insert that hash into the
formData object in a location determined by the repeatable flag. Below
is the logic:

**NOTE**
This logic may not be required if we always map Hash values to
repeats(0). See the above IDEA.

```
     if (repeatable) {
         formData.promptGroups("promptGroupName").repeats(repeatIndex)
= formHash;
     }else{
         formData.promptGroups("promptGroupName") = formHash;
     }
```

Then to get access to it you just access formData.promptGroups
("promptGroupName"). Inside that will be an arraylist of hashes or a
single hash depending on what the configuration of the prompt group is
(repeatable or not).

**NOTE**
If we can map Hash values to repeats(0) then we will actually
alwys have both under the promptGroup data element.

In the template it would look something like this:

```
<#list param in paramGroup.repeats>
     pr_${param.name}
<#list>
```

**UPDATE**
Create a shortcut for the repeat(0) Hash values to give the illusion
that we aren't storing in a list of repeats when it was not
repeatable. It will seem like for nonrepeatable promptGroups that the
hash of prompt values are attached directly to the promptGroup level.
So the template will look something like this:

`     p ${mainPromptGroup.procedureName}...`

Instead of:

`     p ${mainPromptGroup.repeats(0).procedureName}...`

**NOTE**
The second option is valid and would work but is not necessary
with the shortcut.

If we know it never was supposed to repeat, then don't make them state
that it's the first repeat element over and over again.