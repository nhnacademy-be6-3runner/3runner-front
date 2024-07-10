document.addEventListener('DOMContentLoaded', function () {
    const likeButton = document.getElementById('like');
    if (likeButton) {
        likeButton.addEventListener('click', function (event) {
            event.stopPropagation(); // 부모 요소로의 클릭 이벤트 전파 방지
            if (likeButton.classList.contains('active')) {
                decreaseLikeCount(likeButton);
            } else {
                increaseLikeCount(likeButton);
            }
        });
    }
    updateLikeCount();
    updateLikeButtonStatus(); // 페이지 로드 시 좋아요 상태 초기화
});

function increaseLikeCount(likeButton) {
    const reviewId = getReviewIdFromUrl();
    $.ajax({
        url: `/api/review/${reviewId}/like`,
        type: 'POST',
        success: function () {
            console.log('Successfully increased like count');
            likeButton.classList.add('active');
            const path = likeButton.querySelector('path');
            path.setAttribute('fill', '#005cfa');
            updateLikeCount();
        },
        error: function (xhr, status, error) {
            console.error('Error increasing like count:', error);
            console.error('Response Text:', xhr.responseText); // 응답 내용 출력
            console.error('Status:', status);

            // 에러 메시지 파싱
            const response = JSON.parse(xhr.responseText);
            if (response.body && response.body.data && response.body.data.title) {
                showAlert(response.body.data.title);
            } else {
                showAlert('자신의 리뷰에는 추천을 누를 수 없습니다.');
            }
        }
    });
}

function decreaseLikeCount(likeButton) {
    const reviewId = getReviewIdFromUrl();
    console.log(`Decreasing like count for review ID: ${reviewId}`);
    $.ajax({
        url: `/api/review/${reviewId}/like`,
        type: 'DELETE',
        success: function () {
            console.log('Successfully decreased like count');
            likeButton.classList.remove('active');
            const path = likeButton.querySelector('path');
            path.setAttribute('fill', '#777');
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
    const reviewId = getReviewIdFromUrl();
    console.log(`Fetching like count for review ID: ${reviewId}`);
    $.ajax({
        url: `/api/review/${reviewId}/like/count`,
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
    const reviewId = getReviewIdFromUrl();
    $.ajax({
        url: `/api/review/${reviewId}/like/status`,
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
    path.setAttribute('fill', '#005cfa');
}

function deactivateLikeButton() {
    const likeButton = document.getElementById('like');
    likeButton.classList.remove('active');
    const path = likeButton.querySelector('path');
    path.setAttribute('fill', '#777');
}

function getReviewIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}

function showAlert(message) {
    const alertBox = document.createElement('div');
    alertBox.className = 'alert alert-danger';
    alertBox.textContent = message;
    alertBox.style.position = 'fixed';
    alertBox.style.top = '20px';
    alertBox.style.left = '50%';
    alertBox.style.transform = 'translateX(-50%)';
    alertBox.style.zIndex = '10000';
    document.body.appendChild(alertBox);

    setTimeout(() => {
        document.body.removeChild(alertBox);
    }, 3000); // 3초 후에 팝업 제거
}
