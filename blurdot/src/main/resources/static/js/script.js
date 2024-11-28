$(document).ready(function () {
    const radioButtons = $('input[type="radio"][name="userGender"]');
    const labels = $('.radio-lb');

    // 초기 상태 설정
    radioButtons.each(function (index) {
        const radio = $(this);
        const label = labels.eq(index);
        if (radio.is(':checked')) {
            label.addClass('form-checked').removeClass('form-default');
        } else {
            label.addClass('form-default').removeClass('form-checked');
        }
    });

    // 라디오 버튼 클릭 시 상태 업데이트
    radioButtons.on('change', function () {
        radioButtons.each(function (index) {
            const radio = $(this);
            const label = labels.eq(index);
            if (radio.is(':checked')) {
                label.addClass('form-checked').removeClass('form-default');
            } else {
                label.addClass('form-default').removeClass('form-checked');
            }
        });
    });
	
	const globalWarning = $('#global-warning');
    const phoneInput = $('#userPhonenumber');
	const ageInput = $('#userAge');
    const emailInput = $('#userEmail');
    const passwordInput = $('#password');
	

    function showWarning(message) {
        globalWarning.text(message).slideDown(); // 메시지를 설정하고 나타남
        setTimeout(() => globalWarning.slideUp(), 3000); // 3초 후 자동 숨김
    }
	
	function input_bar(rawValue, a, b, c, d){
        if (rawValue.length <= b) {
            return rawValue;
        } else if (rawValue.length <= c) {
            return `${rawValue.slice(a, b)}-${rawValue.slice(b)}`;
        } else {
            return `${rawValue.slice(a, b)}-${rawValue.slice(b, c)}-${rawValue.slice(c,d)}`;
        }
	}
	
	// 휴대폰 번호 검증
    phoneInput.on('input', function () {
        const rawValue = $(this).val().replace(/[^0-9]/g, ''); // 숫자만 남기기
        let formattedValue = '';
		formattedValue = input_bar(rawValue,0,3,7,11)
        $(this).val(formattedValue);
    });
});