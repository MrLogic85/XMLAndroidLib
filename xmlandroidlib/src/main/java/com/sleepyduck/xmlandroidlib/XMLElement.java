package com.sleepyduck.xmlandroidlib;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a XML Element and can be used to build a xml document
 *
 * @author Fredrik Metcalf
 */
public class XMLElement implements Serializable {
    /**
     * A name vaule pair used to store attributes in the {@link XMLElement}
     */
    public class Attribute implements Serializable {
        private static final long serialVersionUID = -2539266391216584081L;

        final String Name;
        final String Value;

        public Attribute(final String name, final String value) {
            Name = name;
            Value = value;
        }
    }

    private static final long serialVersionUID = 8743012699148733484L;

    private String mName = "";
    private String mData = "";
    private final List<XMLElement> mChildren = new ArrayList<XMLElement>();

    private XMLElement mParent = null;
    private final List<Attribute> mAttributes = new ArrayList<Attribute>();

    /**
     * Create and ampty element
     */
    public XMLElement() {

    }

    /**
     * Create a named element
     *
     * @param name
     */
    public XMLElement(final String name) {
        this();
        this.mName = name;
    }

    /**
     * Create a named element with data
     *
     * @param name
     * @param data
     */
    public XMLElement(final String name, final String data) {
        this(name);
        this.mData = data;
    }

    /**
     * Create named element with data and children.
     *
     * @param name
     * @param data
     * @param children
     */
    public XMLElement(final String name, final String data, final XMLElement... children) {
        this(name, data);
        mChildren.addAll(Arrays.asList(children));
    }

    /**
     * Internal {@link #toString()} used to create the XML String output.
     */
    private String toString(final String ident) {
        String str = ident + "<" + mName + "";
        if (mAttributes.size() > 0) {
            if (mAttributes.size() == 1) {
                str += " " + getAttributeName(0) + "=\"" + getAttributeValue(0) + "\"";
            } else for (int i = 0; i < mAttributes.size(); ++i) {
                str += "\n" + ident + "\t" + getAttributeName(i) + "=\"" + getAttributeValue(i) + "\"";
            }
        }
        if (mData.length() > 0 || mChildren.size() > 0) {
            str += ">" + mData + (mChildren.size() > 0 ? "\n" : "");
        } else {
            str += "/>";
        }

        for (final XMLElement el : mChildren) {
            str += el.toString(ident + "\t") + "\n";
        }

        if (mData.length() > 0 || mChildren.size() > 0) {
            str += (mChildren.size() > 0 ? ident : "") + "</" + mName + ">";
        }
        return str;
    }

    /**
     * Adds all the {@link XMLElement XMLElements} in the list as children of this XMLElement
     * @param elems
     * @return Return this object
     */
    public XMLElement addAllChildren(final List<XMLElement> elems) {
        mChildren.addAll(elems);
        for (final XMLElement el : elems)
            el.mParent = this;
        return this;
    }

    /**
     * Adds a {@link Attribute}
     * @param name
     * @param value
     * @return Returns this object
     */
    public XMLElement addAttribute(final String name, final String value) {
        mAttributes.add(new Attribute(name, value));
        return this;
    }

    /**
     * Adds the {@link XMLElement} to the the end of the child list
     * @param el
     * @return Returns the added child
     */
    public XMLElement addChild(final XMLElement el) {
        mChildren.add(el);
        el.mParent = this;
        return el;
    }

    /**
     * Creates a new {@link XMLElement} and adds it to the the end of the child list
     * @param name
     * @return Returns the created child
     */
    public XMLElement createChild(String name) {
        XMLElement el = new XMLElement(name);
        mChildren.add(el);
        el.mParent = this;
        return el;
    }

    /**
     * Returns the {@link Attribute} at index i
     */
    public Attribute getAttribute(final int i) {
        return mAttributes.get(i);
    }

    /**
     * Returns the {@link Attribute#Value} of the {@link Attribute} with the supplied {@link Attribute#Name}
     */
    public String getAttribute(final String name) {
        for (final Attribute a : mAttributes)
            if (a.Name.equals(name))
                return a.Value;
        return null;
    }

    public String getAttribute(String name, String defaultName) {
        for (final Attribute a : mAttributes)
            if (a.Name.equals(name))
                return a.Value;
        return defaultName;
    }

    /**
     * Return the {@link Attribute#Name} at index i
     */
    public String getAttributeName(final int i) {
        return mAttributes.get(i).Name;
    }

    /**
     * Returns the {@link Attribute#Value} at index i
     */
    public String getAttributeValue(final int i) {
        return mAttributes.get(i).Value;
    }

    /**
     * Returns all children
     */
    public List<XMLElement> getChildren() {
        return mChildren;
    }

    /**
     * Returns the data
     */
    public String getData() {
        return mData;
    }

    /**
     * Returns the first occurrence of a {@link XMLElement} with the given name, searches depth first.
     */
    public XMLElement getElement(final String name) {
        if (mName.equals(name))
            return this;
        for (final XMLElement el : mChildren) {
            final XMLElement foundEl = el.getElement(name);
            if (foundEl != null)
                return foundEl;
        }
        return null;
    }

    /**
     * Returns the first occurrence of a {@link XMLElement} with the given element name and attribute name-value pair, searches depth first.
     */
    public XMLElement getElement(final String elementName, final String attributeName, final String attributeValue) {
        if (mName.equals(elementName) && getAttribute(attributeName) != null
                && getAttribute(attributeName).equals(attributeValue)) {
            return this;
        }
        for (final XMLElement el : mChildren) {
            final XMLElement foundEl = el.getElement(elementName, attributeName, attributeValue);
            if (foundEl != null)
                return foundEl;
        }
        return null;
    }

    /**
     * Returns the name of this element
     */
    public String getName() {
        return mName;
    }

    /**
     * Returns the parent {@link XMLElement}
     */
    public XMLElement getParent() {
        return mParent;
    }

    /**
     * Sets the data of this element
     * @param data
     * @return Returns this object
     */
    public XMLElement setData(final String data) {
        this.mData = data;
        return this;
    }

    /**
     * Returns a XML String
     */
    @Override
    public String toString() {
        return toString("");
    }
}
