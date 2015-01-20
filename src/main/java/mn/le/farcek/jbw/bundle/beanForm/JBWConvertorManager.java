/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import java.util.Collection;
import mn.le.farcek.beanform.Convertor;
import mn.le.farcek.beanform.ConvertorInterface;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.beanform.managers.ConvertorManager;
import mn.le.farcek.common.bean.BeanProperty;
import mn.le.farcek.common.utils.FClassUtils;
import mn.le.farcek.jbw.bundle.beanForm.convertor.ListConvertor;
import mn.le.farcek.jbw.bundle.beanForm.resource.Resource;
import mn.le.farcek.jbw.bundle.beanForm.resource.ResourceConvertor;

class JBWConvertorManager implements ConvertorManager {

    @Inject
    Injector injector;

    @Override
    public ConvertorInterface factory(FormField field) throws ConvertorException {
        BeanProperty beanProperty = field.getBeanProperty();
        Convertor annotation = beanProperty.getAnnotation(Convertor.class);
        if (annotation != null) {
            try {

                return injector.getInstance(annotation.value());
            } catch (ConfigurationException | ProvisionException ex) {
                throw new ConvertorException(ex);
            }
        }

        Class<?> type = beanProperty.getType();
        if (FClassUtils.instanceOf(Collection.class, type)) {
            return new ListConvertor();
        }

        if (String.class.equals(beanProperty.getType())) {
            Resource ann = beanProperty.getAnnotation(Resource.class);
            if (ann != null) {
                return injector.getInstance(ResourceConvertor.class);
            }
        }

        return new JBWConvertor();
    }

}
