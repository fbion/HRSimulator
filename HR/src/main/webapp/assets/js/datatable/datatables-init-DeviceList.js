//鑾峰彇璁惧鍒楄〃
var InitiateDeviceListTable = function() {
    return {
            init: function() {
                //Datatable Initiating
            	
                var oTable = $('#devicedatatable').dataTable({
                	 "bProcessing": true,
                     "sAjaxSource": getRootPath()+"/device/all",
                    "aoColumns": [ 
                                   { "mDataProp": "deviceid" },
            	                  { "mDataProp": "username" },
            	                  {"mDataProp":"countDevice"},
            	                  { "mDataProp": "keyhandle" }
                               ],
                    "aLengthMenu": [
                        [20,50, 100, -1],
                        [20,50, 100, "All"]
                    ],
                    "iDisplayLength": 10,
                    "sPaginationType": "bootstrap",
                    "sDom": "Tflt<'row DTTTFooter'<'col-sm-6'i><'col-sm-6'p>>",
                    "language": {
                        "search": "",
                        "sLengthMenu": "_MENU_",
                        "oPaginate": {
                            "sPrevious": "上一页",
                            "sNext": "下一页"
                        }
                    },
                   "createdRow": function ( row, data, index ) {
                       row.cells[0].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.username+"'><label> <input type='checkbox' value='"+data.deviceid+"'> <span class='text'></span> </label></div>";
                       row.cells[1].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.deviceid+"'>" + data.username + "</div>";
                       row.cells[2].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.countDevice+"'>" + data.countDevice + "</div>";
                       row.cells[3].innerHTML = "<div style='font-size: 13px'>" +
                  									"<a href='#' class='btn btn-success shiny btn-xs role'><i class='glyphicon glyphicon-play'></i>查看KeyHandle</a>&nbsp;</div>";
                    }
                });
                //Delete an Existing Row
//                $('#devicedatatable').on("click", 'a.shiny', function(e) {
//                    e.preventDefault();
//                      var nRow = $(this).closest('tr')[0];
//                      var username=$(nRow).find("div").attr("name");//鑾峰彇褰撳墠琛岀殑id
//                      window.location.href=getRootPath()+"keyHandle.jsp";
//                      $.ajax({
//                        	url:getRootPath()+"/device/v1/getKeyHandle/"+username,
//                        	type:"GET",
//                        	success:function(strData){
//                        		alert()
//                        	}
//                        });
//                });
        }

    };
}();

