/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.le.farcek.jbw.bundle.beanForm.el;

import java.io.Serializable;


public class SampleEntitySelectItem implements EntitySelectItem {

    private final Serializable id;
    private final String name;

    public SampleEntitySelectItem(Serializable id, String name) {
        this.id = id;
        this.name = name;
    }
    
    
    
    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
    
}
