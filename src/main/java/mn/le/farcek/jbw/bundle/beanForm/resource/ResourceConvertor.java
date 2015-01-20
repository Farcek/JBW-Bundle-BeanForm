/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.resource;

import com.google.inject.Inject;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mn.le.farcek.beanform.ConvertorInterface;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.beanform.exception.ConvertorException;
import mn.le.farcek.beanform.exception.IgnoreException;
import mn.le.farcek.http.request.HttpParam;
import mn.le.farcek.jbw.api.action.IActionRequest;
import mn.le.farcek.jbw.api.action.IActionRequestPart;
import mn.le.farcek.jbw.api.managers.IResourceManager;
import mn.le.farcek.jbw.api.resource.ResourceResult;

/**
 *
 * @author Farcek
 */
public class ResourceConvertor implements ConvertorInterface {

    @Inject
    IResourceManager resourceManager;

    @Override
    public String toString(FormField field, Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    @Override
    public Object fromString(FormField field, IActionRequest request) throws ConvertorException, IgnoreException {
        String n = field.getBeanProperty().getName();
        IActionRequestPart part = request.getPart(n);
        if (part == null) {
            throw new IgnoreException();
        }

        try {
            ResourceResult name = resourceManager.create(part);
            return name;
        } catch (IOException ex) {
            throw new ConvertorException(ex);
        }
    }

}
