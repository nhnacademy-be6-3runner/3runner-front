let reviewPage = 0;
let reviewSort = 'createdAt,desc';

document.addEventListener('DOMContentLoaded', function () {
    loadReviews(reviewSort, reviewPage);

    // 이벤트 위임을 통해 동적으로 생성된 리뷰 항목에도 이벤트가 작동하도록 설정
    document.getElementById('review-list-items').addEventListener('click', function(event) {
        if (event.target.classList.contains('review-title')) {
            const reviewId = event.target.getAttribute('data-id');
            if (reviewId) {
                event.target.classList.add('click-animation');
                setTimeout(() => {
                    window.location.href = `/review/${reviewId}`;
                }, 100);  // 애니메이션 지속 시간 후 페이지 이동
            }
        }
    });
});

function loadReviews(sort = 'createdAt,desc', page = 0) {
    const bookId = getBookIdFromUrl();
    const url = `/api/books/${bookId}/reviews?sort=${sort}&page=${page}`;
    console.log("Request URL:", url);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const reviewListItems = document.getElementById('review-list-items');
            if (page === 0) {
                reviewListItems.innerHTML = ""; // 기존 목록 초기화 (첫 페이지 로드 시)
                reviewPage = 0; // 페이지 번호 초기화
            }

            data.body.data.content.forEach(review => {
                const reviewItem = document.createElement('div');
                reviewItem.className = 'list-group-item';
                const memberEmail = review.memberEmail;
                const anonymizedEmail = `***${memberEmail.split('@')[0].slice(3)}@${memberEmail.split('@')[1]}`;
                reviewItem.innerHTML = `
                    <img class="review-img" src="/api/images/review/download?fileName=${review.imgUrl}" alt="review img">
                    <div class="review-content">
                        <div class="rating">
                            ${Array.from({ length: review.rating }, () => '<i class="bi bi-star-fill"></i>').join('')}
                            ${Array.from({ length: 5 - review.rating }, () => '<i class="bi bi-star"></i>').join('')}
                        </div>
                        <h5 class="review-title" data-id="${review.id}">${review.title}</h5>
                        <small>작성자: ${anonymizedEmail}</small>
                        <div class="like-count" id="like-count-${review.id}">좋아요: 0</div>
                    </div>
                `;
                reviewListItems.appendChild(reviewItem);

                // 리뷰 좋아요 수 가져오기
                fetchLikeCount(review.id);
            });
        })
        .catch(error => console.error('Error loading reviews:', error));
}

// 리뷰 좋아요 불러오기
function fetchLikeCount(reviewId) {
    const url = `/api/reviewLikes/${reviewId}/count`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const likeCountElement = document.getElementById(`like-count-${reviewId}`);
            likeCountElement.textContent = `좋아요: ${data.count}`;
        })
        .catch(error => console.error('Error fetching like count:', error));
}

function loadMoreReviews() {
    reviewPage++;
    loadReviews(reviewSort, reviewPage);
}

function changeReviewSorting() {
    const reviewSortField = document.getElementById('reviewSort').value;
    const reviewSortOrder = document.getElementById('reviewSortOrder').value;

    reviewSort = `${reviewSortField},${reviewSortOrder}`; // 현재 정렬 상태 업데이트
    reviewPage = 0; // 페이지 번호 초기화
    loadReviews(reviewSort, reviewPage); // 새로 정렬된 리뷰 목록 로드
}

function getBookIdFromUrl() {
    const pathSegments = window.location.pathname.split('/');
    return pathSegments[pathSegments.length - 1];
}