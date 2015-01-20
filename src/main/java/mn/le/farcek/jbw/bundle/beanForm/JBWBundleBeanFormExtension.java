/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm;

import com.google.common.collect.HashBiMap;
import com.google.inject.Inject;
import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.LocaleAware;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mn.le.farcek.beanform.BeanForm;
import mn.le.farcek.common.utils.FCollectionUtils;
import mn.le.farcek.jbw.api.managers.ITemplateManager;
import mn.le.farcek.jbw.api.template.ITemplate;

/**
 *
 * @author Farcek
 */
public class JBWBundleBeanFormExtension extends AbstractExtension {

    @Override
    public Map<String, Filter> getFilters() {
        return new FCollectionUtils.HashMapBuilder<String, Filter>()
                .put("beanForm", new BeanFormFilter())
                .build();
    }

    @Inject
    ITemplateManager templateManager;

    class BeanFormFilter implements Filter, LocaleAware {

        Locale locale;

        @Override
        public Object apply(Object input, Map<String, Object> args) {
            ITemplate factoryRenderer = templateManager.factoryRenderer("bundle://beanForm/form");

            StringWriter sw = new StringWriter();
            
            Map<String, Object> params = new HashMap<>();
            params.put("frm", getBeanForm(input));
            try {
                factoryRenderer.render(sw, params, locale);
            } catch (IOException ex) {
                return ex.getMessage();
            }
            return sw.toString();
        }

        @Override
        public List<String> getArgumentNames() {
            return Arrays.asList();
        }

        @Override
        public void setLocale(Locale locale) {
            this.locale = locale;
        }
        
        private BeanForm getBeanForm(Object bean){
            if(bean instanceof BeanForm){
                return (BeanForm) bean;
            }
            throw new RuntimeException("ddddddddddddddddddddd");
        }

    }

}
