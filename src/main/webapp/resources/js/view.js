const replyContainer = document.getElementById("reply-container");
const formReply = document['form-reply'];
function loadReplies(idx){
	fetch("/api/board/" + idx)
	.then(response => response.json())
	.then(jsons => {
		let inner = "";
		if(jsons.length == 0){
			inner = "<tr><td colspan=3>No Replies Yet</td></tr>"
		}
		else {
			for(let json of jsons){
				inner = inner +
					"<tr><td class='writer'>" +json['writer'] + "</td>" +
					"<td class='content'>" +json['content'] + "</td>" +
					"<td><button type='button' class='delete' onclick='deleteReply("+idx+','+json['idx']+")'>X</button></td>" +
					"</tr>";
			}	
		}
		
		replyContainer.innerHTML = inner;
	});
}
function submitReply(idx){
	const data = {
		writer: formReply.writer.value,
		content: formReply.content.value,
	}
	fetch("/api/board/" + idx, {
		method: "POST",
		headers: {
	    	'Content-Type': 'application/json',
	  	},
	  	body: JSON.stringify(data),
	})
	.then(response => {
		formReply.content.value = "";
		formReply.content.focus();
		if(response.ok){
			loadReplies(idx);
		}
	})
	.catch(console.log)
}
function deleteReply(boardIdx, idx){
	fetch("/api/board/" + boardIdx + "/" + idx, {
		method: "DELETE"
	})
	.then(response => {
		if(response.ok){
			loadReplies(boardIdx);
		} else {
			$("#modal-body").html('Cannot delete the comment.');
    		$("#modal").modal('show');
		}
	})
	.catch(console.log)
}