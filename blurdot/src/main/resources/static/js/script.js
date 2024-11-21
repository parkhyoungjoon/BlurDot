$(document).ready(function () {
	/* 라디오 라벨 클릭 이벤트 컨트롤러 */
	function radio_labelform(form_name){
		$(form_name).on('click', function(){
			$(form_name).removeClass('form-checked');
			$(form_name).addClass('form-default');
			$(this).removeClass('form-default');
			$(this).addClass('form-checked');
		});
	}
	radio_labelform('.radio-lb')
	
	const globalWarning = $('#global-warning');
    const phoneInput = $('#phone');
    const emailInput = $('#email');
    const passwordInput = $('#password');

    function showWarning(message) {
        globalWarning.text(message).slideDown(); // 메시지를 설정하고 나타남
        setTimeout(() => globalWarning.slideUp(), 3000); // 3초 후 자동 숨김
    }

    // 휴대폰 번호 검증
    phoneInput.on('input', function () {
        const rawValue = $(this).val().replace(/[^0-9]/g, ''); // 숫자만 남기기
        let formattedValue = '';
        if (rawValue.length <= 3) {
            formattedValue = rawValue;
        } else if (rawValue.length <= 7) {
            formattedValue = `${rawValue.slice(0, 3)}-${rawValue.slice(3)}`;
        } else {
            formattedValue = `${rawValue.slice(0, 3)}-${rawValue.slice(3, 7)}-${rawValue.slice(7)}`;
        }
        $(this).val(formattedValue);

        if ($(this).val() && rawValue.length < 10) {
            showWarning('휴대폰 번호는 10자리 이상이어야 합니다.');
        }
    });

    // 폼 제출 시 검증
    $('#join_form').on('submit', function (e) {
        e.preventDefault(); // 폼 제출 막기
        let errorMessage = '';

        // 휴대폰 번호 검증
        if (phoneInput.val().replace(/[^0-9]/g, '').length < 10) {
            errorMessage += '유효한 휴대폰 번호를 입력해주세요.';
        }

        // 이메일 검증
        if (!emailInput.val().includes('@')) {
            errorMessage += '유효한 이메일 주소를 입력해주세요.';
        }

        // 비밀번호 검증
        if (passwordInput.val().length < 8) {
            errorMessage += '비밀번호는 8자리 이상이어야 합니다.';
        }
        if (errorMessage) {
            showWarning(errorMessage); // 경고 메시지 표시
        } else {
            alert('회원가입이 성공적으로 완료되었습니다!');
			//e.currentTarget.submit();
        }
    });
});