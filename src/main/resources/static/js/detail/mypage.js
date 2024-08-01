document.addEventListener('DOMContentLoaded', function() {
    const orderBtn = document.getElementById('orderBtn');
    const profileBtn = document.getElementById('profileBtn');
    const orderDelivery = document.getElementById('orderDelivery');
    const userProfile = document.getElementById('userProfile');

    orderBtn.addEventListener('click', function() {
        orderDelivery.classList.remove('hidden');
        userProfile.classList.add('hidden');
    });

    profileBtn.addEventListener('click', function() {
        fetchUserProfile().then(() => {
            userProfile.classList.remove('hidden');
            orderDelivery.classList.add('hidden');
        });
    });

    window.fetchUserProfile = function() {
        return fetch('/member')
            .then(response => response.text())
            .then(html => {
                userProfile.innerHTML = html;
                setUpModalListeners(); // 다시 이벤트 리스너 설정
            })
            .catch(error => console.error('Error loading the profile data:', error));
    }

    function setUpModalListeners() {
        var editModal = document.getElementById('memberEditModal');
        var editCloseButton = document.getElementById('close'); // editModal 내의 close 버튼 선택
        var editBtn = document.getElementById('editMemberBtn');
        var changePasswordBtn = document.getElementById('changePasswordBtn');
        var deleteAccountBtn = document.getElementById('deleteAccountBtn');

        if (deleteAccountBtn) {
            deleteAccountBtn.onclick = function () {
                fetch('/member', {
                    method: 'DELETE'
                }).then(response => response.body)
                    .then(data => {
                        if (data) {
                            alert('회원 탈퇴 성공');
                            window.location.href = '/';
                        } else {
                            alert('회원 탈퇴 실패');
                        }
                    }
                )
            }
        }
        if (editBtn) {
            editBtn.onclick = function () {
                editModal.style.display = 'block';
            };
        }

        if (editCloseButton) {
            editCloseButton.onclick = function () {
                editModal.style.display = 'none'; // editModal 닫기
            };
        }

        var saveBtn = document.getElementById('editMemberForm');
        if (saveBtn) {
            saveBtn.onsubmit = function (event) {
                event.preventDefault(); // 폼의 기본 제출 방지
                if (!saveBtn.checkValidity()) {
                    saveBtn.reportValidity();
                    return;
                }
                var formData = new FormData(saveBtn);

                var object = {};
                formData.forEach(function (value, key) {
                    object[key] = value;
                });
                var json = JSON.stringify(object);

                fetch('/member', {
                    method: 'PUT', // HTTP 메소드 지정
                    headers: {
                        'Content-Type': 'application/json', // 콘텐츠 타입 지정
                    },
                    body: json // 요청 본문에 JSON 데이터 첨부
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data) {
                            alert('정보가 업데이트 되었습니다.');
                            fetchUserProfile(); // 페이지 새로고침
                        } else {
                            alert('정보 업데이트에 실패했습니다.');
                        }
                    })
                    .catch(error => console.error('Error:', error));
            };
        }

        // 주소 모달 관련 설정
        var addressModal = document.getElementById('addressModal');
        var addressCloseButton = document.getElementById('close-2');
        var createAddressBtn = document.getElementById('createAddressBtn');
        var saveAddressBtn = document.getElementById('saveBtn');

        if (createAddressBtn) {
            createAddressBtn.onclick = function () {
                addressModal.style.display = 'block';
            };
        }

        if (addressCloseButton) {
            addressCloseButton.onclick = function () {
                addressModal.style.display = 'none';
            };
        }

        if (saveAddressBtn) {
            saveAddressBtn.onclick = function (event) {
                event.preventDefault();
                var form = document.getElementById('addressForm');
                if (!form.checkValidity()) {
                    form.reportValidity();
                    return;
                }
                var formData = new FormData(form);
                var json = JSON.stringify(Object.fromEntries(formData.entries()));
                fetch('/member/address', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json', // 콘텐츠 타입 지정
                    },
                    body: json
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data) {
                            alert('저장 성공');
                            addressModal.style.display = 'none';
                            fetchUserProfile(); // 업데이트된 정보를 다시 가져옴
                        } else {
                            alert('저장 실패');
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('오류 발생');
                    });
            };
        }

        window.onclick = function (event) {
            if (event.target == addressModal) {
                addressModal.style.display = 'none';
            }
        };

        // 비밀번호 변경 모달 관련 설정
        var changePasswordModal = document.getElementById('changePasswordModal');
        var changePasswordCloseButton = document.getElementById('close-3');
        var verifyPasswordBtn = document.getElementById('verifyPasswordBtn');
        var savePasswordBtn = document.getElementById('savePasswordBtn');
        var errorMessage1 = document.getElementById('errorMessage1');
        var errorMessage2 = document.getElementById('errorMessage2');
        var newPasswordInput = document.getElementById('newPassword');
        var confirmNewPasswordInput = document.getElementById('confirmNewPassword');
        var changePasswordForm = document.getElementById('changePasswordForm');

        // 비밀번호 변경 버튼 클릭 이벤트 추가
        if (changePasswordBtn) {
            changePasswordBtn.onclick = function () {
                changePasswordModal.style.display = 'block';
                errorMessage1.style.display = 'none';
                errorMessage2.style.display = 'none';
            };
        }

        if (changePasswordCloseButton) {
            changePasswordCloseButton.onclick = function () {
                changePasswordModal.style.display = 'none';
            };
        }

        if (verifyPasswordBtn) {
            verifyPasswordBtn.onclick = function () {
                const currentPassword = document.getElementById('currentPassword').value;
                fetch('/member/password', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ password: currentPassword })
                }).then(response => response.json())
                    .then(data => {
                        console.log(data);
                        if (data) {
                            console.log("1");
                            errorMessage1.style.display = 'none';
                            newPasswordInput.disabled = false;
                            confirmNewPasswordInput.disabled = false;
                            savePasswordBtn.disabled = false;
                        } else {
                            errorMessage1.style.display = 'block';
                            newPasswordInput.disabled = true;
                            confirmNewPasswordInput.disabled = true;
                            savePasswordBtn.disabled = true;
                        }
                    })
                    .catch(error => console.error('Error:', error));
            };
        }

        if (changePasswordForm) {
            changePasswordForm.onsubmit = function (event) {
                event.preventDefault();

                const newPassword = newPasswordInput.value;
                const confirmNewPassword = confirmNewPasswordInput.value;

                if (newPassword !== confirmNewPassword) {
                    errorMessage2.style.display = 'block';
                    return;
                } else {
                    errorMessage2.style.display = 'none';
                }

                fetch('/member/password', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ password: newPassword })
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data) {
                            alert('비밀번호가 성공적으로 변경되었습니다.');
                            changePasswordModal.style.display = 'none';
                        } else {
                            alert('비밀번호 변경에 실패했습니다.');
                        }
                    })
                    .catch(error => console.error('Error:', error));
            };
        }

        window.deleteAddress = function (addressId) {
            if (confirm('Are you sure you want to delete this address?')) {
                fetch('/member/address/' + addressId, {
                    method: 'DELETE',
                }).then(response => response.json())
                    .then(data => {
                        if (data) {
                            alert('Address deleted successfully');
                            fetchUserProfile();
                        } else {
                            alert('Failed to delete address');
                        }
                    })
                    .catch(error => console.error('Error deleting address:', error));
            }
        };
    }
});
