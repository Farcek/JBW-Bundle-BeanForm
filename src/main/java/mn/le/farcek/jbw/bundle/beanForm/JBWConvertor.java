/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import java.util.Arrays;
import mn.le.farcek.beanform.ConvertorInterface;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.common.bean.BeanProperty;
import mn.le.farcek.common.utils.FBeanPropertyUtils;
import mn.le.farcek.common.utils.FClassUtils;
import mn.le.farcek.http.request.HttpParam;
import mn.le.farcek.http.request.HttpStringParam;
import mn.le.farcek.jbw.api.action.IActionRequest;

/**
 *
 * @author Farcek
 */
class JBWConvertor implements ConvertorInterface {

    @Override
    public String toString(FormField field, Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    @Override
    public Object fromString(FormField field, IActionRequest request) throws ConvertorException {
        BeanProperty beanProperty = field.getBeanProperty();
        String value = request.getParameter(beanProperty.getName());
        if (value == null) {
            return null;
        }
        Class<?> type = beanProperty.getType();
        try {
            

            return FClassUtils.fromString(type, value);

        } catch (Exception e) {
            throw new ConvertorException(e);
        }
    }

}
