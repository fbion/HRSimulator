//鑾峰彇璁惧鍒楄〃
var InitiateUserListTable = function() {
    return {
            init: function() {
                var oTable = $('#userdatatable').dataTable({
                	 "bProcessing": true,
                     "sAjaxSource": getRootPath()+"/hr/user/all",
                    "aoColumns": [ 
                                  { "mDataProp": "id" },
                                   { "mDataProp": "text" },
            	                  { "mDataProp": "leader" },
            	                  {"mDataProp":"telephone"},
            	                  { "mDataProp": "leaderCode" },
            	                  { "mDataProp": "fax" },
            	                  { "mDataProp": "id" }
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
                       row.cells[0].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.id+"'><label> <input type='checkbox' value='"+data.id+"'> <span class='text'></span> </label></div>";
                       row.cells[1].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.text+"'>" + data.text + "</div>";
                       row.cells[2].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.leader+"'>" + data.leader + "</div>";
                       row.cells[3].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.telephone+"'>" + data.telephone + "</div>";
                       row.cells[4].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.leaderCode+"'>" + data.leaderCode + "</div>";
                       row.cells[5].innerHTML = "<div style='font-size: 13px;width: 120px;'name='"+data.fax+"'>" + data.fax + "</div>";
                       row.cells[6].innerHTML = "<div style='font-size: 13px' id='user_add'>" +
                  									"<a  class='btn btn-success shiny btn-xs role'><i class='glyphicon glyphicon-play'></i>修改</a>&nbsp;</div>";
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

