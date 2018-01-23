$(document).ready(function () {
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
                    // if (page !== 1) {
                    //     $('#selectPerPage').prop('disabled', true);
                    // } else {
                    //     $('#selectPerPage').prop('disabled', false);
                    // }
                    $('#viewport').html(changePage(page, recordsPerPage));
                }
            }
        );
    }).change();
    // changePage(1, recordsPerPage);


//Add this in here

});