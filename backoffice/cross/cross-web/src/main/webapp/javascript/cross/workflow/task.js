function Task(taskid) {
    this.taskid = taskid;
}

Task.prototype.delete = function(successCallback) {
    var parameters = {};
    parameters.taskId = this.taskId;
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

Task.prototype.execute = function(parameters, successCallback) {
    var params = parameters;
    params.taskId = this.taskId;
    $.ajax({
        type: 'POST',
        url: path + '/workflow/task/complete.htm',
        data: params,
        dataType: 'json',
        success: function(data) {
            var esito = data.success;
            var messaggio = data.msg;
            successCallback(esito, messaggio);
        }
    });
};