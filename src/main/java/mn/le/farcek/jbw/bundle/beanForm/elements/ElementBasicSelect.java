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
import mn.le.farcek.jbw.bundle.beanForm.el.SelectionItems;

/**
 *
 * @author Farcek
 */
public class ElementBasicSelect extends AbstractInput {
    private final SelectionItems selectionItems;

    public ElementBasicSelect(SelectionItems selectionItems, FormField formField) {
        super(formField);
        this.selectionItems = selectionItems;
    }
    

    @Override
    public String getViewName() {
        return "basicSelect";
    }

    public SelectionItems getSelectionItems() {
        return selectionItems;
    }

    public boolean isSelected(Map.Entry v){
        if(v == null) return false;
        return Objects.equals(v.getValue(), getRawValue());
    }

    
   

    

}
