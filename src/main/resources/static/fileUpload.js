$(document).ready(function() {
	var latestPeople = {}
	var selectedPerson = {}

    $("#upload-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        
		var form_data = new FormData();
		form_data.append( "file", $("input[name=uploadField]")[0].files[0]);

		upload(form_data);
    });
    
    $("#searchField").autocomplete({
    	source: function(request, response) {
    	    var search = {}
    	    search["query"] = request.term;
    		$.ajax({
    			type: "POST",
    			url: "/search",
    			dataType: "json",
    			contentType: "application/json",
    			data: JSON.stringify(search),
    			success: function(data) {
    				latestPeople = data.results;
    				response($.map(data.results, function(item) {
    					return {
    						label: item.name,
    						value: item.name,
    						id: item.id
    					}
    				}));
    			}
    		});
    	},
    	select: function(event, ui){
    		for (var i = 0; i < latestPeople.length; i++) {
                if(latestPeople[i].id == ui.item.id){
                	selectedPerson = latestPeople[i] 
                }
            }
    		$("#personData tbody").append("<tr><td>" + selectedPerson.id + "</td><td>" + selectedPerson.name + "</td><td>" + selectedPerson.age + "</td><td>" + selectedPerson.address + "</td><td>" + selectedPerson.team + "</td></tr>");
    		$("#personData").show();
    	},
    	minLength: 1
    });
    
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        
        //This is not used anymore because results are queried with autocomplete, not form submit
        //search();
    });
});

function upload(form_data) {
    $("#uploadField").prop("disabled", true);

    $.ajax({
        type: "POST",
        url: "/import",
        data: form_data,
        cache: false,
        contentType: false,
        processData: false,
        timeout: 600000,
        success: function (data) {
            var json = "<h4>Response message</h4><hr/><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json.replace(/\"/g, ""));

            console.log("SUCCESS : ", data);
            $("#uploadField").prop("disabled", false);
        },
        error: function (e) {
            var json = "<h4>Response message</h4><hr/><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json.replace(/\"/g, ""));

            console.log("ERROR : ", e);
            $("#uploadField").prop("disabled", false);
        }
    });
}

//This function is not necessary because results are queried with autocomplete, not form submit
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

            var json = "<h4>Response message</h4><hr/><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json.replace(/\"/g, ""));

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Response message</h4><hr/><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json.replace(/\"/g, ""));

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}