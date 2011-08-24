/**
 * for service request add comment page
 */

jQuery(document).ready(function(){
        loader();
        jQuery('input:text').hint();
        jQuery('textarea').hint();
});

function loader() {

    var authorB = extractValueB('author', 'value');
    var textB = extractValueB('text', 'value');

    var validLenP = function (str, len) { return ( (str != undefined && str.length >= len) );};

    var authorValidLenB = liftB(validLenP, authorB, 3);
    var textValidFormatB = liftB(validLenP, textB, 6);

    var readyForSubmissionB = andB(authorValidLenB, textValidFormatB);

    insertDomB(ifB(authorValidLenB, IMG({src: '../images/checkmark.gif'}), ''), 'author-checkmark');
    insertDomB(ifB(textValidFormatB, IMG({src: '../images/checkmark.gif'}), ''), 'text-checkmark');

    insertValueB(notB(readyForSubmissionB), 'addcomment', 'disabled');
    insertValueB(ifB(readyForSubmissionB, 'button', 'buttonDisabled'), 'addcomment', 'className');
}
