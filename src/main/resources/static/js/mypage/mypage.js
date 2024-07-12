let reviewPage = 0;
let commentPage = 0;

document.addEventListener('DOMContentLoaded', function () {
    loadReviews(reviewPage);
    loadUserComments(commentPage);
});

function loadReviews(page = 0) {
    const url = `/api/books/mypage/reviews?page=${page}`;
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
            console.log("Review data:", data);
            const reviewListItems = document.getElementById('reviewsContent');
            if (page === 0) {
                reviewListItems.innerHTML = "";
                reviewPage = 0;
            }

            data.body.data.content.forEach(review => {
                const reviewItem = document.createElement('div');
                reviewItem.className = 'list-group-item';
                reviewItem.innerHTML = `
                        <div class="row g-0 align-items-center">
                            <div class="col-md-2">
                                <img src="/api/images/review/download?fileName=${review.imgUrl}" class="img-fluid rounded-start" alt="review img">
                            </div>
                            <div class="col-md-10">
                                <div class="review-content" data-id="${review.reviewId}">
                                    <div class="rating">
                                        ${Array.from({ length: review.rating }, () => '<i class="bi bi-star-fill"></i>').join('')}
                                        ${Array.from({ length: 5 - review.rating }, () => '<i class="bi bi-star"></i>').join('')}
                                    </div>
                                    <h5 class="review-title">${review.title}</h5>
                                </div>
                            </div>
                        </div>
                    `;
                reviewListItems.appendChild(reviewItem);
                console.log('review Id', review.reviewId)

                // 리뷰 클릭 이벤트 리스너 추가
                reviewItem.addEventListener('click', () => {
                    window.location.href = `http://localhost:3000/review/${review.reviewId}`;
                });

            });

            const pagination = document.getElementById('reviewPagination');
            pagination.innerHTML = '';

            const totalPages = data.body.data.totalPages;
            const currentPage = data.body.data.number;

            for (let i = 0; i < totalPages; i++) {
                const pageButton = document.createElement('button');
                pageButton.textContent = i + 1;
                pageButton.classList.add('btn', 'btn-secondary', 'me-2');
                if (i === currentPage) {
                    pageButton.classList.add('active');
                }
                pageButton.addEventListener('click', function () {
                    loadReviews(i);
                });
                pagination.appendChild(pageButton);
            }
        })
        .catch(error => console.error('Error loading reviews:', error));
}

function loadUserComments(page = 0) {
    const url = `/api/mypage/comments?page=${page}&size=10`;

    fetch(url, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            const comments = data.body?.data?.content || [];
            const commentList = document.getElementById('commentsContent');
            commentList.innerHTML = '';

            comments.forEach(comment => {
                const commentDiv = document.createElement('div');
                commentDiv.classList.add('comment');
                const createdAt = new Date(comment.createdAt).toLocaleString(); // 날짜와 시간 표시
                commentDiv.innerHTML = `
                <p><small class="text-muted">${comment.commentId}</small></p>
                <p>${comment.content}</p>
                <p><small class="text-muted">작성일: ${createdAt}</small></p>
                <button class="delete-btn" onclick="deleteComment(${comment.commentId})">&times;</button>
            `;
                commentList.appendChild(commentDiv);
            });

            const pagination = document.getElementById('commentPagination');
            pagination.innerHTML = '';

            const totalPages = data.body.data.totalPages;
            const currentPage = data.body.data.number;

            for (let i = 0; i < totalPages; i++) {
                const pageButton = document.createElement('button');
                pageButton.textContent = i + 1;
                pageButton.classList.add('btn', 'btn-secondary', 'me-2');
                if (i === currentPage) {
                    pageButton.classList.add('active');
                }
                pageButton.addEventListener('click', function () {
                    loadUserComments(i);
                });
                pagination.appendChild(pageButton);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function loadMoreReviews() {
    reviewPage++;
    loadReviews(reviewPage);
}

function loadMoreComments() {
    commentPage++;
    loadUserComments(commentPage);
}

function deleteComment(commentId) {
    const url = `/api/comments/${commentId}`;

    fetch(url, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Comment deleted:', data);
            loadUserComments(commentPage); // 댓글 삭제 후 현재 페이지를 다시 로드
        })
        .catch(error => {
            console.error('Error deleting comment:', error);
        });
}
