function changePage(page, recordsPerPage) {
    var pnumber = page || 1;
    var recordsPerPage = $('#filmsPerPage').val();

    var command = $('#commandPart').val() === "" ?
        'controller?command=films-page' : $('#commandPart').val();
    $.ajax({
        type: 'GET',
        url: command + '&page=' + pnumber + '&filmsPerPage=' + recordsPerPage,
        success: function (data) {
            var jqObj = jQuery(data);
            var films = jqObj.find("#viewport").children();
            $("#viewport").empty();
            $("#viewport").append(films);
        }
    })
}

$(document).ready(function () {
    var recordsPerPage = $('#filmsPerPage').val();
    $("#selectPerPage").change(function () {
        $('#filmsPerPage').val($(this).val());
        var records = $('#filmsCount').val();
        recordsPerPage = $(this).val() === null ? $('#filmsCount').val() : $(this).val();
        // recordsPerPage = $(this).val();
        var pages = Math.ceil(records / recordsPerPage);
        $('#pagination-films').twbsPagination('destroy');
        $('#pagination-films').twbsPagination({
                totalPages: pages,
                visiblePages: pages,
                onPageClick: function (event, page) {
                    $('#viewport').html(changePage(page, recordsPerPage));
                }
            }
        );
    }).change();
});

$(document).on('click', '.glyphicon-trash', function () {
    $.ajax({
        type: 'POST',
        data: $(this).closest('form').serialize(),
        url: '/controller',
        success: function (data) {
            var pnumber = $('.active a').html() || 1;
            var records = $('#filmsCount').val();
            var recordsPerPage = $('#filmsPerPage').val() === null ? $('#filmsCount').val() : $('#filmsPerPage').val();
            var pages = Math.ceil(records / recordsPerPage);
            $('#pagination-films').twbsPagination('destroy');
            $('#pagination-films').twbsPagination({
                    totalPages: pages,
                    visiblePages: pages,
                    onPageClick: function (event, page) {
                        $('#viewport').html(changePage(page, recordsPerPage));
                    }
                }
            );
            changePage(pnumber, recordsPerPage);
        }
    })
});

$(document).on('click', '.glyphicon-pencil', function () {
    $('.button-checkbox').each(function () {

        // Settings
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                on: {
                    icon: 'glyphicon glyphicon-check'
                },
                off: {
                    icon: 'glyphicon glyphicon-unchecked'
                }
            };

        // Event Handlers
        $button.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });
        $checkbox.on('change', function () {
            updateDisplay();
        });

        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            }
            else {
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }

        // Initialization
        function init() {
            updateDisplay();

            // Inject the icon if applicable
            if ($button.find('.state-icon').length == 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i> ');
            }
        }

        init();
    });

});