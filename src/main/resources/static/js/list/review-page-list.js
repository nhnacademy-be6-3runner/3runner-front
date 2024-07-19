let reviewPage = 0;
let reviewSort = 'createdAt,desc';

document.addEventListener('DOMContentLoaded', function () {
    loadReviews(reviewSort, reviewPage);
});

function loadReviews(sort = 'createdAt,desc', page = 0) {
    const bookId = getBookIdFromUrl();
    const url = `/api/books/${bookId}/reviews?sort=${sort}&page=${page}`;
    console.log("Request URL:", url);

    fetch(url, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log("Review data:", data); // 데이터 확인용 로그
            const reviewListItems = document.getElementById('review-list-items');
            if (page === 0) {
                reviewListItems.innerHTML = ""; // 기존 목록 초기화 (첫 페이지 로드 시)
                reviewPage = 0; // 페이지 번호 초기화
            }

            data.body.data.content.forEach(review => {
                console.log("Review:", review); // 각 리뷰 확인용 로그
                const reviewItem = document.createElement('div');
                reviewItem.className = 'list-group-item';
                const memberEmail = review.memberEmail;
                const anonymizedEmail = `***${memberEmail.split('@')[0].slice(3)}@${memberEmail.split('@')[1]}`;
                reviewItem.innerHTML = `
                    <div class="review-content" data-id="${review.id}">
                        <img class="review-img" src="/api/images/review/download?fileName=${review.imgUrl}" alt="review img">
                        <div class="rating">
                            ${Array.from({ length: review.rating }, () => '<i class="bi bi-star-fill"></i>').join('')}
                            ${Array.from({ length: 5 - review.rating }, () => '<i class="bi bi-star"></i>').join('')}
                        </div>
                        <h5 class="review-title">${review.title}</h5>
                        <small>작성자: ${anonymizedEmail}</small>
                    </div>
                `;
                reviewListItems.appendChild(reviewItem);
                console.log('review Id', review.reviewId)

                // 리뷰 클릭 이벤트 리스너 추가
                reviewItem.addEventListener('click', () => {
                    window.location.href = `/review/${review.reviewId}`;
                });

            });
        })
        .catch(error => console.error('Error loading reviews:', error));
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
