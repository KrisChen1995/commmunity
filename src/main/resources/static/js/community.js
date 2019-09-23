function commentToTarget(targetId, type, content){
	if(!content){
		alert("回复内容不能为空");
		return;
	}
	$.ajax({
		  type: "POST",
		  url: "/comment",
		  contentType: "application/json",
		  data: JSON.stringify({
			  "parentId": targetId ,
		  	"content" : content ,
          "type" : type
	  }),
		  success: function(response){
			  if(response.code == 200){
				  window.location.reload();
			  }
			  else{
				  if(response.code == 2003){
					  var isAccpted = confirm(response.message);
					  if(isAccpted){
						  window.open("https://github.com/login/oauth/authorize?client_id=5ce84fe25a3cc4b80ed9&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
						  window.localStorage.setItem("closable", true);
					  }
				  }else{
					  alert(response.message);
				  }
				  
			  }
		  },
		  dataType: "json"
		});
}

function post(){
	var questionId = $("#question_id").val();
	var content = $("#comment_content").val();
	commentToTarget(questionId,1,content);
}

function comments(e){
	var commentId = e.getAttribute("data-id");
	var content = $("#input-" + commentId).val();
	commentToTarget(commentId,2,content);
}
/**
 * 
 * 展开二级评论
 * @returns
 */
function comment(e){
	
	var id = e.getAttribute("data-id");
	var comment = $("#comment-"+ id);
	var open = e.getAttribute("open");
	if(open){
		comment.removeClass("in");
		e.removeAttribute("open");
		e.classList.remove("active");
	}
	else{
		var subCommentContainer = $("#comment-" + id);
		if(subCommentContainer.children().length != 1){
			comment.addClass("in");
			e.setAttribute("open", "in");
			e.classList.add("active");
		}else{
			$.getJSON( "/comment/" + id, function( data ) {
				$.each(data.data.reverse(), function (index, comment){
					var mediaLeftElement = $("<div/>", {
						"class": "media-left",
						
					}).append($("<img/>", {
						"class": "media-object img-rounded",
						"src": comment.user.avatarUrl
					}));
					
					var mediaBodyElement = $("<div/>", {
						"class": "media-body",
						
					}).append($("<h5/>", {
						"class": "media-heading",
						"html": comment.user.name
					})).append($("<div/>", {
						"html": comment.content
					})).append($("<div/>", {
						"class": "menu"
					}).append($("<span/>", {
						"class": "pull-right",
						"html": moment(comment.gmtCreate).format('YYYY-MM-DD')
					})));
					
					var mediaElement = $("<div/>", {
						"class": "media",
						
					}).append(mediaLeftElement)
					.append(mediaBodyElement);
					
					var commentElement = $("<div/>", {
						"class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
						
					}).append(mediaElement);
					subCommentContainer.prepend(commentElement)
				});
				comment.addClass("in");
				e.setAttribute("open", "in");
				e.classList.add("active");
			});
		}
			
		
	}
}