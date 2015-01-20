/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import mn.le.farcek.beanform.ElementFactoryInterface;
import mn.le.farcek.beanform.FormElement;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.jbw.bundle.beanForm.resource.Resource;
import mn.le.farcek.common.bean.BeanProperty;
import mn.le.farcek.common.entity.FEntity;
import mn.le.farcek.common.utils.FClassUtils;
import mn.le.farcek.jbw.bundle.beanForm.el.SelectionItems;
import mn.le.farcek.jbw.bundle.beanForm.elements.ElementBasicFile;
import mn.le.farcek.jbw.bundle.beanForm.elements.ElementBasicInput;
import mn.le.farcek.jbw.bundle.beanForm.elements.ElementBasicSelect;
import mn.le.farcek.jbw.bundle.beanForm.elements.ElementCheckboxInput;
import mn.le.farcek.jbw.bundle.beanForm.elements.ElementEntitySelect;

/**
 *
 * @author Farcek
 */
public class JBWElementFactory implements ElementFactoryInterface {

    @Override
    public FormElement factory(FormField field) {
        BeanProperty beanProperty = field.getBeanProperty();
        Class<?> type = beanProperty.getType();

        if (String.class.equals(type)) {
            Resource annotation = beanProperty.getAnnotation(Resource.class);

            if (annotation != null)
                return new ElementBasicFile(field);
        }

        if (FClassUtils.isBooleanClass(type))
            return new ElementCheckboxInput(field);
        if (type.isEnum())
            return new ElementBasicSelect(SelectionItems.fromEnum((Class<? extends Enum>) type), field);

        if (FClassUtils.instanceOf(FEntity.class, type)) {
            DataSource ds = field.getBeanProperty().getAnnotation(DataSource.class);
            if (ds != null) return new ElementEntitySelect(ds, field);
        }

        return new ElementBasicInput(field);
    }

}
