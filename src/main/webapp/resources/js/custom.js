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

/**** datetime picker section ****/

function initDTpicker() {
    $('.date-picker').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });
    $('.time-picker').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });
    $('.datetime-picker').datetimepicker({
        format: 'Y-m-d H:i'
    });
}

/**** render functions ****/
function renderDeleteBtn( data, type, row ) {
    if(type == 'display') {
        return '<a class="btn btn-xs btn-danger deleteElem" onclick="deleteRow(' + row.id + ')">Delete</a>';
    }
    else {
        return data;
    }
}

function renderDate( data, type, row ) {
    if(type == 'display') {
        var dateObject = new Date(data[0], data[1], data[2], data[3], data[4]);
        var options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            timezone: 'UTC'
        };
        return '<span>' + dateObject.toLocaleString("ru", options)  + '</span>';
    }
    else {
        return data;
    }
}

/* participant */

function renderParticipantRatings( data, type, row ) {
    if(type == 'sort') {
        var summ = 0;
        $.each(data, function (key, item) {
            if (item.rating > 0) {
                summ += item.rating;
            }
        });
        return summ;
    }
    if(type == 'display') {
        var str = '<div class="participant-rating-container">';
        $.each(data, function (key, item) {
            if (item.rating > 0) {
                str += '<div>' + item.eventName + ' = ' + item.rating + '%</div>';
            }
        });
        str +='</div>';
        return str;
    }
}

function renderParticipantTags( data, type, row ) {
    if(type == 'display') {
        var str = "";
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            str += '<a href="#" class="participant-tag" data-tag-id="' + key +'" onclick="loadTagData(' + key + ')">#' + val + '</a>';

        });
        return str;
    }
}

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

function renderUserName( data, type, row ) {
    if(type == 'display') {
        return '<a class="updateElem" href="#" onclick="updateUserRow(' + row.id + ')">' + data + '</a>';
    }
    else {
        return data;
    }
}

/**** end render functions ****/

/**** common section ****/

var mainForm;
var table;
var modal;

function clearForm(formId) {
    $(':input', formId)
        .not(':button, :submit, :reset')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
    $('.additional-field').remove();
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
    });
}


function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: mainForm.serialize(),
        success: function (data) {
            modal.modal('hide');
            updateTable();
            successNote('Saved');
        }
    });
}

function loadTagData(tagId) {
    var url = '/ajax/participants/tag/' + tagId;
    table.ajax.url( url ).load();
}

function addInputAdditionalEmail(containerId, btnId, pHolderText) {
    $( btnId ).click(function() {
        var inputCount = $( containerId + " input").length  - 1;
        $(containerId).append(
            '<div class="col-xs-offset-3 col-xs-7 additional-field">' +
            '<input id="'+ btnId + inputCount + '" type="email" class="form-control" value="" placeholder="' + pHolderText + " " + inputCount + '"/>' +
            '</div>');
    });
}

/**** end common section ****/

/**** participants js ****/

function makeParticipantTableEditable(ajaxUrl) {
    table = $('#participantTable').DataTable();
    mainForm = $('#detailsParticipantForm');
    modal = $('#editParticipant');

    $('#create-new-participant').click(function () {
        clearForm(mainForm);
        mainForm.find('#participantId').val(0);
        modal.modal();
    });

    mainForm.submit(function () {
        emailHelper();
        save();
        return false;
    });


    initDTpicker();
    addInputAdditionalEmail("#emailContainer", "#addEmail", "Additional Email");
    sexHelper();
}

function emailHelper() {
    var separator = "::";
    var str = mainForm.find("#email-0").val();
    mainForm.find(".additional-field input").each(function () {
        str += separator + $( this ).val();
    });
    mainForm.find("#email").val(str);
}

/**** end participants js ****/


/**** user/admin js ****/

function makeUserTableEditable(ajaxUrl) {

    table = $('#userTable').DataTable();
    mainForm = $('#detailsUserForm');
    modal = $('#editUser');

    $('#create-new-user').click(function () {
        clearForm(mainForm);
        mainForm.find('#user_id').val(0);
        modal.modal();
    });

    mainForm.submit(function () {
        save();
        return false;
    });

    userFormHelper();
    initErrorNotify();
}

function updateUserRow(id) {
    clearForm(mainForm);
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            mainForm.find("input[name='" + key + "']").val(value);
        });
        mainForm.find('#user_id').val(data.id);
        if(data.sex == 'MALE') {
            mainForm.find('#sex_male').click();
        } else {
            mainForm.find('#sex_female').click();
        }

        if(data.enabled == true) {
            mainForm.find('#enabled-true').click();
        } else {
            mainForm.find('#enabled-false').click();
        }

        modal.modal();
    });
}

function userFormHelper() {
    sexHelper();
    enabledHelper();
}

function sexHelper() {
    mainForm.find('#sex_male').click(function () {
        mainForm.find('#sex').val('male');
    });
    mainForm.find('#sex_female').click(function () {
        mainForm.find('#sex').val('female');
    });
}

function enabledHelper() {
    mainForm.find('#enabled-true').click(function () {
        mainForm.find('#enabled').val('true');
    });
    mainForm.find('#enabled-false').click(function () {
        mainForm.find('#enabled').val('false');
    });
}

/**** end user/admin js ****/