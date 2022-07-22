function valid_chat() {
    var uid = '<%=(String)session.getAttribute("userId")%>';

    if (uid == "null") {
        var url = "/";
        alert("로그인이 필요한 작업입니다 :(");
        window.location.replace(url);
//        jQuery('#form_status').html('<span class="wrong">로그인이 필요한 작업입니다 :(</span>');
//        notice( f.chatButton );
        return false;
    }

    return true;
}

function getToPost(id) {
    console.log(id);
    let f = document.createElement('form');
    let obj;
    obj = document.createElement('input');
    obj.setAttribute('type', 'hidden');
    obj.setAttribute('name', 'itemNo');
    obj.setAttribute('value', id);

    f.appendChild(obj);
    f.setAttribute('method', 'post');
    f.setAttribute('action', 'deleteitem');
    document.body.appendChild(f);
    f.submit();

    return true;
}

function valid_datas( f ){

	if( f.itemName.value == '' ){
		jQuery('#form_status').html('<span class="wrong">Your item must not be empty!</span>');
		notice( f.itemName );
	}else{
	     var itemName = $("#itemName").val();
	     var tradeName = $("#tradeName").val();
	     var content  = $("#content").val();
	     var param = {"itemName":itemName, "tradeName":tradeName, "content":content}
	     console.log(f);

		 jQuery.ajax({

			url: '/register-item',
			type: 'post',
			data: jQuery('form#fruitkha-contact').serialize(),
            dataType: "JSON",
			complete: function(data) {
				jQuery('#form_status').html(data.responseText);
				jQuery('#fruitkha-contact').find('input,textarea').attr({value:''});
				jQuery('#fruitkha-contact').css({opacity:1});
				jQuery('#fruitkha-contact').remove();
			}
		});
		jQuery('#form_status').html('<span class="loading">Sending your message...</span>');
		jQuery('#fruitkha-contact').animate({opacity:0.3});
		jQuery('#fruitkha-contact').find('input,textarea,button').css('border','none').attr({'disabled':''});
	}

	return false;
}

function notice( f ){
	jQuery('#fruitkha-contact').find('input,textarea').css('border','none');
	f.style.border = '1px solid red';
	f.focus();
}

$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        var name = $.trim(this.name),
            value = $.trim(this.value);

        if (o[name]) {
            if (!o[name].push) {
                o[name] = [o[name]];
            }
            o[name].push(value || '');
        } else {
            o[name] = value || '';
        }
    });
    return o;
}