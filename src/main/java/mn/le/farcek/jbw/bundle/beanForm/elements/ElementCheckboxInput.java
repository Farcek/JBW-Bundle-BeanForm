/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.elements;

import mn.le.farcek.jbw.bundle.beanForm.el.AbstractInput;
import mn.le.farcek.beanform.FormField;


/**
 *
 * @author Farcek
 */
public class ElementCheckboxInput extends AbstractInput {

    public ElementCheckboxInput(FormField formField) {
        super(formField);
    }

    @Override
    public String getViewName() {
        return "checkboxInput";
    }

    
   

    

}
