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

function success(text) {
    closeNote();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function fail(event, jqXHR, options, jsExc) {
    closeNote();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}


function initErrorNotify() {
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        fail(event, jqXHR, options, jsExc);
    });
}

/**** render functions ****/
/* user */
function userStatusRender( data, type, row ) {
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

function userDeleteBtnRender( data, type, row ) {
    if(type == 'display') {
        return '<a class="btn btn-xs btn-danger delete" id="' + data + '">Delete</a>';
    }
    else {
        return data;
    }
}

/**** end render functions ****/


/**** user/admin js ****/

function makeEditable(ajaxUrl) {
    $('#create-new-user').click(function () {
        $('#user_id').val(0);
        $('#editUser').modal();
    });

    $('#detailsUserForm').submit(function () {
        save();
        return false;
    });
    initInnerTableButtons();
    initErrorNotify();
}

function initInnerTableButtons() {
    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            success('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        oTable_datatable.clear();
        $.each(data, function (key, item) {
            oTable_datatable.row.add(item);
        });
        oTable_datatable.draw();
        initInnerTableButtons();
    });
}

function save() {
    var frm = $('#detailsUserForm');
    var data = frm.serialize();

    $.ajax({
        type: "POST",
        url: ajaxUrl + $('#user_id').val(),
        data: frm.serialize(),
        success: function (data) {
            $('#editUser').modal('hide');
            updateTable();
            success('Saved');
        }
    });
}

/**** end user/admin js ****/