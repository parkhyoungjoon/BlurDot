package com.mysite.blurdot.email;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mysite.blurdot.user.SiteUser;
import com.mysite.blurdot.user.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final UserRepository userRepository;
    private final EmailRepository emailVerificationRepository;
    private final JavaMailSender javaMailSender;
    private final String senderEmail = "your-email@example.com"; // 발신자 이메일 주소

    // 인증 번호 생성 메서드
    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) { // 총 8자리 인증 번호 생성
            int idx = random.nextInt(3); // 0~2 사이의 값을 랜덤하게 선택

            switch (idx) {
                case 0:
                    key.append((char) (random.nextInt(26) + 97)); // a~z
                    break;
                case 1:
                    key.append((char) (random.nextInt(26) + 65)); // A~Z
                    break;
                case 2:
                    key.append(random.nextInt(10)); // 0~9
                    break;
            }
        }
        return key.toString();
    }

    // 인증 이메일 메시지 생성 메서드
    public MimeMessage createMessage(String email, String number) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true); // Helper 사용
            messageHelper.setFrom(senderEmail);
            messageHelper.setTo(email);
            messageHelper.setSubject("[BlurDot] 이메일 인증 번호 발송");

            String body = "<html><body style='background-color: #000000; margin: 0 auto; max-width: 600px; word-break: break-word; padding-top: 50px; color: #ffffff;'>";
            body += "<h1 style='padding-top: 50px; font-size: 30px;'>이메일 주소 인증</h1>";
            body += "<p style='padding-top: 20px; font-size: 18px; opacity: 0.6; line-height: 30px; font-weight: 400;'>안녕하세요? BlurDot 관리자 입니다.<br />";
            body += "BlurDot 서비스 사용을 위해 회원가입시 고객님께서 입력하신 이메일 주소의 인증이 필요합니다.<br />";
            body += "하단의 인증 번호로 이메일 인증을 완료하시면, 정상적으로 BlurDot 서비스를 이용하실 수 있습니다.<br />";
            body += "항상 최선의 노력을 다하는 BlurDot이 되겠습니다.<br />";
            body += "감사합니다.</p>";
            body += "<div class='code-box' style='margin-top: 50px; padding-top: 20px; color: #000000; padding-bottom: 20px; font-size: 25px; text-align: center; background-color: #f4f4f4; border-radius: 10px;'>" + number + "</div>";
            body += "</body></html>";
            messageHelper.setText(body, true);
        } catch (MessagingException e) {
            log.error("이메일 생성 중 오류 발생", e);
        }
        return mimeMessage;
    }

    // 메일 발송 메서드
    public String sendMail(String email, SiteUser user) {
        String number = createNumber(); // 인증 번호 생성
        log.info("생성된 인증 번호: {}", number);
        MimeMessage mimeMessage = createMessage(email, number);

        try {
            javaMailSender.send(mimeMessage); // 메일 전송
            log.info("이메일 전송 완료: {}", email);
            EmailVerification emailVerification = new EmailVerification();
            emailVerification.setUser(user);
            emailVerification.setVerificationCode(number);
            emailVerification.setExpirationTime(LocalDateTime.now().plusMinutes(10));
            emailVerificationRepository.save(emailVerification);
        } catch (Exception e) {
            log.error("이메일 전송 중 오류 발생", e);
        }
        return number; // 인증 번호 반환
    }
    
    public String verifyEmail(String email, String code) {
        // 이메일로 사용자 조회
        SiteUser user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 인증 코드 검증
        EmailVerification verification = emailVerificationRepository.findByUserAndVerificationCode(user, code)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 인증 코드입니다."));

        if (verification.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("인증 코드가 만료되었습니다.");
        }

        // 인증 완료 처리
        user.setIsVerified(true);
        userRepository.save(user);

        return "이메일 인증이 완료되었습니다.";
        
    }
}
