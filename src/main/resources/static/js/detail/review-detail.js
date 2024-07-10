document.getElementById('commentForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const commentText = document.getElementById('content').value;
    console.log('폼 내용:', commentText);

    if (!commentText) {
        alert('댓글 내용을 입력하세요.');
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
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('댓글 등록에 실패했습니다.');
        });
});

function obfuscateEmail(email) {
    var parts = email.split('@');
    if (parts[0].length < 3) {
        return '***@' + parts[1];
    }
    return '***' + parts[0].substring(3) + '@' + parts[1];
}