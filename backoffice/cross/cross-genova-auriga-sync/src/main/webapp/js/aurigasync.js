$(function() {

    var wrapper;

    $(document).on({
        ajaxStart: function() {
            wrapper = new ajaxLoader($('body'));
        }
    });

    $('#synchronize').click(function() {
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        var deleteFile = $('#deleteFile').is(':checked');
        $.ajax({
            type: 'POST',
            url: '/aurigasync/start',
            data: {
                startDate: startDate,
                endDate: endDate,
                deleteFile: deleteFile
            },
            success: function(data) {
                wrapper.remove();
                $('#myModal .modal-body').text(data.message);
                $('#myModal').modal('show');
            },
            error: function(xhr, error, status) {
                wrapper.remove();
                $('#myModal .modal-body').text(status);
                $('#myModal').modal('show');
            }
        });
    });

    $('#simulate').click(function() {
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        var deleteFile = $('#deleteFile').is(':checked');
        $.ajax({
            type: 'POST',
            url: '/aurigasync/simulate',
            data: {
                startDate: startDate,
                endDate: endDate,
                deleteFile: deleteFile
            },
            success: function(data) {
                wrapper.remove();
                $('#myModal .modal-body').text(data.message);
                $('#myModal').modal('show');
            },
            error: function(xhr, error, status) {
                wrapper.remove();
                $('#myModal .modal-body').text(status);
                $('#myModal').modal('show');
            }
        });
    });
});