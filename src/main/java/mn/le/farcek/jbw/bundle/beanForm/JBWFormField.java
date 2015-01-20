/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import java.util.ArrayList;
import java.util.List;
import mn.le.farcek.beanform.BeanForm;
import mn.le.farcek.beanform.ConvertorInterface;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.BeanFormExeption;
import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.beanform.managers.ConvertorManager;
import mn.le.farcek.common.bean.BeanProperty;

class JBWFormField implements FormField {

    private final JBWBeanForm beanForm;
    private final BeanProperty beanProperty;
    private ConvertorInterface convertor;
    private List<String> errors;

    public JBWFormField(JBWBeanForm beanForm, BeanProperty beanProperty) {
        this.beanForm = beanForm;
        this.beanProperty = beanProperty;
    }

    @Override
    public ConvertorInterface getConvertor() throws ConvertorException {
        if (convertor == null) {
            ConvertorManager convertorManager = beanForm.getConvertorManager();
            if (convertorManager == null) {
                throw new BeanFormExeption("convertor manager is null");
            }
            convertor = convertorManager.factory(this);
        }
        return convertor;
    }

    @Override
    public BeanProperty getBeanProperty() {
        return beanProperty;
    }

    @Override
    public BeanForm<?> getBeanForm() {
        return beanForm;
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void addError(String error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(error);
    }

    @Override
    public boolean isReadonly() {
        
        return getBeanProperty().isWritable() == false;
    }

}
