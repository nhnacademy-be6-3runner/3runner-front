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
});

function increaseLikeCount() {
    const bookId = getBookIdFromUrl();
    $.ajax({
        url: `/api/book-likes/increase/${bookId}`,
        type: 'POST',
        success: function () {
            updateLikeCount();
        },
        error: function (xhr, status, error) {
            console.error('Error increasing like count:', error);
        }
    });
}

function decreaseLikeCount() {
    const bookId = getBookIdFromUrl();
    $.ajax({
        url: `/api/book-likes/decrease/${bookId}`,
        type: 'POST',
        success: function () {
            updateLikeCount();
        },
        error: function (xhr, status, error) {
            console.error('Error decreasing like count:', error);
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
        }
    });
}

function getBookIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    const bookId = pathSegments[pathSegments.length - 1];
    console.log(`Book ID from URL: ${bookId}`); // 디버깅용 로그
    return bookId;
}
