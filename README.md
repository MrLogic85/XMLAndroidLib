# XMLAndroidLib
This is a light weight eady to use XML library. Mostly used for easy creation of XML strings on the go, or easy parsing of XML with a undestandable and searchable tree structure.
Examlpe
===
Creating XML
---
```
XMLElement root = new XMLElement("Foo");
root.createChild("Bar");
String xmlString = root.toString();
```
Will give the following string.
```
<Foo>
    <Bar/>
</Foo>
```
Adding data and attributes will expand the output to always be easy to read.
```
XMLElement root = new XMLElement("Foo");
root.addAttribute("key", "value");
XMLElement child = root.createChild("Bar");
child.setData("Café");
String xmlString = root.toString();

<Foo key="value">
    <Bar>Café</Bar>
</Foo>
```
The same xml structure can be done by chaining.
```
String xmlString = new XMLElement("Foo")
        .addAttribute("key", "value")
        .createChild("Bar")
        .setData("Café")
        .getTopParent() // <- Do this to get back to top, we are currently at the lates child
        .toString();
```
Creating a xml structure from code structure is easily done bo implementing IXMLParsable or extending XMLParsableAdaptor, this is extremele useful for saving data to a file.
**Example**
```
class Foo extends XMLParsableAdaptor {
    String key = "value";
    Bar child = new Bar();
    
    public void putAttributes(XMLElement element) {
        element.addAttribute("key", key);
        element.addChild(child.toXMLElement());
    }
}

class Bar extends XMLParsableAdaptor {
    String data = "Café";

    public void putAttributes(XMLElement element) {
        element.setData(data);
    }
}

new Foo().toString()

<Foo key="value">
    <Bar>Café</Bar>
</Foo>
```
Access
===
To access this android library, add the following to your gradle.build

```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    compile 'com.github.MrLogic85:XMLAndroidLib:1.0.3'
}
```
