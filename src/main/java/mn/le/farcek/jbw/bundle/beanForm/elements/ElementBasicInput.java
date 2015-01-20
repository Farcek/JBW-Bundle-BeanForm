/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.elements;

import mn.le.farcek.jbw.bundle.beanForm.el.AbstractInput;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.common.utils.FClassUtils;

/**
 *
 * @author Farcek
 */
public class ElementBasicInput extends AbstractInput {

    public ElementBasicInput(FormField formField) {
        super(formField);
    }

    @Override
    public String getViewName() {
        return "basicInput";
    }

    private String type;

    public String getType() {
        if (type == null) {

            Input annInput = getBeanProperty().getAnnotation(Input.class);
            if (annInput != null) {
                return annInput.type().name();
            }

            if (FClassUtils.instanceOf(Number.class, getBeanProperty().getType())) {
                type = "number";
            } else {
                type = "text";
            }
        }
        return type;
    }

}
