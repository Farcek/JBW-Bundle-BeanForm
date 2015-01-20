/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import mn.le.farcek.beanform.BeanForm;
import mn.le.farcek.beanform.BeanFormLoader;
import mn.le.farcek.http.request.HttpRequest;
import mn.le.farcek.jbw.api.action.IActionRequest;
import mn.le.farcek.jbw.api.action.IActionRequestPart;

/**
 *
 * @author Farcek
 */
public interface BeanFormFactory {

    <T> BeanForm<T> factory(T beanObject);

    <T> BeanForm<T> factory(T beanObject, String name);

    BeanFormLoader factoryLoader(IActionRequest request);

    BeanFormLoader factoryLoader(HttpRequest request);
}
