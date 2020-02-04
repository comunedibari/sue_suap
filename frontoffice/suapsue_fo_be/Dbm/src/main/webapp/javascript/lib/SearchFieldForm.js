/*!
 * Ext JS Library 3.0+
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.ns('Ext.ux.form');

Ext.ux.form.SearchFieldForm = Ext.extend(Ext.form.TwinTriggerField, {
    initComponent : function(){
        Ext.ux.form.SearchFieldForm.superclass.initComponent.call(this);
        this.on('specialkey', function(f, e){
            if(e.getKey() == e.ENTER){
                this.onTrigger2Click();
            }
        }, this);
    },
    afterRender : function(container, position){
        Ext.ux.form.SearchFieldForm.superclass.afterRender.call(this);
        Ext.getCmp(this.name).getEl().dom.setAttribute('readOnly', true);
    },
    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    hideTrigger1:true,
    width:180,
    window: false,
    clearButton: false,
    disabled: false,
    lookupFields: [],
	
    onTrigger1Click : function(){
           if (!this.disabled) {
               this.el.dom.value = '';
               for (index in this.lookupFields) {
                    Ext.getCmp(this.lookupFields[index]).setValue('');
               };
	       this.window.hide();
           }
    },

    onTrigger2Click : function(){
           if (!this.disabled) {
               if (this.clearButton == true) {
                   this.triggers[0].show();
               }
	       if (this.window != false){	
	           this.window.display();
	       }
           } 
    },
    setDisabled: function(){
	   this.disabled=true;
           this.triggers[0].addClass('x-item-disabled');
           this.triggers[1].addClass('x-item-disabled');
    },
    setEnabled: function(){
	   this.disabled=false;
           this.triggers[0].removeClass('x-item-disabled');
           this.triggers[1].removeClass('x-item-disabled');
	}

});