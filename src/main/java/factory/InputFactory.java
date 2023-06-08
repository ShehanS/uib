package factory;

import com.uib.api.inputComponents.*;
import com.uib.api.interfaces.IInputType;


public class InputFactory {
    public IInputType extractInput(String type) {

        if (type.equalsIgnoreCase("tableFill")) {
            return new TableFillComponent();
        }
        if (type.equalsIgnoreCase("textField")) {
            return new TextFieldComponent();
        }
        if (type.equalsIgnoreCase("inputParserSetting")) {
            return new InputParserSetting();
        }
        if (type.equalsIgnoreCase("switchButton")) {
            return new SwitchButtonComponent();
        }
        if (type.equalsIgnoreCase("dropDown")) {
            return new DropDownComponent();
        }
        if (type.equalsIgnoreCase("mappingTable")) {
            return new MappingTableComponent();
        }
        if (type.equalsIgnoreCase("outputParserSetting")) {
            return new OutputParserSettingComponent();
        }
        if (type.equalsIgnoreCase("groovyEditor")) {
            return new GroovyEditorComponent();
        }
        return new DefaultComponent();
    }
}
