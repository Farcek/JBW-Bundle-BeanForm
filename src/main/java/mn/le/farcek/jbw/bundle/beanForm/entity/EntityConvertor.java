/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.entity;

import com.google.inject.Inject;
import java.io.Serializable;
import javax.persistence.metamodel.Type;
import mn.le.farcek.beanform.ConvertorInterface;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.beanform.exception.IgnoreException;
import mn.le.farcek.common.bean.BeanProperty;
import mn.le.farcek.common.entity.FEntity;
import mn.le.farcek.common.utils.FClassUtils;
import mn.le.farcek.jbw.api.IService;
import mn.le.farcek.jbw.api.action.IActionRequest;

/**
 *
 * @author Farcek
 */
public class EntityConvertor implements ConvertorInterface {

    @Inject
    IService service;

    @Override
    public String toString(FormField field, Object value) {
        if (value == null) return null;
        return ((FEntity) value).getId().toString();

    }

    @Override
    public Object fromString(FormField field, IActionRequest request) throws ConvertorException, IgnoreException {
        BeanProperty beanProperty = field.getBeanProperty();

        String value = request.getParameter(beanProperty.getName());
        if (value == null)
            return null;

        Class<? extends FEntity> type = (Class<? extends FEntity>) beanProperty.getType();

        Type<?> pkType = service.getPkType(type);

        Object idValue = FClassUtils.fromString(pkType.getJavaType(), value);

        try {

            return service.entityById(type, (Serializable)idValue);

        } catch (Exception e) {
            throw new ConvertorException(e);
        }

    }

}
