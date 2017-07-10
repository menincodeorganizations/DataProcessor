$(document).ready(function() {

    $('p').animate({
        fontSize: '48px'
    }, "slow");
    
    $("#upload-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();
        //var form_data = new FormData(this);
		var form_data = new FormData();
		var lll = $("input[name=uploadField]");
		console.log("test");
		console.log(lll);
		var lll2 = $("input[name=uploadField]")[0].files;
		console.log("test2");
		console.log(lll2);
		var lll3 = $("input[name=uploadField]").files;
		console.log("test3");
		console.log(lll3);
		form_data.append( "file", $("input[name=uploadField]")[0].files[0]);
        upload(form_data);

    });
    
//    $(function() {
    $("#searchField2").autocomplete({
    	source: function(request, response) {
    	    var search = {}
    	    search["query"] = request.term;
    		$.ajax({
    			type: "POST",
    			url: "/search",
    			dataType: "json",
    			contentType: "application/json",
//    			data: {
//    				query: request.term
//    			},
    			data: JSON.stringify(search),
    			success: function(data) {
    				console.log("autocomplete works!");
    				console.log(data);
    				response($.map(data.results, function(item) {
    					return {
    						label: item.name,
    						value: item.id
    					}
    				}));
    			}
    		});
    	},
    	minLength: 1
    });
//    });
    
    
    
    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        search();

    });
});

function upload(form_data) {

    //var search = {}
    //search["username"] = $("#username").val();

    $("#uploadField").prop("disabled", true);
	console.log(form_data);
    $.ajax({
        type: "POST",
        //contentType: "application/json",
        url: "/import",
        data: form_data,
        //dataType: 'json',
        cache: false,
        contentType: false,
        processData: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#uploadField").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#uploadField").prop("disabled", false);

        }
    });
}

function search() {

    var search = {}
    search["query"] = $("#searchField").val();

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/search",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}