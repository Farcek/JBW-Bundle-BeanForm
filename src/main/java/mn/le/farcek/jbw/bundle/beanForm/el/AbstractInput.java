/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.el;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import mn.le.farcek.beanform.BeanForm;
import mn.le.farcek.beanform.ConvertorInterface;
import mn.le.farcek.beanform.FormElement;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.FormInput;
import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.common.bean.BeanProperty;

/**
 *
 * @author Farcek
 */
public abstract class AbstractInput implements FormInput, JBWInput {

    private final FormField formField;

    public AbstractInput(FormField formField) {

        this.formField = formField;
    }

    @Override
    public boolean isMultipart() {
        return false;
    }

    @Override
    public boolean isReadonly() {
        return formField.isReadonly();
    }

    @Override
    public BeanForm<?> getBeanForm() {
        return formField.getBeanForm();
    }

    @Override
    public String getId() {
        return getBeanForm().getName() + "-" + getName();
    }

    @Override
    public String getName() {
        return formField.getBeanProperty().getName();
    }

    @Override
    public String getPlaceholder() {
        return getLabel();
    }

    public boolean isRequired() {

        NotNull annotation = formField.getBeanProperty().getAnnotation(NotNull.class);

        return annotation != null;
    }

    @Override
    public String getLabel() {
        return getBeanForm().getBeanClass().getName() + "." + getName();
    }

    @Override
    public BeanProperty getBeanProperty() {
        return formField.getBeanProperty();
    }

    @Override
    public ConvertorInterface getConvertor() throws ConvertorException {
        return formField.getConvertor();
    }

    @Override
    public String getCssClass() {
        return "";
    }

    @Override
    public Object getRawValue() {
        try {
            return getBeanProperty().getValue(getBeanForm().getBean());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            addError(ex.getMessage());
            return null;
        }
    }

    @Override
    public String getValue() {
        try {
            return getConvertor().toString(formField, getRawValue());
        } catch (ConvertorException ex) {
            addError(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<String> getErrors() {
        return formField.getErrors();
    }

    @Override
    public void addError(String error) {
        formField.addError(error);
    }

}
