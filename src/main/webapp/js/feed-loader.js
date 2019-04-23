
// Fetch messages and add them to the page.
function fetchMessages(){
    const url = '/schedulefeed';
    fetch(url).then((response) => {
        return response.json();
    }).then((messages) => {
        const messageContainer = document.getElementById('message-container');
        if(messages.length == 0){
            messageContainer.innerHTML = '<p>There are no posts yet.</p>';
        }
        else{
            messageContainer.innerHTML = '';
        }
        messages.forEach((message) => {
            const messageDiv = buildEventDiv(message);
            messageContainer.appendChild(messageDiv);
        });
    });
}

function buildEventDiv(event){
    const usernameDiv = document.createElement('div');
    usernameDiv.classList.add("left-align");
    usernameDiv.appendChild(document.createTextNode(event.title));

    const timeDiv = document.createElement('div');
    timeDiv.classList.add('right-align');
    timeDiv.appendChild(document.createTextNode(event.startTime));

    const headerDiv = document.createElement('div');
    headerDiv.classList.add('message-header');
    headerDiv.appendChild(event.endTime);
    headerDiv.appendChild(event.description);

    const bodyDiv = document.createElement('div');
    bodyDiv.classList.add('message-body');
    bodyDiv.appendChild(document.createTextNode(event.text));

    const messageDiv = document.createElement('div');
    messageDiv.classList.add("message-div");
    messageDiv.appendChild(headerDiv);
    messageDiv.appendChild(bodyDiv);

    return messageDiv;
}

// Fetch data and populate the UI of the page.
function buildUI(){
    fetchMessages();
}