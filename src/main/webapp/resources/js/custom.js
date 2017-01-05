// find and add input fields like phone and email
/*
$(function() {
    function addInputAdditionalFields(containerId, btnId, name, pHolderText, type) {
        $( btnId ).click(function() {
            var inputCount = $( containerId + " input").length;
            var input = $("<input class='form-control' value=''/>").attr("name", name + inputCount++);
            input.attr("type", type);
            input.attr("placeholder", pHolderText + " " + inputCount);
            $(containerId).append(input);
        });
    }
    addInputAdditionalFields("#additionalPhones", "#addAdditionalPhones", "additionalPhone", "Дополнительный телефон", "tel");
    addInputAdditionalFields("#additionalEmails", "#addAdditionalEmails", "additionalEmail", "Дополнительный Email", "email");
});
*/


/**** notify section ****/
var failedNote;

function closeNote() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNote(text) {
    closeNote();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNote(event, jqXHR, options, jsExc) {
    closeNote();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}


function initErrorNotify() {
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNote(event, jqXHR, options, jsExc);
    });
}

/**** render functions ****/
/* user */
function renderUserStatus( data, type, row ) {
    if(type == 'display') {
        if(data == 'true' || data == 1) {
            return '<button type="button" class="btn btn-xs btn-primary" disabled="disabled">Active</button>';
        }
        else {
            return '<button type="button" class="btn btn-xs btn-danger" disabled="disabled">Blocked</button>';
        }
    }
    else {
        return data;
    }
}

function renderUserDeleteBtn( data, type, row ) {
    if(type == 'display') {
        return '<a class="btn btn-xs btn-danger deleteElem" id="' + row.id + '">Delete</a>';
    }
    else {
        return data;
    }
}

function renderUserName( data, type, row ) {
    if(type == 'display') {
        return '<a class="updateElem" data-user-id="'+ row.id +'"  href="#">' + data + '</a>';
    }
    else {
        return data;
    }
}



/**** end render functions ****/


/**** user/admin js ****/

var mainForm;
var table;
var modal;

function makeUserTableEditable(ajaxUrl) {

    table = $('#userTable').DataTable();
    mainForm = $('#detailsUserForm');
    modal = $('#editUser');

    $('#create-new-user').click(function () {
        $('#user_id').val(0);
        modal.modal();
    });

    mainForm.submit(function () {
        save();
        return false;
    });

    /* initiate inner table elements */
    $(document).ajaxSuccess(function (event, jqXHR, options, jsExc) {
        $('.deleteElem').click(function () {
            deleteRow($(this).attr("id"));
        });
        $('.updateElem').click(function () {
            //deleteRow($(this).attr("id"));
            updateUserRow($(this).attr("data-user-id"))
        });

    });

    userFormHelper();
    initErrorNotify();
}

function updateUserRow(id) {
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            mainForm.find("input[name='" + key + "']").val(value);
        });
        mainForm.find('#user_id').val(data.id);
        if(data.sex == 'MALE') {
            //mainForm.find('#sex_female').removeAttr('checked');
            mainForm.find('#sex_male').attr("checked", true);
        } else {
            //mainForm.find('#sex_male').removeAttr('checked');
            mainForm.find('#sex_female').attr("checked", true);
        }

/*        if(data.enabled == true) {
            mainForm.find('#enabled-true').click();
        } else {
            mainForm.find('#enabled-false').click();
        }*/
        modal.modal();
        updateTable();
    });
}

function userFormHelper() {
    mainForm.find('#sex_male').click(function () {
        mainForm.find('#sex').val('male');
    });
    mainForm.find('#sex_female').click(function () {
        mainForm.find('#sex').val('female');
    })
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNote('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        table.clear();
        $.each(data, function (key, item) {
            table.row.add(item);
        });
        table.draw();
//        initUserTableElements();
    });
}


function save() {
    var frm = $('#detailsUserForm');
    var data = frm.serialize();

    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: frm.serialize(),
        success: function (data) {
            modal.modal('hide');
            updateTable();
            successNote('Saved');
        }
    });
}

/**** end user/admin js ****/