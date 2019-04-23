/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
    window.location.replace('/');
}

/**
 * Shows the message form if the user is logged in.
 */
function showMessageFormIfLoggedIn() {
    fetch('/login-status')
        .then((response) => {
            return response.json();
        })
        .then((loginStatus) => {
            if (loginStatus.isLoggedIn) {
                const messageForm = document.getElementById('message-form');
                messageForm.action = '/messages?recipient=' + parameterUsername;
                messageForm.classList.remove('hidden');
            }
        });
    //document.getElementById('about-me-form').classList.remove('hidden');

}

/** Fetches messages and add them to the page. */
function fetchMessages() {
    fetch('/messages').then((response) => {
        return response.json();
    }).then((events) => {
        const messagesContainer = document.getElementById('message-container');
        if (events.length == 0) {
            messagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
        } else {
            messagesContainer.innerHTML = '';
        }
        events.forEach((event) => {
            const messageDiv = buildEventDiv(event);
            messagesContainer.appendChild(messageDiv);
        });
    });
}

/**
 * Builds an element that displays the event.
 * @param {Message} event
 * @return {Element}
 */
function buildEventDiv(event) {
    const headerDiv = document.createElement('div');
    headerDiv.classList.add('message-header');
    headerDiv.appendChild(document.createTextNode(
        "Task: " + event.title ));

    const startTimeDiv = document.createElement('div');
    startTimeDiv.classList.add('message-body1');
    startTimeDiv.appendChild(document.createTextNode(
        "Start Time: " + event.startTime));

    const endTimeDiv = document.createElement('div');
    endTimeDiv.classList.add('message-body2');
    endTimeDiv.appendChild(document.createTextNode(
        "End Time: " + event.endTime));

    const descriptionDiv = document.createElement('div');
    descriptionDiv.classList.add('message-body3');
    descriptionDiv.appendChild(document.createTextNode(
        "Task Description: "
        + event.description));

    const spaceDiv = document.createElement('div');
    spaceDiv.classList.add('message-space');
    spaceDiv.appendChild(document.createTextNode(
        "********************************************"));

    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message-div');
    messageDiv.appendChild(headerDiv);
    messageDiv.appendChild(startTimeDiv);
    messageDiv.appendChild(endTimeDiv);
    messageDiv.appendChild(descriptionDiv);
    messageDiv.appendChild(spaceDiv);

    return messageDiv;
}

/** Fetches data and populates the UI of the page. */
function buildUI() {
    showMessageFormIfLoggedIn()
    fetchMessages();
}