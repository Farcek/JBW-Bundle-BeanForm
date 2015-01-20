/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import com.google.inject.AbstractModule;
import mn.le.farcek.beanform.managers.ConvertorManager;
import mn.le.farcek.beanform.managers.ElementFactoryManager;

/**
 *
 * @author Farcek
 */
public class JBWBundleBeanFormInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(BeanFormFactory.class).to(JBWBeanFormFactory.class);
        bind(ConvertorManager.class).to(JBWConvertorManager.class);
        bind(ElementFactoryManager.class).to(JBWElementFactoryManager.class);
    }

}
