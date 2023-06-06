package factory;

import com.uib.api.inputComponents.*;


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
        return new DefaultComponent();
    }
}
