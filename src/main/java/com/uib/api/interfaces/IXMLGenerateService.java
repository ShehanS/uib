package com.uib.api.interfaces;

import com.uib.api.dtos.Flow;
import com.uib.api.exceptions.FieldHasNull;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public interface IXMLGenerateService {
    void generate(Flow flow) throws ParserConfigurationException, TransformerException, FieldHasNull;
}
