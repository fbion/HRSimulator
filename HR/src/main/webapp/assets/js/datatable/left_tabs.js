function leftTabs(seq,chil){
	//seq  父类标签所在位置    chil子类标签所在位置     父:1   子:(1,2)子:(1,3)  父:2    子:(2,4)  子:(2,5)  子一直排序 
	var oTabs=document.getElementById('sidebar'); 
    var aTabsNav=oTabs.querySelectorAll('.nav li'); 
    	aTabsNav[seq].className='open';
        $(aTabsNav[chil]).find("span").addClass("font_color");
//        if(seq==2){
//        	var li = $(".nav li .submenu").show();
//        	$(aTabsNav[1]).children("ul").css("display","none");
//        	var li = $(".nav li").eq(5);
//        	$(li).attr("class","open");
//        }
}  
