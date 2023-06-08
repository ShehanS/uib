package com.uib.api.interfaces;

import com.uib.api.exceptions.FieldHasNull;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface IXMLUtility {
    Element createElement(Document doc, String element) throws FieldHasNull;

    Attr createAttribute(Element element, Document doc, String name, String value) throws FieldHasNull;

    Node createTextNode(Element element, Document doc, String name) throws FieldHasNull;

}
