package com.uib.api.dtos.returnDTOs.InputParserSetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputParserSettings {
    public String parserName;
    public ArrayList<Property> property;
    public HadlingProperties hadlingProperties;
}
