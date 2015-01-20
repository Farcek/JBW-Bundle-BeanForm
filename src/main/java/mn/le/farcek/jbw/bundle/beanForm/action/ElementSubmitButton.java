/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.action;

import mn.le.farcek.beanform.BeanForm;
import mn.le.farcek.jbw.bundle.beanForm.el.JBWFormView;

/**
 *
 * @author Farcek
 */
public class ElementSubmitButton implements JBWFormView {

    private final BeanForm<?> beanForm;

    public ElementSubmitButton(BeanForm<?> beanForm) {
        this.beanForm = beanForm;
    }

    @Override
    public BeanForm<?> getBeanForm() {
        return beanForm;
    }

    @Override
    public String getCssClass() {
        return "";
    }

    @Override
    public String getViewName() {
        return "submitButton";
    }

    public String getId() {
        return beanForm.getName() + "-btn-submit";
    }

    public String getLabel() {
        return getBeanForm().getBeanClass().getName() + ".btnSubmit";
    }

    @Override
    public boolean isMultipart() {
        return false;
    }

    
    
}
