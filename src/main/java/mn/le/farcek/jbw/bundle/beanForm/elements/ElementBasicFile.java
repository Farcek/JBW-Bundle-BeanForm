/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.elements;

import mn.le.farcek.beanform.FormField;
import mn.le.farcek.jbw.bundle.beanForm.el.AbstractInput;

/**
 *
 * @author Farcek
 */
public class ElementBasicFile extends AbstractInput{

    public ElementBasicFile(FormField formField) {
        super(formField);
    }

    @Override
    public boolean isMultipart() {
        return true;
    }
    
    

    @Override
    public String getViewName() {
        return "basicFile";
    }
    
}
