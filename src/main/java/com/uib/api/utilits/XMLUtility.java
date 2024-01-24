package com.uib.api.utilits;


import com.uib.api.exceptions.FieldHasNull;
import com.uib.api.interfaces.IXMLUtility;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLUtility implements IXMLUtility {

    public final static String MESSAGE_FLOW_NODE = "MessageFlow";
    public final static String MESSAGE_FLOW_UUID_ATT = "uuid";
    public final static String MESSAGE_FLOW_START_ATT = "start";
    public final static String MESSAGE_FLOW_TRACE_ATT = "trace";
    public final static String MESSAGE_FLOW_LABEL_ATT = "label";
    public final static String MESSAGE_FLOW_STATS_ATT = "stats";
    public final static String MESSAGE_FLOW_ADDITIONAL_INSTANCES_ATT = "additionalInstances";
    public final static String NODE = "Node";
    public final static String NODE_IMPLEMENTER = "implementer";
    public final static String NODE_UUID_ATT = "uuid";
    public final static String NODE_TYPE_ATT = "type";
    public final static String NODE_OUTPUT_TERMINAL = "OutputTerminal";
    public final static String OUTPUT_TERMINAL_ID_ATT = "id";
    public final static String NODE_PROPERTY = "Property";
    public final static String NODE_PROPERTY_NAME_ATTR = "name";
    public final static String NODE_PROPERTY_VALUE_ATTR = "value";
    public final static String NODE_INPUT_PARSER_SETTINGS = "InputParserSettings";
    public final static String NODE_OUTPUT_PARSER_SETTINGS = "OutputParserSettings";
    public final static String NODE_INPUT_PARSER_NAME = "ParserName";
    public final static String NODE_OUTPUT_PARSER_NAME = "ParserName";

    public final static String NODE_HANDLING_PROPERTIES = "HadlingProperties";
    public final static String NODE_HANDLING_PROPERTY = "HadlingProperty";

    public final static String INPUT_TERMINAL = "InputTerminal";
    public final static String INPUT_TERMINAL_NAME_ATTR = "id";
    public final static String OUTPUT_TERMINAL = "OutputTerminal";
    public final static String OUTPUT_TERMINAL_NAME_ATTR = "id";
    public final static String MAPPING_NODE_MAP = "Map";

    public final static String EXECUTE = "Execute";

    public final static String EXECUTE_OPERATOR_ATT = "operator";

    public final static String EXECUTE_FROM_ATT = "from";

    public final static String EXECUTE_TO_ATT = "to";

    public final static String EXECUTE_TYPE_ATT = "type";

    public final static String EXECUTE_VALUE_ATT = "value";

    public static final String HTTP_REQUEST_HEADER = "HTTPRequestHeaders";

    public static final String HEADER = "Header";

    public static final String HEADER_NAME_ATT = "name";

    public static final String HEADER_VALUE_ATT = "value";

    public static final String GROOVY_SCRIPT = "GroovyScript";

    public static final String CONNECTION = "Connection";

    public static final String SOURCE_NODE = "sourceNode";

    public static final String TARGET_NODE = "targetNode";

    public static final String SOURCE_TERMINAL = "sourceTerminal";

    public static final String TARGET_TERMINAL = "targetTerminal";

    public static final String CAPTURE_ELEMENT = "Capture";

    public static final String MONITORING = "monitoring";


    public Element createElement(Document doc, String element) throws FieldHasNull {
        if (element != null) {
            Element el = doc.createElement(element);
            return el;
        } else {
            throw new FieldHasNull("Cannot empty required field");
        }
    }

    public Attr createAttribute(Element element, Document doc, String name, String value) throws FieldHasNull {
        if (value != null) {
            Attr attr = doc.createAttribute(name);
            attr.setValue(value);
            return attr;
        } else {
            throw new FieldHasNull("Cannot empty required field");
        }
    }

    public Node createTextNode(Element element, Document doc, String name) throws FieldHasNull {
        if (name != null) {
            Node textNode = element.appendChild(doc.createTextNode(name));
            return textNode;
        } else {
            throw new FieldHasNull("Cannot empty required field");
        }
    }

}
