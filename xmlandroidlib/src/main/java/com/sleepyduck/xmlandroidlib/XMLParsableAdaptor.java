package com.sleepyduck.xmlandroidlib;


import java.io.Serializable;


/**
 * Adaptor to the {@link IXMLParsable} Interface
 *
 * @author Fredrik Metcalf
 */
public class XMLParsableAdaptor implements IXMLParsable, Serializable {
    private static final long serialVersionUID = 3650027401908677306L;

    @Override
    public void putAttributes(final XMLElement element) {}

    @Override
    public XMLElement toXMLElement() {
        XMLElement el = new XMLElement(getClass().getSimpleName());
        putAttributes(el);
        return el;
    }

    /**
     * @return the XML {@link String} representation from {@link XMLElementFactory}
     */
    @Override
    public String toString() {
        return toXMLElement().toString();
    }

}
