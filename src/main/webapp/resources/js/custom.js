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
        timeout: 7500
    });
}

function failNote(event, jqXHR, options, jsExc) {
    closeNote();
    if(typeof jqXHR !== 'undefined') {
        failedNote = noty({
            text: 'Failed: ' + jqXHR.statusText + "<br>",
            type: 'error',
            layout: 'bottomRight'
        });
    }

}

function customErrorNote(text) {
    closeNote();
    failedNote = noty({
        text: 'Failed: ' + text + "<br>",
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
    return data;
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
    return data;
}


/* events */
function renderSpeakerName( data, type, row ) {
    if(type == 'display') {
        var str = '';
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            str += '<a class="event-list-speaker" href="/speaker/' + key + '">' + val + '</a>';
        });
        return str;
    }
    return data;
}

function renderSpeechStatus( data, type, row ) {
    if(type == 'display') {
        return data;
    }

    if(type == 'sort') {
        if(data == "Новая") {
            return 10;
        }
        else if(data == "Рассмотрение заявки") {
            return 20;
        }
        else if(data == "Описание доклада") {
            return 30;
        }
        else if(data == "Тестирование, тренинги") {
            return 40;
        }
        else if(data == "Требует уточнения") {
            return 50;
        }
        else if(data == "Фидбек спикера") {
            return 60;
        }
        else if(data == "Фидбек участников") {
            return 70;
        }
        else if(data == "Анализ") {
            return 80;
        }
        else if(data == "Закрыта") {
            return 90;
        }
        else {
            return 200;
        }
    }

    return data;
}

function renderSpeechJiraLink( data, type, row ) {
    if(type == 'display') {
        return '<a target="_blank" href=' + data + '> Jira </a>';
    }

    return data;
}

function renderEventLink( data, type, row ) {
    if(type == 'display') {
        return '<a href=../event/' + row.id + '>' + data + '</a>';
    }
    return data;
}

function renderConfirmedSpeeches( data, type, row ) {
    if(type == 'display') {
            console.log(data);
        var str = '';
        var tmp = '';
        var sNew = '';
        var sExplore = '';
        var sDescription = '';
        var sTesting = '';
        var sRevision = '';
        var sFeedbackSpeaker = '';
        var sFeedbackPart = '';
        var sAnalyse = '';
        var sClosed =  '';

        Object.keys(data).forEach(function (key) {
            var val = data[key];
            if(key == "Новая") {
                sNew = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else if(key == "Рассмотрение заявки") {
                sExplore = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else if(key == "Описание доклада") {
                sDescription = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else if(key == "Тестирование, тренинги") {
                sTesting = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else if(key == "Требует уточнения") {
                sRevision = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else if(key == "Фидбек спикера") {
                sFeedbackSpeaker = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else if(key == "Фидбек участников") {
                sFeedbackPart = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else if(key == "Анализ") {
                sAnalyse = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else if(key == "Закрыта") {
                sClosed = '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
            else {
                tmp += '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
        });
        str = sNew + sExplore + sDescription + sTesting + sRevision + sFeedbackSpeaker + sFeedbackPart + sAnalyse + sClosed + tmp;
        return str;
    }
    if(type == 'sort') {
        var summ = 0;
        Object.keys(data).forEach(function (key) {
            summ += data[key];
        });
        return summ;
    }

    return data;
}

function renderNewSpeeches( data, type, row ) {
    if(type == 'display') {
        var str = '';
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            if(key == 'Новая'){
                str += '<div class="">' + val + '</div>';
            }
        });
        return str;
    }
    if(type == 'sort') {
        var summ = 0;
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            if(key == 'Новая'){
                summ += val;
            }
        });
        return summ;
    }

    return data;
}

function renderJiraLink( data, type, row ) {
    if(type == 'display') {
        if(data != null) {
            return '<a href="' + data + '" target="_blank"> Jira </a>';
        } else {
            return '-';
        }
    }

    return data;
}

/* speech + speaker */

function renderSpeechName( data, type, row ) {
    if(type == 'display') {
        return '<a href="../speech/'+ row.id + '/"> ' + data + '</a>';
    }
    return data;
}

function renderSpeechTags( data, type, row ) {
    if(type == 'display') {
        var str = "";
        data.forEach(function (obj) {
            str += '<div class="participant-tag" data-tag-id="' + obj.id +'">#' + obj.tag + '</div>';

        });
        str += '<div class="participant-tag"><a class="btn btn-xs btn-success" onclick="addSpeechTag(' + row.id + ')">Edit Tags</a></div>';
        return str;
    }
    return data;
}

function renderSpeechEventLink( data, type, row ) {
    if(type == 'display') {
        return '<a href=../event/' + row.eventId + '>' + data + '</a>';
    }

    return data;
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
                str += '<div>' + item.eventName + ' = ' + item.rating + '</div>';
            }
        });
        str +='</div>';
        return str;
    }
    return data;
}

function renderParticipantTags( data, type, row ) {
    var str = "";

    if(type == 'display') {
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            str += '<a href="#" class="participant-tag" data-tag-id="' + key +'" onclick="loadTagData(' + key + ')">#' + val + '</a>';

        });
        return str;
    }

    if(type == 'filter') {
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            str += val + ' ';

        });
        return str;
    }

    return data;
}

function renderParticipantEmails( data, type, row ) {
    if(type == 'display') {
        var sep = '';
       var str = "";
        data.forEach(function (obj) {
            str += sep + obj.email;
            sep = ', ';
        });
        return str;
    }
    return data;
}

function renderParticipantName( data, type, row ) {
    if(type == 'display') {
        return '<a href="../speaker/' + row.id + '"> ' + data + '</a>';
    }
    return data;
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
    return data;

}

function renderUserName( data, type, row ) {
    if(type == 'display') {
        return '<a class="updateElem" href="#" onclick="updateUserRow(' + row.id + ')">' + data + '</a>';
    }
    return data;
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

function createDeleteDialog(entityName, deleteUrl, redirectUrl) {
    $("#footer").append(
    '<div id="deleteDialog" class="modal fade" tabindex="-1" role="dialog">' +
        '<div class="modal-dialog" role="document">' +
            '<div class="modal-content">' +
                '<div class="modal-header">' +
                    '<button id="delete-close" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
                    '<h3 class="modal-title">Confirm delete Entity</h3>' +
                '</div>' +
                '<div class="modal-body">' +
                    '<p>' + entityName + '</p>' +
                '</div>' +
                '<div class="modal-footer">' +
                    '<button id="delete-cancel" type="button" class="btn btn-default">Cancel</button>' +
                    '<button id="delete-confirm" type="button" class="btn btn-danger">Delete</button>' +
                '</div>' +
            '</div>' +
        '</div>' +
    '</div>'
    );
    var deleteDialog = $('#deleteDialog');
    deleteDialog.find('#delete-close').click(function () {
       removeDeleteDialog();
    });
    deleteDialog.find('#delete-cancel').click(function () {
        removeDeleteDialog();
    });
    deleteDialog.find('#delete-confirm').click(function () {
        $.ajax({
            url: deleteUrl,
            type: 'DELETE',
            success: function () {
                successNote('Deleted: ' + entityName);
                removeDeleteDialog();
                if(redirectUrl != null) {
                    window.location.replace(redirectUrl);
                }
            }
        });

    });

    deleteDialog.modal();
}

function removeDeleteDialog() {
    $('#deleteDialog').remove();
}

/**** end common section ****/

/**** single event js ****/

function makeEventSpeechTableEditable() {
    table = $('#speechTable').DataTable();
    mainForm = $('#detailsSpeechForm');
    modal = $('#editSpeech');
}

function initSingleEventControl() {

    $('#create-new-speech').click(function () {
        //without param patricipants - this is new speech
        showSpeakerSearchForm();
    });


    speakerFindForm.submit(function (ev) {
        ev.preventDefault();
        var partId = [];
        $.each($('#detailsSpeakersFindForm').find('input[name="speakers"]:checked'), function (key, value) {
            partId.push($(this).val());
        });
        $('#addSpeaker').modal('hide');
        clearForm(mainForm);
        mainForm.find('#eventId').val(eventID);
        mainForm.find('#partId').val(partId);
        //speaker cost and rating must be not null
        mainForm.find('#speakerCost').val(0);
        mainForm.find('#rating').val(0);
        //this is new speech
        mainForm.find('#id').val(0);
        modal.modal();
        //second modal window doesn't scroll
        //this fix it
        modal.css('overflow-y', 'scroll');
    });

    mainForm.submit(function () {
        $.ajax({
            type: "POST",
            url: "/ajax/speeches/",
            data: mainForm.serialize(),
            success: function (data) {
                modal.modal('hide');
                updateTable();
                successNote('New speech saved');
            }
        });
        return false;
    });

    $('#add-speech-from-jira').click(function () {
        $.ajax({
            type: "GET",
            url: ajaxUrl + 'jira/',
            success: function (data) {
                var isEmpty = true;
                Object.keys(data).forEach(function (key) {
                    var val = data[key];
                    var str;
                    var separator;
                    if (key == "success") {
                        if (val.length > 0) {
                            isEmpty = false;
                            str = 'Updated events: ';
                            separator = '';
                            val.forEach(function (speechName) {
                                str += separator + speechName;
                                separator = ', '
                            });
                            successNote(str);
                        }
                    }
                    if (key == "error") {
                        if (val.length > 0) {
                            isEmpty = false;
                            str = 'Error by update speeches: ';
                            var speechLinks = [];
                            separator = '';
                            val.forEach(function (speechName) {
                                str += separator + speechName;
                                separator = ', ';
                                speechLinks.push('<a class="speech-link-error alert-link" target="_blank" href="http://jira.jugru.org/browse/' + speechName + '">' + speechName + '</a>' );
                            });
                            customErrorNote(str);
                            showErrorAlert(speechLinks);

                        }
                    }
                });
                if(isEmpty) {
                    successNote("No speeches found");
                }
                initEvent();
                updateTable();
            }
        });
    });

    $('#delete-event').click(function () {
        createDeleteDialog(event.name + " " + event.version, "/ajax/events/" + eventID, "/events");
    });
}

function showErrorAlert(speechLinks) {
    var speechStr = '';
    var separator = '';
    speechLinks.forEach(function (link) {
        speechStr += separator + link;
        separator = ' ,'
    });
    $('#alertArea').html(
    '<div class="alert alert-danger alert-dismissible" role="alert">' +
        '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
    '<strong>Ой, что-то пошло не так!</strong> Пожалуйста, вручную проверьте эти доклады: <br>' +
        speechStr +
    '</div>'
    );

}

/**** end single event js ****/

/**** events js ****/

function makeEventTableEditable() {
    table = $('#eventTable').DataTable();
    mainForm = $('#detailsEventForm');
    modal = $('#editEvent');

    $('#create-new-event').click(function () {
        clearForm(mainForm);
        mainForm.find('#eventId').val(0);
        modal.modal();
    });

    mainForm.submit(function () {
        save();
        return false;
    });

    $('#add-all-from-jira').click(function () {
        $.ajax({
            type: "GET",
            url: ajaxUrl + 'jira/',
            success: function (data) {
                updateTable();
                Object.keys(data).forEach(function (key) {
                    var val = data[key];
                    var str;
                    var separator;
                    if (key == "success") {
                        if (val.length > 0) {
                            str = 'Updated events: ';
                            separator = '';
                            val.forEach(function (eventName) {
                                str += separator + eventName;
                                separator = ', '
                            });
                            successNote(str);
                        }
                    }
                    if (key == "error") {
                        if (val.length > 0) {
                            str = 'Error by update events: ';
                            separator = '';
                            val.forEach(function (eventName) {
                                str += separator + eventName;
                                separator = ', '
                            });
                            customErrorNote(str);
                        }
                    }
                });

            }
        });
    });
}

function initEvent() {
    $.ajax({
        type: "GET",
        url: "../ajax/events/" + eventID,
        success: function (data) {
            event = data;
            addEventInfo();
        }
    });
}

function addEventInfo(){
    $('#page-name').html('Конференция: ' +event.name + ' ' + event.version);
    $('#photo').html('<img class="" src="' + event.logoURL + '" />');
    $('#count').html('Сводка статусов докладов: <br>' + renderConfirmedSpeeches(event.speechesCount, "display"));
    $('#desc').html(event.description);

}

/**** end events js ****/

/**** speech js ****/

function initSingleSpechControl() {
    $('#edit-tags').click(function () {
        addSpeechTag(speechId);
    });

    $('#edit-speakers').click(function () {
        showSpeakerSearchForm(speech.participants);
    });

    speakerFindForm.submit(function () {
        $.ajax({
            type: "POST",
            url: "/ajax/speeches/" + speechId + "/participants",
            data: speakerFindForm.serialize(),
            success: function (data) {
                $('#addSpeaker').modal('hide');
                initSpeech();
                successNote('Saved');
            }
        });
        return false;
    });



    $('#edit-speech').click(function () {
        clearForm(mainForm);
        $.each(speech, function (key, value) {
            mainForm.find("input[name='" + key + "']").val(value);
            mainForm.find("textarea[name='" + key + "']").val(value);
        });
        var part = [];
        Object.keys(speech.participants).forEach(function (key) {
            part.push(key);
        });
        mainForm.find('#partId').val(part);
        modal.modal();
    });

    mainForm.submit(function () {
        $.ajax({
            type: "POST",
            url: "/ajax/speeches/",
            data: mainForm.serialize(),
            success: function (data) {
                modal.modal('hide');
                initSpeech();
                successNote('Saved');
            }
        });
        return false;
    });


    $('#delete-speech').click(function () {
        createDeleteDialog(speech.name, "/ajax/speeches/" + speechId, "/event/" + speech.eventId);
    });
}

/*
    Don`t forget override submint btn for this form
 */
function showSpeakerSearchForm(part) {
    var speakerArray = [];
    $('#speaker-container').html('');
    if(part != null) {
        Object.keys(part).forEach(function (key) {
            speakerArray.push(key);
            $('#speaker-container').append('<label class="checkbox-inline">' +
                '<input type="checkbox" name="speakers" value="'+ key +'" checked>' + part[key] +
                '</label>')
        });
    }

    $('#addSpeaker').modal();

    if(speakerSearchDatatable == null) {
        $.ajax({
            type: "GET",
            url: "/ajax/participants",
            success: function (data) {
                var speakerData = [];
                data.forEach(function (el) {
                    if( !indexContains(speakerArray, el.id) ){
                        speakerData.push([el.fullName, '<a class="btn btn-success" data-fullName="el.fullName" ' +
                        'onclick="addSpeakerToSpeech(' + el.id + ', \'' + el.fullName  + '\')" href="#"><i class="fa fa-plus"aria-hidden="true"></i></a>']);
                    }
                });

                initSpeakerSearchTable(speakerData);
            }
        });
    }
}

function initSpeakerSearchTable(data) {
    speakerSearchDatatable = $('#speakerSearchTable').DataTable({
        data: data,
        scrollY: '50vh',
        scrollCollapse: true,
        paging: false,
        ordering: false,
        info: false,
        columns: [
            {title: "Name"},
            {title: "Add"}
        ]
    } );
}

function addSpeakerToSpeech(id, name) {
    $('#speaker-container').append('<label class="checkbox-inline">' +
        '<input type="checkbox" name="speakers" value="'+ id +'" checked>' + name +
        '</label>');
}

function initSpeech() {
    $.ajax({
        type: "GET",
        url: "/ajax/speeches/" + speechId,
        success: function (data) {
            speech = data;
            addSpeechInfo();
        }
    });
}

function addSpeechInfo() {
    $('#page-name').html('Доклад: ' +speech.name);
    var nameEN = '-';
    if(speech.nameEN != null) {
        nameEN = speech.nameEN;
    }
    var status =  '-';
    if(speech.jiraStatus != null) {
        status = '<button type="button" class="btn btn-xs btn-primary" disabled="disabled">' + speech.jiraStatus + '</button>';
    }
    var resolution = 'Нет решения';
    if(speech.jiraResolution != null) {
        resolution = speech.jiraResolution;
    }

    var rating = '';
    if(speech.rating != null) {
        rating = speech.rating;
    }

    var event = '-';
    if(speech.eventId != null) {
        event = '<a href="/event/' + speech.eventId + '">' + speech.eventName + '</a>';
    }

    var speakers = '';
    if(speech.participants != null) {
        Object.keys(speech.participants).forEach(function (key) {
            var val = speech.participants[key];
            speakers += '<a class="speaker-block-link" href="/speaker/' + key + '">' + val + '</a>';
        });
    }

    var link = '-';
    if(speech.jiraLink != null) {
        link = '<a target="_blank" href="' + speech.jiraLink + '"> Jira </a>'
    }

    var sync = '-';
    if(speech.jiraSync != null) {
        ds = speech.jiraSync;
        if(ds.length > 4) {
            var dateObject = new Date(ds[0], ds[1], ds[2], ds[3], ds[4]);
            var options = {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: 'numeric',
                minute: 'numeric',
                second: 'numeric',
                timezone: 'UTC'
            };
            sync = dateObject.toLocaleString("ru", options);
        }
    }

    var tags = '';
    if(speech.tags != null) {
        var separator = '';
        speech.tags.forEach(function (obj) {
            tags += separator + '<strong>' +  obj.tag + '</strong>';
            separator = ', ';
        });
    }

    var sDesc = '-';
    if(speech.shortDescription != null) {
        sDesc = speech.shortDescription;
    }

    var desc = '-';
    if(speech.fullDescription != null) {
        desc = speech.fullDescription
    }

    var descEN = '-';
    if(speech.fullDescriptionEN != null) {
        descEN = speech.fullDescriptionEN
    }

    var sDescEN = '-';
    if(speech.shortDescriptionEN != null) {
        sDescEN = speech.shortDescriptionEN;
    }

    var plan = '-';
    if(speech.plan != null) {
        plan = speech.plan;
    }

    var viwerVal = '-';
    if(speech.viewerValue != null) {
        viwerVal = speech.viewerValue;
    }

    var focus = '-';
    if(speech.focus != null) {
        focus = speech.focus;
    }

    $('#common-speech-info').html(
        '<div class="row"><div class="col-xs-4"><strong>Name (en):</strong></div> <div class="col-xs-8 speaker-info-text">'+ nameEN + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Jira Status:</strong></div> <div class="col-xs-8 speaker-info-text">'+ status + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Jira Resolution:</strong></div> <div class="col-xs-8 speaker-info-text">'+ resolution + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Rating:</strong></div> <div class="col-xs-8 speaker-info-text"><strong>'+ rating + '</strong></div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Event:</strong></div> <div class="col-xs-8 speaker-info-text">'+ event + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Speakers:</strong></div> <div class="col-xs-8 speaker-info-text">'+ speakers + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Jira link:</strong></div> <div class="col-xs-8 speaker-info-text">'+ link + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Last sync:</strong></div> <div class="col-xs-8 speaker-info-text">'+ sync + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Tags:</strong></div> <div class="col-xs-8 speaker-info-text">'+ tags + '</div></div>'
    );
    $('#speech-description').html(
        '<div class="row"><div class="col-xs-3"><strong>Short Description:</strong></div> <div class="col-xs-9 speaker-info-text">'+ sDesc + '</div></div>' +
        '<div class="row"><div class="col-xs-3"><strong>Description:</strong></div> <div class="col-xs-9 speaker-info-text">'+ desc + '</div></div>' +
        '<div class="row"><div class="col-xs-3"><strong>Short Description (en):</strong></div> <div class="col-xs-9 speaker-info-text">'+ sDescEN + '</div></div>' +
        '<div class="row"><div class="col-xs-3"><strong>Description (en):</strong></div> <div class="col-xs-9 speaker-info-text">'+ descEN + '</div></div>'
    );
    $('#speech-others').html(
        '<div class="row"><div class="col-xs-3"><strong>Что получат:</strong></div> <div class="col-xs-9 speaker-info-text">'+ viwerVal + '</div></div>' +
        '<div class="row"><div class="col-xs-3"><strong>Plan:</strong></div> <div class="col-xs-9 speaker-info-text">'+ plan + '</div></div>' +
        '<div class="row"><div class="col-xs-3"><strong>Доклад ориентирован:</strong></div> <div class="col-xs-9 speaker-info-text">'+ focus + '</div></div>'
    );
}

/**** end speech js ****/

/**** speaker js ****/

function initParticipantControl() {
    $('#edit-participant').click(function () {

        clearForm(mainForm);
        $.each(speaker, function (key, value) {
            mainForm.find("input[name='" + key + "']").val(value);
            mainForm.find("textarea[name='" + key + "']").val(value);
        });
        modal.modal();

    });
}

function initTagForm() {
    $('#add-new-tag').click(function () {
        var tagName = tagForm.find('#new-tag').val();
        if(tagName != null && tagName != ''){
            tagContainer.append(
                '<label class="checkbox-inline">' +
                    '<input type="checkbox" name="tags" value="0-' + tagName + '" checked>' + tagName +
                '</label>'
            );
            tagForm.find('#new-tag').val('');
        }
    });
    tagForm.submit(function () {
            $.ajax({
                type: "POST",
                url: "/ajax/speeches/tags",
                data: tagForm.serialize(),
                success: function (data) {
                    tagModal.modal('hide');
                    if(typeof ajaxUrl !== 'undefined') {
                        updateTable();
                    } else if (typeof speechId !== 'undefined') {
                        initSpeech();
                    }
                    successNote('New speech tags: ' + data );
                }
            });
            return false;
        });
}

function addSpeechTag(speechId) {
    tagForm.find('#speechIdTags').val(speechId);
    tagContainer.html('');
    tagForm.find('#new-tag').val('');

    $.ajax({
        type: "GET",
        url: "/ajax/speeches/tags/",
        success: function (data) {
            data.forEach(function (tag) {
                tagContainer.append(
                    '<label class="checkbox-inline">' +
                        '<input type="checkbox" id="' + tag.id + '" name="tags" value="' + tag.id + '-' + tag.tag + '">' + tag.tag +
                    '</label>'
                );

            });
            $.ajax({
                type: "GET",
                url: "/ajax/speeches/" + speechId,
                success: function (data) {
                    data.tags.forEach(function (tag) {
                        el = "#" + tag.id;
                        tagContainer.find(el).click();
                    });
                }
            });
            tagModal.modal();
        }
    });
}


function makeSpeechTableEditable() {

}


function updateSpeechRow(id){
    clearForm(mainForm);
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            mainForm.find("input[name='" + key + "']").val(value);
            mainForm.find("textarea[name='" + key + "']").val(value);
        });

    });

}


function initSpeaker() {
    $.ajax({
        type: "GET",
        url: "../ajax/participants/" + speakerID,
        success: function (data) {
            speaker = data;
            addSpeakerInfo();
        }
    });
}

function addSpeakerInfo(){
    $('#page-name').html('Спикер: ' +speaker.fullName);
    $('#photo').html('<img class="img-circle" src="' + speaker.photoURL + '" />');
    var phone = '-';
    if(speaker.phone != null) {
        phone = speaker.phone;
    }
    var emails = '';
    speaker.emails.forEach(function (e) {
        if(e.main = true) {
            emails += '<div> <strong>' + e.email + '</strong></div>';
        } else {
            emails += '<div>' + e.email + '</div>';
        }
    });
    var twitter = '-';
    if(speaker.twitter != null) {
        twitter = '<a href="' + speaker.twitter.fullLink + '" target="_blank"> @' + speaker.twitter.account + '</a>';
    }
    var github = '-';
    if(speaker.github != null) {
        twitter = '<a href="' + speaker.github.fullLink + '" target="_blank"> ' + speaker.github.account + '</a>';
    }
    var skype = '-';
    if(speaker.skype != null) {
        skype = speaker.skype;
    }
    var employer = '-';
    if(speaker.employer != null) {
        employer = speaker.employer;
    }
    var city = '-';
    if(speaker.city != null) {
        city = speaker.city;
    }

    $('#contacts').html(
        '<div class="row"><div class="col-xs-4"><strong>Phone:</strong></div> <div class="col-xs-8 speaker-info-text">'+ phone + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Emails:</strong></div> <div class="col-xs-8 speaker-info-text">'+ emails + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Twitter:</strong></div> <div class="col-xs-8 speaker-info-text">'+ twitter + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Skype:</strong></div> <div class="col-xs-8 speaker-info-text">'+ skype + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Github:</strong></div> <div class="col-xs-8 speaker-info-text">'+ github + '</div></div>' +

        '<div class="row speaker-info-separator"><div class="col-xs-4"><strong>Employer:</strong></div> <div class="col-xs-8 speaker-info-text">'+ employer + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>City:</strong></div> <div class="col-xs-8 speaker-info-text">'+ city + '</div></div>'
    );
    var bioRU = '-';
    if(speaker.biography != null) {
        bioRU =speaker.biography;
    }
    var bioEN = '-';
    if(speaker.biographyEN != null) {
        bioEN =speaker.biography;
    }
    var background = '-';
    if(speaker.speakerBackground != null) {
        background =speaker.speakerBackground;
    }

    $('#bio').html(
        '<div class="speaker-bio-ru"><strong>Bio:</strong> ' + bioRU + '</div>' +
        '<div class="speaker-bio-en"><strong>BioEN:</strong> ' + bioEN + '</div>' +
        '<div class="speaker-background"><strong>Background:</strong> ' + background + '</div>'
    );
}

/**** end speaker js ****/


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

        if(data.enabled == true) {
            mainForm.find('#enabled-true').click();
        } else {
            mainForm.find('#enabled-false').click();
        }

        modal.modal();
    });
}

function userFormHelper() {
    enabledHelper();
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

/**** profile ****/
function initUserProfile() {
    mainForm = $('#detailsUserForm');
    updateProfileForm();

    mainForm.submit(function () {
        $.ajax({
            type: "POST",
            url: ajaxUrl,
            data: mainForm.serialize(),
            success: function (data) {
                if (data.length > 0) {
                    displaytestJiraInfo(
                        '<h4 class="text-success">Jira connection success</h4>' +
                        '<p>Events, avaliable for you:</p>' +
                        '<p>' + data + '</p>'
                    );
                    successNote("Saved");
                    updateProfileForm();
                } else {
                    displaytestJiraInfo(
                        '<h4 class="text-danger">Jira connection failed, check Credentials</h4>'
                    );
                }
            },
            error: function () {
                displaytestJiraInfo(
                    '<h4 class="text-danger">Jira connection failed, check Credentials</h4>'
                );
            }
        });
        return false;
    });
}

function displaytestJiraInfo(msg) {
    var el = $('#jira-info');
    el.html('');
    el.html(msg);
}

function updateProfileForm() {
    $.get(ajaxUrl, function (data) {
        $.each(data, function (key, value) {
            mainForm.find("input[name='" + key + "']").val(value);
        });
        mainForm.find('#jiraPassword').val('');
        mainForm.find('#jiraLogin').val('');
        var b = data.jiraValidCredentials;
            mainForm.find('#group-jiraLogin').toggleClass('has-success', b);
            mainForm.find('#group-jiraPassword').toggleClass('has-success', b);
            mainForm.find('#group-jiraLogin').toggleClass('has-warning', !b);
            mainForm.find('#group-jiraPassword').toggleClass('has-warning', !b);
    });
}
/**** end profile ****/


function indexContains(a, obj) {
    var i = a.length;
    while (i--) {
        if (a[i] == obj) {
            return true;
        }
    }
    return false;
}