/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.el;

import mn.le.farcek.beanform.BeanForm;
import mn.le.farcek.beanform.FormElement;

/**
 *
 * @author Farcek
 */
public interface JBWFormView extends FormElement{

    BeanForm<?> getBeanForm();

    String getCssClass();
}
