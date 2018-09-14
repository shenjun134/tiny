$(document).ready(function () {

    $('#dialog-recon-detail .recon-detail').resizable({
        handleSelector: '.splitter-horizontal',
        resizeWidth: false
    });


    $("#dialog-recon-detail .recon-detail").attrchange({
        trackValues: true, // set to true so that the event object is updated with old & new values
        callback: function (event) {
//            console.log('#dialog-recon-detail .recon-detail event', event);
            var detailHeight = $('#dialog-recon-detail .recon-detail').css('height');
            var splitterHeight = $('#dialog-recon-detail .splitter-horizontal').css('height');
            var bodyHeight = $('#dialog-recon-detail .modal-body').height();
            detailHeight = parseFloat(detailHeight.replace('px', ''));
            splitterHeight = parseFloat(splitterHeight.replace('px', ''));

            var topTotal = detailHeight + splitterHeight;
            var imageHeight = bodyHeight - topTotal;
//            $('#dialog-recon-detail .recon-image').css('height', '' + imageHeight + 'px');
            $('#dialog-recon-detail .recon-image').css('height', 'calc(100% - ' + topTotal + 'px');
        }
    });

    $('select#layout-type').change(function(){
        var selectedOption = $('select#layout-type option:selected');
//        console.log('selectedOptionselectedOptionselectedOption', selectedOption);
        markMatchedLayout(selectedOption.attr('value'));
    });
});


