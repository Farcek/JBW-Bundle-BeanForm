/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import mn.le.farcek.beanform.BeanForm;
import mn.le.farcek.beanform.BeanFormLoader;
import mn.le.farcek.beanform.ElementFactoryInterface;
import mn.le.farcek.beanform.FormElement;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.BeanFormExeption;

import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.beanform.exception.ElementFactoryException;
import mn.le.farcek.beanform.exception.IgnoreException;
import mn.le.farcek.beanform.managers.ConvertorManager;
import mn.le.farcek.beanform.managers.ElementFactoryManager;
import mn.le.farcek.common.bean.BeanProperty;
import mn.le.farcek.http.request.HttpRequest;
import mn.le.farcek.http.request.HttpStringParam;
import mn.le.farcek.jbw.api.validation.ValidationManager;
import mn.le.farcek.jbw.bundle.beanForm.action.ElementSubmitButton;

class JBWBeanForm<T> implements BeanForm<T> {

    private String action;
    private final Class<T> beanClass;
    private final BeanProperty[] proportys;
    private T bean;

    public JBWBeanForm(T bean, BeanProperty[] proportys) {
        this.beanClass = (Class<T>) bean.getClass();
        this.proportys = proportys;
        this.bean = bean;
    }

    @Override
    public Class<T> getBeanClass() {
        return beanClass;
    }

    @Override
    public T getBean() {
        return bean;
    }

    @Override
    public void setBean(T bean) {
        this.bean = bean;
    }

    private List<FormField> fields;

    @Override
    public List<FormField> getFields() throws BeanFormExeption {
        if (fields == null) {
            fields = new ArrayList<>();
            for (BeanProperty prop : proportys) {
                JBWFormField formField = new JBWFormField(this, prop);
                fields.add(formField);
            }
        }
        return fields;
    }

    private List<FormElement> elements;

    @Override
    public List<FormElement> getElements() throws BeanFormExeption {
        if (elements == null) {
            elements = new ArrayList<>();
            for (FormField field : getFields()) {
                try {
                    ElementFactoryInterface elementFactory = getElementFactoryManager().factory(field);
                    FormElement element = elementFactory.factory(field);
                    elements.add(element);
                } catch (ElementFactoryException ex) {
                    throw new BeanFormExeption(ex);
                }
            }
        }
        return elements;
    }

    private List<FormElement> bottomActions;

    public List<FormElement> getBottomActions() throws BeanFormExeption {
        if (bottomActions == null) {
            bottomActions = new ArrayList<>();

            bottomActions.add(new ElementSubmitButton(this));
        }
        return bottomActions;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    

    private ConvertorManager convertorManager;

    @Override
    public ConvertorManager getConvertorManager() {
        return convertorManager;
    }

    public void setConvertorManager(ConvertorManager convertorManager) {
        this.convertorManager = convertorManager;
    }

    private ElementFactoryManager elementFactoryManager;

    @Override
    public ElementFactoryManager getElementFactoryManager() {
        return elementFactoryManager;
    }

    public void setElementFactoryManager(ElementFactoryManager elementFactoryManager) {
        this.elementFactoryManager = elementFactoryManager;
    }

    @Override
    public T load(BeanFormLoader loader) {
        T b = getBean();
        for (FormField field : getFields()) {
            BeanProperty beanProperty = field.getBeanProperty();
            if (beanProperty.isWritable()) {

                try {
                    Object value = loader.loadPropery(field);
                    beanProperty.setValue(b, value);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    field.addError(ex.getMessage());
                } catch (IgnoreException ex) {

                }
            }
        }
        return b;
    }

//    @Override
//    public T loadRequest(HttpRequest request) {
//        T b = getBean();
//        for (FormField field : getFields()) {
//            BeanProperty beanProperty = field.getBeanProperty();
//            HttpStringParam valueString = request.getString(beanProperty.getName());
//
//            try {
//                Object value = field.getConvertor().fromString(valueString != null ? valueString.getValue() : null);
//                beanProperty.setValue(b, value);
//            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ConvertorException ex) {
//                field.addError(ex.getMessage());
//            }
//        }
//        return b;
//    }
    @Override
    public String toString() {
        return "JBWBeanForm{" + "beanClass=" + beanClass + ", proportys=" + Arrays.toString(proportys) + '}';
    }

    @Override
    public String getName() {
        return getBeanClass().getSimpleName();
    }

    private ValidationManager validationManager;

    public ValidationManager getValidationManager() {
        return validationManager;
    }

    public void setValidationManager(ValidationManager validationManager) {
        this.validationManager = validationManager;
    }

    private List<String> errors;

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
    public boolean valid(Class... groups) {
        Set<ConstraintViolation<T>> validates = validationManager.validate(getBean(), groups);
        if (validates.isEmpty()) {
            return true;
        }

        errors = new ArrayList<>();
        for (ConstraintViolation<T> validate : validates) {
            if (addFieldError(validate) == false) {
                errors.add(validate.getMessage());
            }
        }

        return false;
    }

    private boolean addFieldError(ConstraintViolation<T> validate) {

        String path = validate.getPropertyPath().toString();

//        System.out.println("validate.getPropertyPath()=" + validate.getPropertyPath());
//        System.out.println("validate.cls=" + validate.getPropertyPath().getClass());
//        System.out.println("validate.cls=" + validate.getMessage());
//        System.out.println("validate.cls=" + validate.getMessageTemplate());
        for (FormField field : getFields()) {
            if (path.equals(field.getBeanProperty().getName())) {
                field.addError(validate.getMessage());
                return true;
            }
        }
        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="method">
    private String method;

    public String getMethod() {
        if (method == null) {
            method = "POST";
        }
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="enctype">
    private String enctype;

    public String getEnctype() {
        if (enctype == null) {
            for (FormElement e : getElements()) {
                if (e.isMultipart()) {
                    enctype = "multipart/form-data";
                    break;
                }
            }
            if (enctype == null) {
                enctype = "application/x-www-form-urlencoded";
            }
        }
        return enctype;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }
    //</editor-fold>

}
