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
