$(document).ready(function () {
    function changePage(page, recordsPerPage) {
        var pnumber = page || 1;
        var recordsPerPage = $('#filmsPerPage').val();

        $.ajax({
            type: 'GET',
            data: 'json',
            url: '/controller?command=films-page&page=' + pnumber + '&filmsPerPage=' + recordsPerPage,
            success: function (data) {
                var jqObj = jQuery(data);
                var films = jqObj.find("#viewport").children();
                $("#viewport").empty();
                $("#viewport").append(films);
            }
        })
    }

    $("#selectPerPage").change(function () {
        $('#filmsPerPage').val($(this).val());
        var records = $('#filmsCount').val();
        var recordsPerPage = $(this).val();
        var pages = Math.ceil(records / recordsPerPage);
        $('#pagination-films').twbsPagination('destroy');
        changePage(1, recordsPerPage);
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

//Add this in here

});