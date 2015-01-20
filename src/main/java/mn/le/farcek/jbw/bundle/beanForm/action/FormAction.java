/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.action;

import mn.le.farcek.beanform.FormElement;

/**
 *
 * @author Farcek
 */
public class FormAction implements FormElement{

    @Override
    public String getViewName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean isMultipart() {
        return false;
    }
    
}
