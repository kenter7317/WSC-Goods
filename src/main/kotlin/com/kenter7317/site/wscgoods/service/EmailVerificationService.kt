package com.kenter7317.site.wscgoods.service

import com.kenter7317.site.wscgoods.entity.EmailVerification
import com.kenter7317.site.wscgoods.repository.EmailVerificationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class EmailVerificationService(
    private val emailVerificationRepository: EmailVerificationRepository,
    private val emailFilterService: EmailFilterService,
    private val gmailApiService: GmailApiService
) {

    fun sendVerificationCode(email: String): Boolean {
        // 이메일 필터링 확인
        if (!emailFilterService.isEmailAllowed(email)) {
            return false
        }

        // 6자리 인증번호 생성
        val code = Random.nextInt(100000, 999999).toString()

        // 이메일 인증 정보 저장
        val verification = EmailVerification(
            email = email,
            code = code
        )
        emailVerificationRepository.save(verification)

        // Gmail API를 통한 이메일 발송
        return try {
            val subject = "WSC Goods 인증번호"
            val bodyText = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { text-align: center; background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 20px; }
                        .code { font-size: 32px; font-weight: bold; color: #2c3e50; text-align: center; padding: 20px; background-color: #e9ecef; border-radius: 8px; margin: 20px 0; }
                        .footer { text-align: center; color: #666; font-size: 14px; margin-top: 30px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>WSC Goods 이메일 인증</h1>
                        </div>
                        <p>안녕하세요!</p>
                        <p>WSC Goods 굿즈 신청을 위한 이메일 인증번호를 보내드립니다.</p>
                        <div class="code">$code</div>
                        <p>위 인증번호를 입력하여 굿즈 신청을 완료해주세요.</p>
                        <p><strong>주의사항:</strong></p>
                        <ul>
                            <li>이 인증번호는 10분간 유효합니다.</li>
                            <li>인증번호는 한 번만 사용 가능합니다.</li>
                            <li>타인에게 인증번호를 공유하지 마세요.</li>
                        </ul>
                        <div class="footer">
                            <p>감사합니다.<br>WSC Goods 팀</p>
                        </div>
                    </div>
                </body>
                </html>
            """.trimIndent()

            gmailApiService.sendEmail(email, subject, bodyText)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun verifyCode(email: String, code: String): Boolean {
        val verification = emailVerificationRepository
            .findByEmailAndCodeAndIsVerifiedFalseAndExpiresAtAfter(
                email, code, LocalDateTime.now()
            )

        return if (verification != null) {
            verification.isVerified = true
            emailVerificationRepository.save(verification)
            true
        } else {
            false
        }
    }
}
