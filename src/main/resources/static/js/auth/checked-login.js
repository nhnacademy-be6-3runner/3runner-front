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
        document.getElementById('loginButton').style.display = 'none';
        document.getElementById('signupButton').style.display = 'none';
        document.getElementById('logoutButton').style.display = 'inline-block';
        document.getElementById('mypageButton').style.display = 'inline-block';
        document.getElementById('loginLink').style.display = 'none';
        document.getElementById('logoutLink').style.display = 'inline-block';
        document.getElementById('profileDropdown').style.display = 'block';
        document.getElementById('profileMenu').style.display = 'block';
    } else {
        document.getElementById('loginButton').style.display = 'inline-block';
        document.getElementById('signupButton').style.display = 'inline-block';
        document.getElementById('logoutButton').style.display = 'none';
        document.getElementById('mypageButton').style.display = 'none';
        document.getElementById('loginLink').style.display = 'inline-block';
        document.getElementById('logoutLink').style.display = 'none';
        document.getElementById('profileDropdown').style.display = 'none';
        document.getElementById('profileMenu').style.display = 'none';
    }
}

function logout() {
    window.location.href = '/logout';
}

function mypage() {
    window.location.href = '/member/mypage';
}

function login() {
    window.location.href = '/login';
}

function signup() {
    window.location.href = 'member/createForm';
}