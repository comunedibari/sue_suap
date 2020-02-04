function Process(instanceId) {
    this.instanceId = instanceId;
}

Process.prototype.delete = function(successCallback) {
    var parameters = {};
    parameters.instanceId = this.instanceId;
    $.ajax({
        type: 'POST',
        url: path + '/workflow/instance/delete.htm',
        data: parameters,
        dataType: 'json',
        success: function(data) {
            var esito = data.success;
            var messaggio = data.msg;
            successCallback(esito, messaggio);
        }
    });
};