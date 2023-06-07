package com.uib.api.services;

import com.uib.api.dtos.*;
import com.uib.api.dtos.returnDTOs.Edge.EdgeDTO;
import com.uib.api.dtos.returnDTOs.InputParserSetting.InputParserSettingDTO;
import com.uib.api.dtos.returnDTOs.TextFiled.TextFieldDTO;
import com.uib.api.inputComponents.EdgeComponent;
import com.uib.api.interfaces.IInputType;
import com.uib.api.interfaces.IXMLGenerateService;
import com.uib.api.utilits.XMLUtility;
import factory.InputFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

@Service
public class XMLGenerateService implements IXMLGenerateService {
    private DocumentBuilderFactory factory = null;
    private DocumentBuilder builder = null;
    private static final Logger logger = LoggerFactory.getLogger(XMLGenerateService.class);
    private MappingService mappingService;
    private InputFactory inputFactory = null;


    public XMLGenerateService(MappingService mappingService) {
        this.mappingService = mappingService;


    }


    @Override
    public void generate(Flow flow) throws ParserConfigurationException, TransformerException {
        List<Node> nodes = flow.getDiagram().getNodes();
        List<Edge> edges = flow.getDiagram().getEdges();
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        inputFactory = new InputFactory();
        StringBuilder fileName = new StringBuilder();
        fileName.append(flow.getFileName());
        fileName.append(".xml");
        try {
            Element rootElement = XMLUtility.createElement(doc, XMLUtility.MESSAGE_FLOW_NODE);
            Attr rootUUID = XMLUtility.createAttribute(rootElement, doc, XMLUtility.MESSAGE_FLOW_UUID_ATT, flow.getFileName());
            rootElement.setAttributeNode(rootUUID);
            Attr start = XMLUtility.createAttribute(rootElement, doc, XMLUtility.MESSAGE_FLOW_START_ATT, "auto");
            rootElement.setAttributeNode(start);
            Attr trace = XMLUtility.createAttribute(rootElement, doc, XMLUtility.MESSAGE_FLOW_TRACE_ATT, "on");
            rootElement.setAttributeNode(trace);
            Attr stats = XMLUtility.createAttribute(rootElement, doc, XMLUtility.MESSAGE_FLOW_STATS_ATT, "on");
            rootElement.setAttributeNode(stats);
            Attr label = XMLUtility.createAttribute(rootElement, doc, XMLUtility.MESSAGE_FLOW_LABEL_ATT, "MF_HTTP_ECHO");
            rootElement.setAttributeNode(label);
            Attr additionalInstance = XMLUtility.createAttribute(rootElement, doc, XMLUtility.MESSAGE_FLOW_ADDITIONAL_INSTANCES_ATT, "0");
            rootElement.setAttributeNode(additionalInstance);
            if (nodes.size() > 0) {
                for (Node node : nodes) {
                    Element nodeElement = XMLUtility.createElement(doc, XMLUtility.NODE);
                    Attr uuid = XMLUtility.createAttribute(nodeElement, doc, XMLUtility.NODE_UUID_ATT, node.getData().getId());
                    Attr implementer = XMLUtility.createAttribute(nodeElement, doc, XMLUtility.NODE_IMPLEMENTER, node.getData().getImplementer());
                    Attr type = XMLUtility.createAttribute(nodeElement, doc, XMLUtility.NODE_TYPE_ATT, node.getData().getNodeType());
                    nodeElement.setAttributeNode(uuid);
                    nodeElement.setAttributeNode(implementer);
                    nodeElement.setAttributeNode(type);
                    rootElement.appendChild(nodeElement);

                    if (node.getProperties() != null) {
                        List<Property> properties = node.getProperties();

                        List<NodeType> nodeTypes = node.getData().getNodeTypes();
                        if (nodeTypes.size() > 0) {
                            for (NodeType nodeType : nodeTypes) {
                                Element terminal = XMLUtility.createElement(doc, XMLUtility.OUTPUT_TERMINAL);
                                Attr id = XMLUtility.createAttribute(terminal, doc, XMLUtility.OUTPUT_TERMINAL_NAME_ATTR, nodeType.getTerminal());
                                terminal.setAttributeNode(id);
                                nodeElement.appendChild(terminal);
                            }
                        }

                        for (Property property : properties) {

                            IInputType extractor = inputFactory.extractInput(property.getType());
                            Object attribute = mappingService.inputComponentToXMLAttribute(extractor.extract(property));

                            if (attribute.getClass() == TextFieldDTO.class) {
                                TextFieldDTO textFieldDTO = (TextFieldDTO) attribute;
                                Element propertyElement = XMLUtility.createElement(doc, XMLUtility.NODE_PROPERTY);
                                Attr name = XMLUtility.createAttribute(rootElement, doc, XMLUtility.NODE_PROPERTY_NAME_ATTR, textFieldDTO.getName());
                                Attr value = XMLUtility.createAttribute(rootElement, doc, XMLUtility.NODE_PROPERTY_VALUE_ATTR, textFieldDTO.getValue());
                                propertyElement.setAttributeNode(name);
                                propertyElement.setAttributeNode(value);
                                nodeElement.appendChild(propertyElement);

                            }

                            if (attribute.getClass() == InputParserSettingDTO.class) {
                                InputParserSettingDTO inputParserSettingDTO = (InputParserSettingDTO) attribute;
                                Element inputParserSettingsElement = XMLUtility.createElement(doc, XMLUtility.NODE_INPUT_PARSER_SETTINGS);
                                Element inputParserName = XMLUtility.createElement(doc, XMLUtility.NODE_INPUT_PARSER_NAME);
                                inputParserName.appendChild(XMLUtility.createTextNode(inputParserSettingsElement, doc, inputParserSettingDTO.getInputParserSettings().getParserName()));
                                inputParserSettingsElement.appendChild(inputParserName);

                                if (inputParserSettingDTO.getInputParserSettings().getProperty().size() > 0) {
                                    for (com.uib.api.dtos.returnDTOs.InputParserSetting.Property parserProperty : inputParserSettingDTO.getInputParserSettings().getProperty()) {
                                        Element propertyElement = XMLUtility.createElement(doc, XMLUtility.NODE_PROPERTY);
                                        Attr name = XMLUtility.createAttribute(rootElement, doc, XMLUtility.NODE_PROPERTY_NAME_ATTR, parserProperty.getName());
                                        Attr value = XMLUtility.createAttribute(rootElement, doc, XMLUtility.NODE_PROPERTY_VALUE_ATTR, parserProperty.getValue());
                                        propertyElement.setAttributeNode(name);
                                        propertyElement.setAttributeNode(value);
                                        inputParserSettingsElement.appendChild(propertyElement);
                                    }
                                    Element hadlingProperties = XMLUtility.createElement(doc, XMLUtility.NODE_HANDLING_PROPERTIES);
                                    Element handlingProperty = XMLUtility.createElement(doc, XMLUtility.NODE_HANDLING_PROPERTY);
                                    Attr name = XMLUtility.createAttribute(rootElement, doc, XMLUtility.NODE_PROPERTY_NAME_ATTR, inputParserSettingDTO.getInputParserSettings().getHadlingProperties().getHandlingProperty().getName());
                                    Attr value = XMLUtility.createAttribute(rootElement, doc, XMLUtility.NODE_PROPERTY_VALUE_ATTR, inputParserSettingDTO.getInputParserSettings().getHadlingProperties().getHandlingProperty().getValue());
                                    handlingProperty.setAttributeNode(name);
                                    handlingProperty.setAttributeNode(value);
                                    hadlingProperties.appendChild(handlingProperty);
                                    inputParserSettingsElement.appendChild(hadlingProperties);

                                }
                                nodeElement.appendChild(inputParserSettingsElement);
                            }

                        }

                    }

                }
            }

            if (edges.size() > 0) {
                for (Edge edge : edges) {
                    EdgeComponent edgeComponent = new EdgeComponent();
                    EdgeDTO edgeDTO = edgeComponent.extractEdge(edge);
                    Element connection = XMLUtility.createElement(doc, XMLUtility.CONNECTION);
                    Attr sourceNode = XMLUtility.createAttribute(connection, doc, XMLUtility.SOURCE_NODE, edgeDTO.getSourceNode());
                    Attr targetNode = XMLUtility.createAttribute(connection, doc, XMLUtility.TARGET_NODE, edgeDTO.getTargetNode());
                    Attr sourceTerminal = XMLUtility.createAttribute(connection, doc, XMLUtility.SOURCE_TERMINAL, edgeDTO.getSourceTerminal());
                    Attr targetTerminal = XMLUtility.createAttribute(connection, doc, XMLUtility.TARGET_TERMINAL, edgeDTO.getTargetTerminal());
                    connection.setAttributeNode(sourceNode);
                    connection.setAttributeNode(targetNode);
                    connection.setAttributeNode(sourceTerminal);
                    connection.setAttributeNode(targetTerminal);
                    rootElement.appendChild(connection);
                }
            }

            doc.appendChild(rootElement);

        } catch (Exception e) {
            e.printStackTrace();
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File(fileName.toString()));
        transformer.transform(domSource, streamResult);
        System.out.println("Done creating XML File");
    }
}
