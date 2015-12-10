
function cutText(content, max, empty_holder){
	if(empty_holder == null){
		empty_holder = "Not set.";
	}
	if(content == null) {
		content = empty_holder;
	} else if(content.length > max){
		content = content.substring(0, max) + "..";
	}
	
	return content;
}

function sortJSON(data, key, way) {
    return data.sort(function(a, b) {
        var x = a[key]; var y = b[key];
        if (way === 'asc' ) { return ((x < y) ? -1 : ((x > y) ? 1 : 0)); }
        if (way === 'desc') { return ((x > y) ? -1 : ((x < y) ? 1 : 0)); }
    });
}

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}

function validateRequiredField(field, minimum){
	
	if(minimum == null){
		minimum = 0;
	}
	
	var return_value = false;
	
	if($.trim(field.val()).length > minimum){
		return_value = true;
	}
	
	return return_value;
	
}

function validateFieldByRegularExpression(field, expressions_array){
	
		
	if(expressions_array == null){
		
		expressions_array = new Array();
		
		expressions_array.push({expression: /\d/, message: "Password has to contain at least one digit. \n"});
		expressions_array.push({expression: /[a-zA-Z]/, message: "Password has to contain at least one letter. \n"});
		expressions_array.push({expression: /^.{6,}$/, message: "Password has to be at least 6 in length. \n"});
		
		// expression = /^(?=.*\d)(?=.*[a-zA-Z]).{6,}$/i;
	}
	
	var return_flag = true;
	var return_message = "";
	
	var value = $.trim(field.val());
	
	$.each(expressions_array, function(index, expression){
		var search = value.search(expression.expression);
		if(search == -1){
			return_flag = false;
			return_message += expression.message;
		}
	});
	
	var return_value = {flag: return_flag, message: return_message};
	
	return return_value;
	
}

var guid = (function() {
  function s4() {
    return Math.floor((1 + Math.random()) * 0x10000)
               .toString(16)
               .substring(1);
  }
  return function() {
    return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
           s4() + '-' + s4() + s4() + s4();
  };
})();


function showShade(){
	$("#shade").show();
	$("#shade").animate({"opacity": 0.7}, 500);
}

function hideShade(){

	var count_of_displaying_shade = 0;
	$.each($(".shade_inner"), function(i, element){
		if($(this).css("display") == "block"){
			count_of_displaying_shade ++;
		}
	});
	
	if(count_of_displaying_shade < 2){
		$("#shade").animate({"opacity": 0.0}, 500, function(){
			$(this).hide();
		});
	}
	
}

// reload the current page, which will redirect to the url with a page parameter.
// this is effective because of the sub-menus in the left bar.
function reloadPage(){

	var page_index = 0;
	var found = false;
		
	$.each($(".sso_main_leftbar_container_item"), function(i, element){
		if(found) return false;
		if($(this).hasClass("sso_main_leftbar_container_item_menu")){
			$.each($(this).next().children(".sso_main_leftbar_container_item_option"), function(j, element){
				// alert("alert in option")
				if($(this).hasClass("sso_main_leftbar_container_item_selected")){
					// page_index ++;
					found = true;
					return false;
				} else {
					page_index ++;
				}
				// alert("alert in option " + count_index);
			});
		} else {
			// alert("alert in item")
			if($(this).hasClass("sso_main_leftbar_container_item_selected")){
				// alert("hit in item: " + count_index + " " + index);
//				$(this).addClass("sso_main_leftbar_container_item_selected");
//				text = $(this).text();
				found = true;
				return false;
			} else {
				page_index ++;
			}
		}
	});
		
	var current_url = window.location.href;
	
	var url_page_index = current_url.search("page=");

	var redirect_url = current_url;
	if(url_page_index == -1){
		// I am assuming that there could only be one parameter passed in, which is page.
		redirect_url = redirect_url + "?page=" + page_index;
	} else {
		redirect_url = redirect_url.substr(0, url_page_index + 5) + page_index + redirect_url.substr(url_page_index + 6);
	}
		
	window.location.href = redirect_url;
		
}

// samlTable plug-in
(function($){
	
	var local_options = new Array();
	// language: 1 - EN; 2 - ZH
	var local_language = 1;
	
	var methods = {
			
        init : function(i, options, language) {
        	if(language != null){
        		local_language = language
        	}
        	local_options.push(options);
        	
        	var base_element = $(this);
        	$(this).find("tr").attr("class", "samlTable_header");
        	$(this).find("tr").append("<th></th>");
        	
        	// create the add button
        	var add_element = "<tr class='samlTable_footer'>";
        	$.each(options, function(i, option){
        		add_element += "<td style='border: 0;'></td>";
        	});
        	add_element += "<td><button class='samlTable_addRow'>" + (local_language == 1 ? "Add" : "添加") + "</button></td></tr>";
        	
        	$(this).append(add_element);
        	
        	// initialize with just 1 row
        	//$(createEmptyTR(options)).insertBefore($(this).find(".samlTable_footer"));
        	
        	// remove the row
        	$(this).on("click", ".samlTable_removeRow", function(){
        		$(this).closest("tr").remove();
        	});
        	
        	// add a new row
        	$(this).on("click", ".samlTable_addRow", function(event){
        		event.preventDefault();
        		$(createEmptyTR(local_options[i], true)).insertBefore(base_element.find(".samlTable_footer"));
        	});
        	
        },
        add : function( index, data, allowDelete ) {
        	var base_element = $(this);
        	
        	var added_tr = $(createEmptyTR(local_options[index], allowDelete));
        	
        	$.each(local_options[index], function(i, option){

        		switch(option.type){
	    		case "text":
					added_tr.find("td").eq(i).find("input").val(data[i]);
					break;
	    		case "textarea":
					added_tr.find("td").eq(i).find("textarea").val(data[i]);
					break;
	    		case "select":
	    			added_tr.find("td").eq(i).find("select").find("option").filter(function () { return ($(this).text() == data[i] || $(this).val() == data[i]); }).prop("selected", true);
	    			break;
	    		case "checkbox":
	    			added_tr.find("td").eq(i).find("input").prop("checked", data[i]);
					break;
				default: 
					added_tr.find("td").eq(i).find("input").val("Unsupported input format.");
					break;
        		}
        		
        	});
        	        	
        	added_tr.insertAfter(base_element.find(".samlTable_header"));
        	
        	return added_tr;
        	        	
        },
        changeOptions: function(index, selectOptions){

        	$.each(local_options[index], function(i, option){
        		for (var j in option) {
        	        if(j == "selectOptions"){
        	        	option[j] = selectOptions;
        	        }
        	    }
        	});
        	
        }
    };

    $.fn.samlTable = function( methodOrOptions ) {
    	
    	
    	if ( methods[methodOrOptions] ) {
            return methods[ methodOrOptions ].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof methodOrOptions === 'object' || ! methodOrOptions ) {
            // Default to "init"
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  methodOrOptions + ' does not exist on $.samlTable' );
        }  
    	
        return this;
        
    };
    
    // create the html of a row based on input types
    function createEmptyTR(options, allowDelete){
    	
    	var tr_html = "<tr>";
        $.each(options, function(i, option){
        	tr_html += "<td>";
        	var field_element = null;
    		switch(option.type){
	    		case "text":
	    			if(option.disabled != null && option.disabled == true){
						field_element = "<input type='text' disabled/>";
	    			} else {
						field_element = "<input type='text' />";
	    			}
					break;
	    		case "textarea":
					field_element = "<textarea />";
					break;
	    		case "select":
					field_element = "<select>";
					$.each(option.selectOptions, function(i, option){
						field_element += "<option value='" + option.optionId + "'>" + option.optionValue + "</option>";
					});
					field_element += "</select>";
					break;
	    		case "checkbox":
					field_element = "<input type='checkbox' />";
					break;
				default: 
					field_element = "<span>Error! No Match</span>";
					break;
    		}
    		tr_html += field_element;
    		tr_html += "</td>";
    	});
        if(allowDelete){
            tr_html += "<td><button class='samlTable_removeRow'>" + (local_language == 1 ? "Remove" : "删除") + "</button></td>";
        } else{
            tr_html += "<td></td>";
        }
        tr_html += "</tr>";
    	return tr_html;
    }

})( jQuery );
