Ext.ns('Ext.ux.form');

Ext.ux.form.SearchTripleFieldForm = Ext.extend(Ext.form.TripleTriggerField, {
    initComponent : function(){
        Ext.ux.form.SearchTripleFieldForm.superclass.initComponent.call(this);

    },
    afterRender : function(container, position){
        Ext.ux.form.SearchTripleFieldForm.superclass.afterRender.call(this);
        Ext.getCmp(this.name).getEl().dom.setAttribute('readOnly', true);
         
//        for (var ele=0;ele<this.windowLinkKeys.length;ele++) {
//           Ext.getCmp(this.windowLinkKeys[ele]).getEl().dom.setAttribute('readOnly', true);
//        }
    },

    validationEvent:false,
    validateOnBlur:false,
    trigger1Class:'x-form-clear-trigger',
    trigger2Class:'x-form-search-trigger',
    trigger3Class:'x-form-edit-trigger',
    hideTrigger1:true,
    width:180,
    window: false,
    clearButton: false,
    disabled: false,
    disabledButton1: false,
    disabledButton2: false,
    disabledButton3: false,
    windowLink: false,
    formName: false,
    url: false,
    windowLinkKeys: false,
    lookupFields: [],
    onTrigger1Click : function(){
           if (!this.disabled) {
               if (!this.disabledButton1) {
                  this.el.dom.value = '';
                  for (index=0;index < this.lookupFields.length;index++) {
                      Ext.getCmp(this.lookupFields[index]).setValue('');
                  };
                  this.customFunctionClear();
	          this.window.hide();
              }
           }  
    },

    onTrigger2Click : function(){
	    if (!this.disabled) {
               if (!this.disabledButton2) {
                   if (this.clearButton == true) {
                       this.triggers[0].show();
                   }
                   this.window.lookupFields=this.lookupFields;
                   this.window.display();
               }
            }
    },
    onTrigger3Click : function(){
	   if (!this.disabled) {
               if (!this.disabledButton3) {
                  var items=Ext.getCmp(this.formName).getForm().getValues();
                  var par={};
                  var link=this.windowLink;
                  for (i in items) {
                      par[i]=items[i];
                  }
                  var parmKey = '';
                  if (this.windowLinkKeys) {
                     for (i in this.windowLinkKeys) {
                        if (par[this.windowLinkKeys[i]]) {
                           parmKey=parmKey+'&'+this.windowLinkKeys[i]+'='+par[this.windowLinkKeys[i]];
                        }
                     }  
                  }
                  link=link+parmKey;
                  Ext.Ajax.request({
                     url: this.url,
                     method: 'POST',
		     params: par, 
                     success: function(response,request) {
                               window.location=link;
                              },
                     failure: function(response,request) {
                              Ext.Msg.alert('Status', Ext.util.JSON.decode(request.response.responseText).failure);
                              }
                    });
               }
          }
    },
    setDisabled: function(){
	   this.disabled=true;
	   this.disabledButton1=true;
	   this.disabledButton2=true;
	   this.disabledButton3=true;
           this.triggers[0].addClass('x-item-disabled');
           this.triggers[1].addClass('x-item-disabled');
           this.triggers[2].addClass('x-item-disabled');
    },
    setEnabled: function(){
	   this.disabled=false;
	   this.disabledButton1=false;
	   this.disabledButton2=false;
	   this.disabledButton3=false;
           this.triggers[0].removeClass('x-item-disabled');
           this.triggers[1].removeClass('x-item-disabled');
           this.triggers[2].removeClass('x-item-disabled');
    },
    setDisableButton1: function(){
	   this.disabledButton1=true;
           this.triggers[0].addClass('x-item-disabled');
    },
    setDisableButton2: function(){
	   this.disabledButton2=true;
           this.triggers[1].addClass('x-item-disabled');
    },
    setDisableButton3: function(){
	   this.disabledButton3=true;
           this.triggers[2].addClass('x-item-disabled');
    },
    setEnableButton1: function(){
	   this.disabledButton1=false;
           this.triggers[0].removeClass('x-item-disabled');
    },
    setEnableButton2: function(){
	   this.disabledButton2=false;
           this.triggers[1].removeClass('x-item-disabled');
    },
    setEnableButton3: function(){
	   this.disabledButton3=false;
           this.triggers[2].removeClass('x-item-disabled');
    },
    setValueCustom: function(r) {
           this.setValue(r);
    },
    customFunctionClear: function() {
    }
});
