document.addEventListener('DOMContentLoaded', function () {
    checkAccessToken();
    loadComments(0); // 페이지 로드 시 첫 번째 페이지 댓글 리스트를 로드합니다.
    formatReviewDates();
    // 리뷰 작성자 이메일 마스킹
    const memberEmailElement = document.getElementById('memberEmail');
    if (memberEmailElement) {
        const originalEmail = memberEmailElement.textContent;
        const maskedEmail = obfuscateEmail(originalEmail);
        memberEmailElement.textContent = maskedEmail;
    }
});

function checkAccessToken() {
    const accessToken = getCookie('Access');
    if (!accessToken) {
        document.getElementById('commentForm').style.display = 'none';
        document.getElementById('loginMessage').style.display = 'block';
    }
}

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

// 댓글 생성
document.getElementById('commentForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const commentText = document.getElementById('content').value;
    console.log('폼 내용:', commentText);

    if (commentText.length < 5 || commentText.length > 100) {
        alert('댓글은 최소 5글자, 최대 100글자까지 작성할 수 있습니다.');
        return;
    }

    const reviewId = document.getElementById('reviewId').value;
    const url = `/api/review/${reviewId}/comments`;
    console.log(reviewId);
    console.log(url);

    const formData = new FormData();
    formData.append('content', commentText);

    fetch(url, {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert('댓글이 성공적으로 작성되었습니다!');
            document.getElementById('content').value = '';
            loadComments(0); // 댓글 작성 후 첫 번째 페이지를 다시 로드합니다.
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('댓글 등록에 실패했습니다.');
        });
});

function obfuscateEmail(email) {
    var parts = email.split('@');
    if (parts[0].length <= 3) {
        return '***@' + parts[1];
    }
    return '***' + parts[0].substring(3) + '@' + parts[1];
}

// 댓글 리스트
function loadComments(page) {
    const reviewId = document.getElementById('reviewId').value;
    const url = `/api/review/${reviewId}/comments?page=${page}&size=20`;

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
            const commentList = document.getElementById('commentList');
            commentList.innerHTML = '';

            comments.forEach(comment => {
                const commentDiv = document.createElement('div');
                commentDiv.classList.add('comment');
                const createdAt = new Date(comment.createdAt).toLocaleString(); // 날짜와 시간 표시
                commentDiv.innerHTML = `
                <p>${comment.content}</p>
                <p><small class="text-muted">작성자: ${obfuscateEmail(comment.memberEmail)}</small></p>
                <p><small class="text-muted">작성일: ${createdAt}</small></p>
            `;
                commentList.appendChild(commentDiv);
            });

            const pagination = document.getElementById('pagination');
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
                    loadComments(i);
                });
                pagination.appendChild(pageButton);
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function formatReviewDates() {
    const createdAtElement = document.getElementById('createdAt');
    const updatedAtElement = document.getElementById('updatedAt');
    if (createdAtElement) {
        createdAtElement.textContent = formatDate(new Date(createdAtElement.textContent));
    }
    if (updatedAtElement) {
        updatedAtElement.textContent = formatDate(new Date(updatedAtElement.textContent));
    }
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}