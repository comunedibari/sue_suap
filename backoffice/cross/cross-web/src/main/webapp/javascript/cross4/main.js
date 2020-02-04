var EventManager = {};

function pollNotifiche() {
    setTimeout(function() {
        aggiornaNotifiche();
    }, 60000);
}

function aggiornaNotifiche() {
    $.ajax({
        url: path + '/workflow/notifiche/ajax/count.htm',
        type: "POST",
        success: function(data) {
            if (data.rows.length > 0) {
                $('.notifiche-count').empty();
                $('.dropdown-menu-notifiche').empty();
                $('.notifiche-count').text(data.total);

                for (var i = 0; i < data.rows.length; i++) {
                    var task = data.rows[i];
                    var row = $('<li />');
                    var item = $('<a >').attr({
                        href: path + '/workflow/notifiche/event.htm?taskId=' + task.taskId
                    });

                    var itemTitle = $('<span />').addClass('msg-title');
                    itemTitle.text(task.variables['titoloNotifica']);

                    var itemText = $('<div />').addClass('msg-body');
                    $(itemText).text(task.variables['testoMessaggio']);

                    $(item).append(itemTitle).append(itemText);

                    $(row).append(item);
                    $('.dropdown-menu-notifiche').append(row);
                }
            }
        },
        dataType: "json",
        complete: pollNotifiche,
        timeout: 5000
    });
}

$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
    _title: function(title) {
        var $title = this.options.title || '&nbsp;';
        if (("title_html" in this.options) && this.options.title_html === true) {
            title.html($title);
        } else {
            title.text($title);
        }
    }
}));

$(function() {
    aggiornaNotifiche();
});

$.extend($.gritter.options, {
    position: 'bottom-right'
});