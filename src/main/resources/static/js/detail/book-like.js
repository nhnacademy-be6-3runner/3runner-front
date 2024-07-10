// like-feature.js

document.addEventListener('DOMContentLoaded', function () {
    const likeButton = document.getElementById('like');
    if (likeButton) {
        likeButton.addEventListener('click', function (event) {
            event.stopPropagation(); // 부모 요소로의 클릭 이벤트 전파 방지
            likeButton.classList.toggle('active');
            const path = likeButton.querySelector('path');
            if (likeButton.classList.contains('active')) {
                path.setAttribute('fill', '#e30d0d');
                increaseLikeCount();
            } else {
                path.setAttribute('fill', '#777');
                decreaseLikeCount();
            }
        });
    }
    updateLikeCount();
    updateLikeButtonStatus(); // 페이지 로드 시 좋아요 상태 초기화
});

function increaseLikeCount() {
    const bookId = getBookIdFromUrl();
    $.ajax({
        url: `/api/books/${bookId}/likes`,
        type: 'POST',
        success: function () {
            console.log('Successfully increased like count');
            updateLikeCount();
        },
        error: function (xhr, status, error) {
            console.error('Error increasing like count:', error);
            console.error('Response Text:', xhr.responseText); // 응답 내용 출력
            console.error('Status:', status);
        }
    });
}

function decreaseLikeCount() {
    const bookId = getBookIdFromUrl();
    console.log(`Decreasing like count for book ID: ${bookId}`);
    $.ajax({
        url: `/api/books/${bookId}/likes/delete`,
        type: 'DELETE',
        success: function () {
            updateLikeCount();
        },
        error: function (xhr, status, error) {
            console.error('Error decreasing like count:', error);
            console.error('Response Text:', xhr.responseText); // 응답 내용 출력
            console.error('Status:', status);
        }
    });
}

function updateLikeCount() {
    const bookId = getBookIdFromUrl();
    console.log(`Fetching like count for book ID: ${bookId}`);
    $.ajax({
        url: `/api/books/${bookId}/likes`,
        type: 'GET',
        success: function (response) {
            console.log(`Received like count response: `, response);
            document.getElementById('likeCount').innerText = response.body.data;
        },
        error: function (xhr, status, error) {
            console.error('Error fetching like count:', error);
            console.error('Response Text:', xhr.responseText); // 응답 내용 출력
            console.error('Status:', status);
        }
    });
}

function updateLikeButtonStatus() {
    const bookId = getBookIdFromUrl();
    $.ajax({
        url: `/api/books/${bookId}/likes/status`,
        type: 'GET',
        success: function (response) {
            console.log(`Received like status response: `, response);
            if (response.body.data) {
                activateLikeButton();
            } else {
                deactivateLikeButton();
            }
        },
        error: function (xhr, status, error) {
            console.error('Error fetching like status:', error);
            console.error('Response Text:', xhr.responseText); // 응답 내용 출력
            console.error('Status:', status);
        }
    });
}

function activateLikeButton() {
    const likeButton = document.getElementById('like');
    likeButton.classList.add('active');
    const path = likeButton.querySelector('path');
    path.setAttribute('fill', '#e30d0d');
}

function deactivateLikeButton() {
    const likeButton = document.getElementById('like');
    likeButton.classList.remove('active');
    const path = likeButton.querySelector('path');
    path.setAttribute('fill', '#777');
}

function getBookIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}
