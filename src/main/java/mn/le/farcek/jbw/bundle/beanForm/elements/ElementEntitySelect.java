/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.elements;

import java.util.Map;
import java.util.Objects;
import mn.le.farcek.jbw.bundle.beanForm.el.AbstractInput;
import mn.le.farcek.beanform.FormField;
import mn.le.farcek.common.utils.FClassUtils;
import mn.le.farcek.jbw.bundle.beanForm.DataSource;
import mn.le.farcek.jbw.bundle.beanForm.el.SelectionItems;

/**
 *
 * @author Farcek
 */
public class ElementEntitySelect extends AbstractInput {
    private final DataSource dataSource;

    public ElementEntitySelect(DataSource dataSource, FormField formField) {
        super(formField);
        this.dataSource = dataSource;
    }
    

    @Override
    public String getViewName() {
        return "entitySelect";
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public String getValueName(){
        Object v = getRawValue();
        if(v == null) return "";
        return v.toString();
    }

    

    
   

    

}
