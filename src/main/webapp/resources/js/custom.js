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


/* events */
function renderSpeakerName( data, type, row ) {
    if(type == 'display') {
        var str = '';
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            str += '<a class="event-list-speaker" href="../speaker/' + key + '">' + val + '</a>';
        });
        return str;
    }
}

function renderEventLink( data, type, row ) {
    if(type == 'display') {
        return '<a href=../event/' + row.id + '>' + data + '</a>';
    }
}

function renderConfirmedSpeeches( data, type, row ) {
    if(type == 'display') {
        var str = '';
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            if(key != 'НОВАЯ'){
                str += '<div class="event-speech-status">' + key + ' : ' + val + '</div>';
            }
        });
        return str;
    }
    if(type == 'sort') {
        var summ = 0;
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            if(key != 'НОВАЯ'){
                summ += val;
            }
        });
        return summ;
    }
}

function renderNewSpeeches( data, type, row ) {
    if(type == 'display') {
        var str = '';
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            if(key == 'НОВАЯ'){
                str += '<div class="">' + val + '</div>';
            }
        });
        return str;
    }
    if(type == 'sort') {
        var summ = 0;
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            if(key == 'НОВАЯ'){
                summ += val;
            }
        });
        return summ;
    }
}

function renderJiraLink( data, type, row ) {
    if(type == 'display') {
        if(data != null) {
            return '<a href="' + data + '" target="_blank"> Jira </a>';
        } else {
            return '-';
        }
    }
}

/* speech */

function renderSpeechName( data, type, row ) {
    if(type == 'display') {
        return '<a href="../speech/'+ row.id + '/"> ' + data + '</a>';
    }
}

function renderSpeechTags( data, type, row ) {
    if(type == 'display') {
        var str = "";
        data.forEach(function (obj) {
            str += '<div class="participant-tag" data-tag-id="' + obj.id +'">#' + obj.tag + '</div>';

        });
        str += '<div class="participant-tag"><a class="btn btn-xs btn-success" onclick="addSpeakerTag(' + row.id + ')">Edit Tags</a></div>';
        return str;
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
    if(type == 'display') {
        var str = "";
        Object.keys(data).forEach(function (key) {
            var val = data[key];
            str += '<a href="#" class="participant-tag" data-tag-id="' + key +'" onclick="loadTagData(' + key + ')">#' + val + '</a>';

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

/**** single event js ****/

function makeEventSpeechTableEditable() {
    table = $('#speechTable').DataTable();
    mainForm = $('#detailsSpeechForm');
    modal = $('#editSpeech');
}

function initSingleEventControl() {
    $('#add-speech-from-jira').click(function () {
        $.ajax({
            type: "GET",
            url: ajaxUrl + 'jira/',
            success: function (data) {
                if(data.length > 0) {
/*                    var str = 'Updated events: ';
                    var separator = '';
                    data.forEach(function (eventName) {
                        str += separator +  eventName;
                        separator = ', '
                    });
                    successNote(str);*/
                    updateTable();
                }

            }
        });
    });
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

    $('#add-from-jira').click(function () {
        $.ajax({
            type: "GET",
            url: ajaxUrl + 'jira/',
            success: function (data) {
                if(data.length > 0) {
                    var str = 'Updated events: ';
                    var separator = '';
                    data.forEach(function (eventName) {
                        str += separator +  eventName;
                        separator = ', '
                    });
                    successNote(str);
                    updateTable();
                }

            }
        });
    });
}

/**** end events js ****/

/**** speech js ****/

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
    if(speech.jiraStatus != null) {
        nameEn = speech.jiraStatus;
    }
    var status =  '-';
    if(speech.jiraStatus != null) {
        status = '<button type="button" class="btn btn-xs btn-primary" disabled="disabled">' + nameEN + '</button>';
    }
    var sDesc = '-';
    if(speech.shortDescription != null) {
        sDesc = speech.shortDescription;
    }
    var sDescEN = '-';
    if(speech.shortDescriptionEN != null) {
        sDescEN = speech.shortDescriptionEN;
    }


    $('#speech-info').html(
        '<div class="row"><div class="col-xs-4"><strong>Name (en):</strong></div> <div class="col-xs-8 speaker-info-text">'+ nameEN + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Jira Status:</strong></div> <div class="col-xs-8 speaker-info-text">'+ status + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Short Description:</strong></div> <div class="col-xs-8 speaker-info-text">'+ sDesc + '</div></div>' +
        '<div class="row"><div class="col-xs-4"><strong>Short Description (en):</strong></div> <div class="col-xs-8 speaker-info-text">'+ sDescEN + '</div></div>'
    );
}

/**** end speech js ****/

/**** speaker js ****/

function initSpeechForm() {
    $('#add-new-tag').click(function () {
        var tagName = tagForm.find('#new-tag').val();
        if(tagName != null && tagName != ''){
            tagContainer.append(
                '<label class="checkbox-inline">' +
                    '<input type="checkbox" id="inlineCheckbox1" name="tags" value="0-' + tagName + '">' + tagName +
                '</label>'
            );
            tagForm.find('#new-tag').val('');
        }
    });
    tagForm.submit(function () {
            $.ajax({
                type: "POST",
                url: "../ajax/speeches/tags",
                data: tagForm.serialize(),
                success: function (data) {
                    tagModal.modal('hide');
                    updateTable();
                    successNote('Saved');
                }
            });
            return false;
        });
}

function addSpeakerTag(speechId) {
    tagForm.find('#speechIdTags').val(speechId);
    tagContainer.html('');
    tagForm.find('#new-tag').val('');

    $.ajax({
        type: "GET",
        url: "../ajax/speeches/tags/",
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
                url: "../ajax/speeches/" + speechId,
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
    table = $('#speechTable').DataTable();
    mainForm = $('#detailsSpeechForm');
    modal = $('#editSpeech');
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
        twitter = '<a href="' + speaker.twitter.fullLink + '" target="_blank"> @' + speaker.twitter.accountLink + '</a>';
    }
    var github = '-';
    if(speaker.github != null) {
        twitter = '<a href="' + speaker.github.fullLink + '" target="_blank"> ' + speaker.github.accountLink + '</a>';
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
                console.log(data.length);
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