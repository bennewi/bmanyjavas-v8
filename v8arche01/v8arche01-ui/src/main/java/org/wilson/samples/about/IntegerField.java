package org.wilson.samples.about;

import com.vaadin.data.HasValue;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.TextField;

public class IntegerField extends TextField implements HasValue.ValueChangeListener<String> {

    public String lastValue;

    public IntegerField() {
        setValueChangeMode(ValueChangeMode.EAGER);
        addValueChangeListener(this);
        lastValue = "";
    }

    @Override
    public void valueChange(ValueChangeEvent<String> event) {
        String text = (String) event.getValue();
            try {
                new Integer(text);
                lastValue = text;
            } catch (NumberFormatException e) {
                setValue(lastValue);
        }
    }
}
