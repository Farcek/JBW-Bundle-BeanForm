/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import com.google.inject.Inject;
import com.google.inject.Injector;
import mn.le.farcek.beanform.ElementFactory;
import mn.le.farcek.beanform.ElementFactoryInterface;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.ElementFactoryException;
import mn.le.farcek.beanform.managers.ElementFactoryManager;
import mn.le.farcek.common.bean.BeanProperty;

class JBWElementFactoryManager implements ElementFactoryManager {

    @Inject
    Injector injector;

    @Override
    public ElementFactoryInterface factory(FormField field) throws ElementFactoryException {
        BeanProperty beanProperty = field.getBeanProperty();
        ElementFactory annotation = beanProperty.getAnnotation(ElementFactory.class);

        if (annotation != null) {
            try {
                ElementFactoryInterface newInstance = annotation.value().newInstance();
                injector.injectMembers(newInstance);
                return newInstance;
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new ElementFactoryException(ex);
            }
        } else {
            return injector.getInstance(JBWElementFactory.class);
        }
    }

}
