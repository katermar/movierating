$(document).ready(function () {
    var maxFields = 10; //maximum input boxes allowed
    var wrapper = $(".emails-wrap"); //Fields wrapper
    var addButton = $(".add-email-button"); //Add button ID

    var x = 1; //initlal text box count
    $(addButton).click(function (e) { //on add input button click
        e.preventDefault();
        if (x < maxFields) { //max input box allowed
            x++; //text box increment
            var html = "<div class=\"email-wrap\">\n" +
                "                    <div class=\"email-input\"><input type=\"email\" placeholder=\"enter e-mail\" name=\"email\" required pattern=\"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\"></div>\n" +
                "                    <button class=\"remove-field\">-</button>\n" +
                "                </div>";
            $(wrapper).append(html); //add input box
        }
    });

    $(wrapper).on("click", ".remove-field", function (e) { //user click on remove text
        e.preventDefault();
        $(this).parent('div').remove();
        x--;
    })
});