// Initialize your app
var myApp = new Framework7({
    modalTitle: 'Memories'
});

// Export selectors engine
var $$ = Dom7;

// Add views
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});
// Build Todo HTML using Template7 template engine
var todoItemTemplateSource = $$('#todo-item-template').html();
var todoItemTemplate = Template7.compile(todoItemTemplateSource);
function buildTodoListHtml() {
	$$.getJSON("/whatever/chat/list.json", {}, function(data){
		var renderedList = todoItemTemplate(data['chats']);
	    $$('.todo-items-list').html(renderedList);
	});
}
// Build HTML on App load
buildTodoListHtml();

var itemTemplateSource = $$('#item-template').html();
var itemTemplate = Template7.compile(itemTemplateSource);
function buildMessagesHtml(chat_id) {
	$$.getJSON("/whatever/chat/" + chat_id + ".json", {}, function(data){
		var renderedList = itemTemplate(data['messages']);
	    $$('.messages').html(renderedList);
	});
}

$$(document).on('pageInit', '.page[data-page="messages"]', function (e) {
	 var chat_id = e.detail.page.url.replace('.html', '');
	 buildMessagesHtml(chat_id);
}); 

// Delete item
$$('.todo-items-list').on('delete', '.swipeout', function () {
    var id = $$(this).attr('data-id') * 1;
    var index;
    for (var i = 0; i < todoData.length; i++) {
        if (todoData[i].id === id) index = i;
    }
    if (typeof(index) !== 'undefined') {
        todoData.splice(index, 1);
        localStorage.td7Data = JSON.stringify(todoData);
    }
});

// Update app when manifest updated 
// http://www.html5rocks.com/en/tutorials/appcache/beginner/
// Check if a new cache is available on page load.
window.addEventListener('load', function (e) {
    window.applicationCache.addEventListener('updateready', function (e) {
        if (window.applicationCache.status === window.applicationCache.UPDATEREADY) {
            // Browser downloaded a new app cache.
            myApp.confirm('A new version of ToDo7 is available. Do you want to load it right now?', function () {
                window.location.reload();
            });
        } else {
            // Manifest didn't changed. Nothing new to server.
        }
    }, false);
}, false);
