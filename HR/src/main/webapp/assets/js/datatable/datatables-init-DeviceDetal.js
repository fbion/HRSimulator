            //����
					$("#userdatatable").on("click","a.shiny",function(e){
		 			var nRow = $(this).closest('tr')[0];
		            var username = $(nRow).find("div").attr("name");//��ȡ��ǰ�е�id
		        	var url = getRootPath()+"hr/user/toEdit/"+ username;
							$(window.document).find("#detail_iframe").attr(
									"src", url);
							$("#myModal1").on(
									"show",
									function() {
										$("#myModal1 a.btn,#Close_btn").on(
												"click",
												function(e) {
													$("#myModal1")
															.modal('hide');
												});
									});
							$("#myModal1").on("hide", function() {
								$("#myModal1 a.btn").off("click");
							});

							$("#myModal1").on("hidden", function() {
								$("#myModal1").remove();
							});
							$("#myModal1").modal({
								"backdrop" : "static",
								"keyboard" : true,
								"show" : true
							});
							$("#Close_detail").bind("click", function() {
								$("#myModal1").modal('hide');
							});
							$("#title_header").html("�û�-keyhandle");
						});