/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mn.le.farcek.beanform.BeanForm;
import mn.le.farcek.beanform.BeanFormLoader;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.beanform.exception.IgnoreException;
import mn.le.farcek.beanform.managers.ConvertorManager;
import mn.le.farcek.beanform.managers.ElementFactoryManager;
import mn.le.farcek.common.bean.BeanProperty;
import mn.le.farcek.common.bean.BeanPropertyFactory;
import mn.le.farcek.common.bean.NoSuchPropertyException;
import mn.le.farcek.http.request.HttpRequest;
import mn.le.farcek.http.request.HttpRequestBuilder;
import mn.le.farcek.jbw.api.action.IActionRequest;
import mn.le.farcek.jbw.api.validation.ValidationManager;

class JBWBeanFormFactory implements BeanFormFactory {

    private final ConvertorManager convertorManager;
    private final ElementFactoryManager elementFactoryManager;
    private final ValidationManager validationManager;

    @Inject
    public JBWBeanFormFactory(ConvertorManager convertorManager, ElementFactoryManager elementFactoryManager, ValidationManager validationManager) {
        this.convertorManager = convertorManager;
        this.elementFactoryManager = elementFactoryManager;
        this.validationManager = validationManager;
    }

    @Override
    public BeanForm<?> factory(Object beanObject) {
        return factory(beanObject, "default");
    }

    public Form getForm(Class<?> fromClass, String name) {
        Form annFrm = fromClass.getAnnotation(Form.class);
        if (annFrm != null && annFrm.name().equals(name)) {
            return annFrm;
        }

        Forms annFrms = fromClass.getAnnotation(Forms.class);
        if (annFrms != null) {
            for (Form frm : annFrms.value()) {
                if (frm != null && frm.name().equals(name)) {
                    return frm;
                }
            }
        }

        return null;

    }

    @Override
    public BeanForm<?> factory(Object beanObject, String name) {
        if (beanObject == null) {
            throw new NullPointerException();
        }
        Class<?> cls = beanObject.getClass();

        Form form = getForm(cls, name);
        List<BeanProperty> prs = new ArrayList<>();
        if (form != null) {

            for (String p : form.fields()) {
                try {
                    BeanProperty factoryProperty = BeanPropertyFactory.factoryProperty(cls, p);
                    prs.add(factoryProperty);
                } catch (NoSuchPropertyException ex) {
                }
            }
        }
        if (prs.isEmpty()) {
            prs.addAll(Arrays.asList(BeanPropertyFactory.findAllProperys(cls)));
        }

        JBWBeanForm frm = new JBWBeanForm(beanObject, prs.toArray(new BeanProperty[]{}));
        frm.setConvertorManager(convertorManager);
        frm.setElementFactoryManager(elementFactoryManager);
        frm.setValidationManager(validationManager);
        return frm;
    }

    @Override
    public BeanFormLoader factoryLoader(IActionRequest request) {
        return new ActionRequestLoader(request);
    }

    @Override
    public BeanFormLoader factoryLoader(HttpRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class ActionRequestLoader implements BeanFormLoader {

        private final IActionRequest request;

        public ActionRequestLoader(IActionRequest request) {
            this.request = request;
        }

        @Override
        public Object loadPropery(FormField field) throws IgnoreException {
            //BeanProperty beanProperty = field.getBeanProperty();
            //String parameters[] = request.getParameterValues(beanProperty.getName());
            try {
                return field.getConvertor().fromString(field, request);
            } catch (ConvertorException ex) {
                field.addError(ex.getMessage());
                return null;
            }
        }

    }

}
