
$('#singupbtn').click(function (event) {
    event.preventDefault();

    //var memberData = $(this).serializeObject();

    singup();
});


/*
 Attempts to register a new member using a JAX-RS POST.  The callbacks
 the refresh the member table, or process JAX-RS response codes to update
 the validation errors.
 */
function singup() {
    event.preventDefault();
    //clear existing  msgs
    $('span.invalid').remove();
    $('span.success').remove();

    // Display the loader widget
    //$.mobile.loading("show");
//data: JSON.stringify(memberData),
    $.ajax({
        url: 'rest/users',
        contentType: "application/json",
        dataType: "json",
        type: "POST",
        data: formToJSON(),
        success: function (data) {
            $('#formMsgs').append($('<span class="success">Your profile was registered successfully</span>'));
        },
        error: function (error) {
            if ((error.status == 409) || (error.status == 400)) {
                //console.log("Validation error registering user!");

                var errorMsg = $.parseJSON(error.responseText);

                $.each(errorMsg, function (index, val) {
                    $('<span class="invalid">' + val + '</span>').insertAfter($('#' + index));
                });
            } else {
                //console.log("error - unknown server issue");
                $('#formMsgs').append($('<span class="invalid">Unknown server error</span>'));
            }
        }
    });
}

function userlogin() {
    event.preventDefault();
    $.ajax({
        url: 'rest/users/1',
        contentType: "application/json",
        dataType: "json",
        type: "GET",
        //data: formLoginToJSON(),
        success: function (data) {
            $('#formMsgs').append($('<span class="success">Your profile was registered successfully</span>'));
        },
        error: function (error) {
            if ((error.status == 409) || (error.status == 400)) {
                //console.log("Validation error registering user!");

                var errorMsg = $.parseJSON(error.responseText);

                $.each(errorMsg, function (index, val) {
                    $('<span class="invalid">' + val + '</span>').insertAfter($('#' + index));
                });
            } else {
                //console.log("error - unknown server issue");
                $('#formMsgs').append($('<span class="invalid">Unknown server error</span>'));
            }
        }
    });
}
function formToJSON() {
    return JSON.stringify({
        "userName": $('#userName').val(),
        "password": $('#password').val(),
        "confirmedPassword": $('#confirmpassword').val()
    });
}