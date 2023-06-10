package com.uib.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.uib.api.dtos.*;
import com.uib.api.dtos.returnDTOs.CheckBox.CheckBoxDTO;
import com.uib.api.dtos.returnDTOs.DropDown.DropDownDTO;
import com.uib.api.dtos.returnDTOs.Edge.EdgeDTO;
import com.uib.api.dtos.returnDTOs.GroovyEditor.GroovyEditorDTO;
import com.uib.api.dtos.returnDTOs.InputParserSetting.InputParserSettingDTO;
import com.uib.api.dtos.returnDTOs.MappingTable.MapAttribute;
import com.uib.api.dtos.returnDTOs.MappingTable.MappingTableDTO;
import com.uib.api.dtos.returnDTOs.OutputParserSetting.OutputParserSettingsDTO;
import com.uib.api.dtos.returnDTOs.SwitchButton.SwitchButtonDTO;
import com.uib.api.dtos.returnDTOs.TableFill.TableAttribute;
import com.uib.api.dtos.returnDTOs.TableFill.TableFillDTO;
import com.uib.api.dtos.returnDTOs.TextFiled.TextFieldDTO;
import com.uib.api.exceptions.FieldHasNull;
import com.uib.api.inputComponents.EdgeComponent;
import com.uib.api.interfaces.IInputType;
import com.uib.api.interfaces.IXMLGenerateService;
import com.uib.api.utilits.XMLUtility;
import factory.InputFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${edge.input.terminal}")
    private String[] inputTerminalAttrs;

    @Value("${edge.output.terminal}")
    private String[] outputTerminalAttrs;

    @Value("${node.attrs}")
    private String[] configEl;

    public XMLGenerateService(MappingService mappingService) {
        this.mappingService = mappingService;


    }


    @Override
    public void generate(Flow flow) throws ParserConfigurationException, TransformerException, FieldHasNull {
        List<Node> nodes = flow.getDiagram().getNodes();
        List<Edge> edges = flow.getDiagram().getEdges();
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        inputFactory = new InputFactory();
        boolean foundFlag = false;
        StringBuilder fileName = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = null;
        XMLUtility xmlUtility = new XMLUtility();
        StringBuilder generatedPath = new StringBuilder();
        generatedPath.append(flow.getProjectPath());
        generatedPath.append("/Generated Flows/");
        fileName.append(generatedPath);
        fileName.append(flow.getFileName());
        fileName.append(".mfl");
        try {
            Element rootElement = xmlUtility.createElement(doc, xmlUtility.MESSAGE_FLOW_NODE);
            Attr rootUUID = xmlUtility.createAttribute(rootElement, doc, xmlUtility.MESSAGE_FLOW_UUID_ATT, flow.getFileName());
            rootElement.setAttributeNode(rootUUID);
            Attr start = xmlUtility.createAttribute(rootElement, doc, xmlUtility.MESSAGE_FLOW_START_ATT, "auto");
            rootElement.setAttributeNode(start);
            Attr trace = xmlUtility.createAttribute(rootElement, doc, xmlUtility.MESSAGE_FLOW_TRACE_ATT, "on");
            rootElement.setAttributeNode(trace);
            Attr stats = xmlUtility.createAttribute(rootElement, doc, xmlUtility.MESSAGE_FLOW_STATS_ATT, "on");
            rootElement.setAttributeNode(stats);
            Attr label = xmlUtility.createAttribute(rootElement, doc, xmlUtility.MESSAGE_FLOW_LABEL_ATT, "MF_HTTP_ECHO");
            rootElement.setAttributeNode(label);
            Attr additionalInstance = xmlUtility.createAttribute(rootElement, doc, xmlUtility.MESSAGE_FLOW_ADDITIONAL_INSTANCES_ATT, "0");
            rootElement.setAttributeNode(additionalInstance);
            if (nodes.size() > 0) {
                for (Node node : nodes) {
                    Element nodeElement = xmlUtility.createElement(doc, xmlUtility.NODE);
                    Attr uuid = xmlUtility.createAttribute(nodeElement, doc, xmlUtility.NODE_UUID_ATT, node.getData().getUuid());
                    Attr implementer = xmlUtility.createAttribute(nodeElement, doc, xmlUtility.NODE_IMPLEMENTER, node.getData().getImplementer());
                    Attr type = xmlUtility.createAttribute(nodeElement, doc, xmlUtility.NODE_TYPE_ATT, node.getData().getNodeType());
                    nodeElement.setAttributeNode(uuid);
                    nodeElement.setAttributeNode(implementer);
                    nodeElement.setAttributeNode(type);
                    rootElement.appendChild(nodeElement);

                    if (node.getProperties() != null) {
                        List<Property> properties = node.getProperties();
                        objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
                        String jsonStringNodeTypes = objectWriter.writeValueAsString(node.getData().getNodeTypes());
                        List<NodeType> nodeTypes = objectMapper.readValue(jsonStringNodeTypes, new TypeReference<List<NodeType>>() {
                        });

                        if (nodeTypes.size() > 0) {
                            for (NodeType nodeType : nodeTypes) {
                                Element inputTerminal = xmlUtility.createElement(doc, xmlUtility.INPUT_TERMINAL);
                                Element outTerminal = xmlUtility.createElement(doc, xmlUtility.OUTPUT_TERMINAL);

                                if (nodeType.getTerminal().equals("in")) {
                                    Attr id = xmlUtility.createAttribute(inputTerminal, doc, xmlUtility.INPUT_TERMINAL_NAME_ATTR, nodeType.getTerminal());
                                    if (node.getProperties().size() > 0) {
                                        for (Property property : node.getProperties()) {
                                            for (String attr : inputTerminalAttrs) {
                                                if (property.getIdentity().equals(attr) && (property.getDefaultValue().toString() != null)) {
                                                    Attr at = xmlUtility.createAttribute(inputTerminal, doc, attr, property.getDefaultValue().toString());
                                                    inputTerminal.setAttributeNode(at);
                                                }
                                            }
                                        }
                                    }

                                    inputTerminal.setAttributeNode(id);
                                    nodeElement.appendChild(inputTerminal);

                                }
                                if (nodeType.getTerminal().equals("out")) {
                                    Attr id = xmlUtility.createAttribute(outTerminal, doc, xmlUtility.OUTPUT_TERMINAL_NAME_ATTR, nodeType.getTerminal());
                                    if (node.getProperties().size() > 0) {
                                        for (Property property : node.getProperties()) {
                                            for (String attr : outputTerminalAttrs) {
                                                if (property.getIdentity().equals(attr) && (property.getDefaultValue().toString() != null)) {
                                                    Attr at = xmlUtility.createAttribute(outTerminal, doc, attr, property.getDefaultValue().toString());
                                                    outTerminal.setAttributeNode(at);
                                                }
                                            }
                                        }
                                    }
                                    outTerminal.setAttributeNode(id);
                                    nodeElement.appendChild(outTerminal);

                                }
                                if (nodeType.getTerminal().equals("catch")) {
                                    Attr id = xmlUtility.createAttribute(outTerminal, doc, xmlUtility.OUTPUT_TERMINAL_NAME_ATTR, nodeType.getTerminal());
                                    if (node.getProperties().size() > 0) {
                                        for (Property property : node.getProperties()) {
                                            for (String attr : outputTerminalAttrs) {
                                                if (property.getIdentity().equals(attr) && (property.getDefaultValue().toString() != null)) {
                                                    Attr at = xmlUtility.createAttribute(outTerminal, doc, attr, property.getDefaultValue().toString());
                                                    outTerminal.setAttributeNode(at);
                                                }
                                            }
                                        }
                                    }
                                    outTerminal.setAttributeNode(id);
                                    nodeElement.appendChild(outTerminal);

                                }
                                if (nodeType.getTerminal().equals("error")) {
                                    Attr id = xmlUtility.createAttribute(outTerminal, doc, xmlUtility.OUTPUT_TERMINAL_NAME_ATTR, nodeType.getTerminal());
                                    if (node.getProperties().size() > 0) {
                                        for (Property property : node.getProperties()) {
                                            for (String attr : outputTerminalAttrs) {
                                                if (property.getIdentity().equals(attr) && (property.getDefaultValue().toString() != null)) {
                                                    Attr at = xmlUtility.createAttribute(outTerminal, doc, attr, property.getDefaultValue().toString());
                                                    outTerminal.setAttributeNode(at);
                                                }
                                            }
                                        }
                                    }
                                    outTerminal.setAttributeNode(id);
                                    nodeElement.appendChild(outTerminal);
                                }
                                if (nodeType.getTerminal().equals("failure")) {
                                    Attr id = xmlUtility.createAttribute(outTerminal, doc, xmlUtility.OUTPUT_TERMINAL_NAME_ATTR, nodeType.getTerminal());
                                    if (node.getProperties().size() > 0) {
                                        for (Property property : node.getProperties()) {
                                            for (String attr : outputTerminalAttrs) {
                                                if (property.getIdentity().equals(attr) && (property.getDefaultValue().toString() != null)) {
                                                    Attr at = xmlUtility.createAttribute(outTerminal, doc, attr, property.getDefaultValue().toString());
                                                    outTerminal.setAttributeNode(at);
                                                }
                                            }
                                        }
                                    }
                                    outTerminal.setAttributeNode(id);
                                    nodeElement.appendChild(outTerminal);
                                }

                            }
                        }

                        for (Property property : properties) {
                            IInputType extractor = inputFactory.extractInput(property.getType());
                            Object attribute = mappingService.inputComponentToXMLAttribute(extractor.extract(property));

                            if (attribute.getClass() == TextFieldDTO.class) {
                                TextFieldDTO textFieldDTO = (TextFieldDTO) attribute;
                                String propertyFlag = "";
                                for (String el : configEl) {
                                    if (el.equals(textFieldDTO.getName())) {
                                        Element additionalEl = xmlUtility.createElement(doc, textFieldDTO.getAdditionalAttribute());
                                        additionalEl.appendChild(xmlUtility.createTextNode(additionalEl, doc, textFieldDTO.getValue()));
                                        nodeElement.appendChild(additionalEl);
                                        propertyFlag = textFieldDTO.getName();
                                        break;
                                    }

                                }
                                if(!propertyFlag.equals(textFieldDTO.getName())) {
                                    Element propertyEl = xmlUtility.createElement(doc, xmlUtility.NODE_PROPERTY);
                                    Attr name = xmlUtility.createAttribute(rootElement, doc, xmlUtility.NODE_PROPERTY_NAME_ATTR, textFieldDTO.getName());
                                    Attr value = xmlUtility.createAttribute(rootElement, doc, xmlUtility.NODE_PROPERTY_VALUE_ATTR, textFieldDTO.getValue());
                                    propertyEl.setAttributeNode(name);
                                    propertyEl.setAttributeNode(value);
                                    nodeElement.appendChild(propertyEl);
                                }

                            }

                            if (attribute.getClass() == InputParserSettingDTO.class) {
                                InputParserSettingDTO inputParserSettingDTO = (InputParserSettingDTO) attribute;
                                Element inputParserSettingsElement = xmlUtility.createElement(doc, xmlUtility.NODE_INPUT_PARSER_SETTINGS);
                                Element inputParserName = xmlUtility.createElement(doc, xmlUtility.NODE_INPUT_PARSER_NAME);
                                if (inputParserSettingDTO.getInputParserSettings() != null) {
                                    inputParserName.appendChild(xmlUtility.createTextNode(inputParserSettingsElement, doc, inputParserSettingDTO.getInputParserSettings().getParserName()));
                                    inputParserSettingsElement.appendChild(inputParserName);
                                }

                                if (inputParserSettingDTO.getInputParserSettings() != null) {
                                    if (inputParserSettingDTO.getInputParserSettings().getProperty().size() > 0) {
                                        for (com.uib.api.dtos.returnDTOs.InputParserSetting.Property parserProperty : inputParserSettingDTO.getInputParserSettings().getProperty()) {
                                            Element propertyElement = xmlUtility.createElement(doc, xmlUtility.NODE_PROPERTY);
                                            Attr name = xmlUtility.createAttribute(rootElement, doc, xmlUtility.NODE_PROPERTY_NAME_ATTR, parserProperty.getName());
                                            Attr value = xmlUtility.createAttribute(rootElement, doc, xmlUtility.NODE_PROPERTY_VALUE_ATTR, parserProperty.getValue());
                                            propertyElement.setAttributeNode(name);
                                            propertyElement.setAttributeNode(value);
                                            inputParserSettingsElement.appendChild(propertyElement);
                                        }
                                        Element hadlingProperties = xmlUtility.createElement(doc, xmlUtility.NODE_HANDLING_PROPERTIES);
                                        Element handlingProperty = xmlUtility.createElement(doc, xmlUtility.NODE_HANDLING_PROPERTY);
                                        Attr name = xmlUtility.createAttribute(rootElement, doc, xmlUtility.NODE_PROPERTY_NAME_ATTR, inputParserSettingDTO.getInputParserSettings().getHadlingProperties().getHandlingProperty().getName());
                                        Attr value = xmlUtility.createAttribute(rootElement, doc, xmlUtility.NODE_PROPERTY_VALUE_ATTR, inputParserSettingDTO.getInputParserSettings().getHadlingProperties().getHandlingProperty().getValue());
                                        handlingProperty.setAttributeNode(name);
                                        handlingProperty.setAttributeNode(value);
                                        hadlingProperties.appendChild(handlingProperty);
                                        inputParserSettingsElement.appendChild(hadlingProperties);

                                    }
                                    nodeElement.appendChild(inputParserSettingsElement);
                                }
                            }

                            if (attribute.getClass() == MappingTableDTO.class) {
                                MappingTableDTO mappingTableDTO = (MappingTableDTO) attribute;
                                List<MapAttribute> mapAttributeList = mappingTableDTO.getMapAttributes();
                                if (mapAttributeList.size() > 0) {
                                    Element map = xmlUtility.createElement(doc, xmlUtility.MAPPING_NODE_MAP);
                                    for (MapAttribute mapAttribute : mapAttributeList) {
                                        Element execute = xmlUtility.createElement(doc, xmlUtility.EXECUTE);
                                        Attr operator = xmlUtility.createAttribute(execute, doc, xmlUtility.EXECUTE_OPERATOR_ATT, mapAttribute.getOperator());
                                        Attr from = xmlUtility.createAttribute(execute, doc, xmlUtility.EXECUTE_FROM_ATT, mapAttribute.getFrom());
                                        Attr to = xmlUtility.createAttribute(execute, doc, xmlUtility.EXECUTE_TO_ATT, mapAttribute.getTo());
                                        Attr value = xmlUtility.createAttribute(execute, doc, xmlUtility.EXECUTE_VALUE_ATT, mapAttribute.getValue());
                                        Attr typeE = xmlUtility.createAttribute(execute, doc, xmlUtility.EXECUTE_TYPE_ATT, mapAttribute.getType());
                                        execute.setAttributeNode(operator);
                                        execute.setAttributeNode(from);
                                        execute.setAttributeNode(to);
                                        execute.setAttributeNode(value);
                                        execute.setAttributeNode(typeE);
                                        map.appendChild(execute);
                                    }
                                    nodeElement.appendChild(map);
                                }
                            }

                            if (attribute.getClass() == OutputParserSettingsDTO.class) {
                                OutputParserSettingsDTO outputParserSettingsDTO = (OutputParserSettingsDTO) attribute;
                                Element outputParserSettings = xmlUtility.createElement(doc, xmlUtility.NODE_OUTPUT_PARSER_SETTINGS);
                                Element parserNameEl = xmlUtility.createElement(doc, xmlUtility.NODE_OUTPUT_PARSER_NAME);
                                if (!outputParserSettingsDTO.getParserName().equals("")) {
                                    org.w3c.dom.Node parserName = xmlUtility.createTextNode(parserNameEl, doc, outputParserSettingsDTO.getParserName());
                                    parserNameEl.appendChild(parserName);
                                    outputParserSettings.appendChild(parserNameEl);
                                    nodeElement.appendChild(outputParserSettings);
                                }
                            }

                            if (attribute.getClass() == TableFillDTO.class) {
                                TableFillDTO tableFillDTO = (TableFillDTO) attribute;
                                List<TableAttribute> tableAttributeList = tableFillDTO.getTableData().getAttributeList();
                                if (tableAttributeList.size() > 0) {
                                    for (TableAttribute tableAttribute : tableAttributeList) {
                                    }
                                }
                            }

                            if (attribute.getClass() == DropDownDTO.class) {
                                DropDownDTO dropDownDTO = (DropDownDTO) attribute;
                                Element dropDownproperty = xmlUtility.createElement(doc, xmlUtility.NODE_PROPERTY);
                                if ((dropDownDTO.getName() != null) && (dropDownDTO.getValue() != null)) {
                                    Attr name = xmlUtility.createAttribute(dropDownproperty, doc, xmlUtility.NODE_PROPERTY_NAME_ATTR, dropDownDTO.getName());
                                    Attr value = xmlUtility.createAttribute(dropDownproperty, doc, xmlUtility.NODE_PROPERTY_VALUE_ATTR, dropDownDTO.getValue());
                                    dropDownproperty.setAttributeNode(name);
                                    dropDownproperty.setAttributeNode(value);
                                    nodeElement.appendChild(dropDownproperty);
                                }
                            }

                            if (attribute.getClass() == CheckBoxDTO.class) {
                                CheckBoxDTO checkBoxDTO = (CheckBoxDTO) attribute;
                                Element checkBoxproperty = xmlUtility.createElement(doc, xmlUtility.NODE_PROPERTY);
                                Attr name = xmlUtility.createAttribute(checkBoxproperty, doc, xmlUtility.NODE_PROPERTY_NAME_ATTR, checkBoxDTO.getName());
                                Attr value = xmlUtility.createAttribute(checkBoxproperty, doc, xmlUtility.NODE_PROPERTY_VALUE_ATTR, checkBoxDTO.getValue());
                                checkBoxproperty.setAttributeNode(name);
                                checkBoxproperty.setAttributeNode(value);
                                nodeElement.appendChild(checkBoxproperty);
                            }

                            if (attribute.getClass() == SwitchButtonDTO.class) {
                                SwitchButtonDTO switchButtonDTO = (SwitchButtonDTO) attribute;
                                if (!switchButtonDTO.getName().equals("monitoring")) {
                                    Element switchButtonProperty = xmlUtility.createElement(doc, xmlUtility.NODE_PROPERTY);
                                    Attr name = xmlUtility.createAttribute(switchButtonProperty, doc, xmlUtility.NODE_PROPERTY_NAME_ATTR, switchButtonDTO.getName());
                                    Attr value = xmlUtility.createAttribute(switchButtonProperty, doc, xmlUtility.NODE_PROPERTY_VALUE_ATTR, switchButtonDTO.getValue());
                                    switchButtonProperty.setAttributeNode(name);
                                    switchButtonProperty.setAttributeNode(value);
                                    nodeElement.appendChild(switchButtonProperty);
                                }

                            }

                            if (attribute.getClass() == GroovyEditorDTO.class) {
                                GroovyEditorDTO groovyEditorDTO = (GroovyEditorDTO) attribute;
                                Element groovyCodeBlock = xmlUtility.createElement(doc, XMLUtility.GROOVY_SCRIPT);
                                org.w3c.dom.Node code = xmlUtility.createTextNode(groovyCodeBlock, doc, groovyEditorDTO.getScriptBlock());
                                groovyCodeBlock.appendChild(code);
                                nodeElement.appendChild(groovyCodeBlock);

                            }

                        }

                    }

                }
            }

            if (edges.size() > 0) {
                for (Edge edge : edges) {
                    EdgeComponent edgeComponent = new EdgeComponent();
                    EdgeDTO edgeDTO = edgeComponent.extractEdge(edge);
                    Element connection = xmlUtility.createElement(doc, XMLUtility.CONNECTION);
                    Attr sourceNode = xmlUtility.createAttribute(connection, doc, XMLUtility.SOURCE_NODE, edgeDTO.getSourceNode());
                    Attr targetNode = xmlUtility.createAttribute(connection, doc, XMLUtility.TARGET_NODE, edgeDTO.getTargetNode());
                    Attr sourceTerminal = xmlUtility.createAttribute(connection, doc, XMLUtility.SOURCE_TERMINAL, edgeDTO.getSourceTerminal());
                    Attr targetTerminal = xmlUtility.createAttribute(connection, doc, XMLUtility.TARGET_TERMINAL, edgeDTO.getTargetTerminal());
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
