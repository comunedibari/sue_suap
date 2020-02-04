
Ext.require(['*']);


/**
 * This function start the periodic update for the unavailableBeGRid
 * Also hide the PanelGrid if returned recordset is empty
 * 
 * @param panelGridId the Ext PanelGrid ID to update
 */
function startBeAvailabilityUpdaterTask(panelGridId, updateEachMillis) {

	var task = {
	    run: function(){
	    	Ext.getCmp(panelGridId).getStore().load({
	    		//Callback function after loaded records
	    		callback: function(records) {
	    			//Hide grid if empty records
	    			if (Ext.isEmpty(records)) {
	    				Ext.getCmp(panelGridId).setVisible(false);
	    			}
	    			else {
	    				if (Ext.getCmp(panelGridId).isHidden()) {
	    					Ext.getCmp(panelGridId).setVisible(true);
	    					Ext.getCmp(panelGridId).doComponentLayout();
	    				}
	    			}
	    		}
	    	});
	    },
	    interval: updateEachMillis
	};
	
	Ext.TaskManager.start(task);
}

/**
 * This function creates the Unavailavle BE services grid.
 * Default visibility of the created PanelGrid is false
 * 
 * @param renderElementId the html element ID to render the grid
 * @param panelGridId the Id assigned to the new grid
 */
function createUnavaliableBeGrid(renderElementId, panelGridId) {
	
	 // Set up a model to use in our Store
	 Ext.define('UnavailableBe', {
	     extend: 'Ext.data.Model',
	     fields: [
	         {name: 'logicalServiceName', type: 'string'},
	         {name: 'backEndURL',  type: 'string'},
	         {name: 'affectedNodes', type: 'string'}
	     ]
	 });
	
	 //Create a store for data
	 var beListStore = Ext.create('Ext.data.Store', {
	     model: 'UnavailableBe',
	     proxy: {
	         type: 'ajax',
	         url: 'json/updateBe.json',
	         reader: {
	             type: 'json'
	         }
	     },
	     autoLoad: true
	 });

//define test grid
Ext.create('Ext.grid.Panel', {
   visible: false,
   autoShow: false,
   autoRender: false,
   renderTo: renderElementId,
   loadMask: true,
   store: beListStore,
   id: panelGridId,
   height: 280,
   title: 'Servizi di Back-End non raggiungibili',
   columns: [
       {
           text: 'Nome servizio',
           width: 330,
           sortable: true,
           hideable: false,
           dataIndex: 'logicalServiceName'
       },
       {
           text: 'URL',
           width: 550,
           flex: 1,
           sortable: true,
           dataIndex: 'backEndURL',
           hidden: false
       },
       {
           text: 'Nodi interessati',
           width: 200,
           sortable: false,
           dataIndex: 'affectedNodes',
           hidden: false
       }
   ]
});
}		