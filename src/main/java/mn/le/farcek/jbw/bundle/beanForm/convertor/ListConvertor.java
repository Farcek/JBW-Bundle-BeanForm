/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.convertor;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.Collection;
import mn.le.farcek.beanform.ConvertorInterface;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.beanform.exception.IgnoreException;
import mn.le.farcek.common.bean.BeanProperty;
import mn.le.farcek.common.utils.FClassUtils;
import mn.le.farcek.jbw.api.action.IActionRequest;

/**
 *
 * @author Farcek
 */
public class ListConvertor implements ConvertorInterface {

    @Override
    public String toString(FormField field, Object value) {
        if (value == null) {
            return "";
        }
        Collection collection = (Collection) value;

        return Joiner.on(";").skipNulls().join(collection);
    }

    @Override
    public Object fromString(FormField field, IActionRequest request) throws ConvertorException, IgnoreException {
        BeanProperty beanProperty = field.getBeanProperty();
        String value = request.getParameter(beanProperty.getName());
        if (value == null) {
            return null;
        }
        try {
            return Splitter.on(";").trimResults().omitEmptyStrings().splitToList(value);
        } catch (Exception e) {
            throw new ConvertorException(e);
        }
    }

}
