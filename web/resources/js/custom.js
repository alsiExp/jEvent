// resize editor window
// $(function () {
//     $(".wysihtml5-resize iframe").contents().find("body").each(function () {
//             $(this).on("click", function () {
//                 var max_height = 750;
//                 var editor_box = $(".wysihtml5-resize iframe");
//                 var doc_height = $(this);
//                 var scroll_height = editor_box[0].scrollHeight;
//       // console.log('height: ' + doc_height + ', scroll: ' + editor_box[0].scrollHeight + ', max-height: ' + max_height);
//                 if (doc_height != scroll_height) {
//                     if (doc_height < max_height) {
//                         editor_box.height(doc_height + 30);
//                     } else {
//                         editor_box.height(max_height);
//                     }
//                 }
//             })
//         }
//     )
//
// });

// find and add input fields like phone and email
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