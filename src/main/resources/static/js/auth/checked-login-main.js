document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus();
});

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function checkLoginStatus() {
    const accessToken = getCookie('Access');
    console.log('Access Token:', accessToken);

    if (accessToken) {
        document.getElementById('loginLink').style.display = 'none';
        document.getElementById('logoutLink').style.display = 'inline-block';
        document.getElementById('dropdownUser').style.display = 'block';
    } else {
        document.getElementById('loginLink').style.display = 'inline-block';
        document.getElementById('logoutLink').style.display = 'none';
        document.getElementById('dropdownUser').style.display = 'none';
    }
}
function mypage() {
    window.location.href = '/mypage';
}