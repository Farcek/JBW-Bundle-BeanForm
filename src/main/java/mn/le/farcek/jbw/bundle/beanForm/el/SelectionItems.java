/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.el;

import java.io.Serializable;
import java.util.HashMap;
import mn.le.farcek.common.NamedContainer;

/**
 *
 * @author Farcek
 */
public class SelectionItems extends HashMap<Serializable, Object> {
    
    public SelectionItems add(Serializable key, Object value){
        put(key, value);
        return this;
    } 

    public static SelectionItems fromEnum(Class<? extends Enum> enumClass) {
        SelectionItems items = new SelectionItems();

        for (Enum e : enumClass.getEnumConstants()) {
            items.put(e.name(), e);
        }
        
        return items;
    }
}
