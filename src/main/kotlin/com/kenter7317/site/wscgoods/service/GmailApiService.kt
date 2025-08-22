package com.kenter7317.site.wscgoods.service

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.model.Message
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.*
import java.util.*
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.Base64
import org.slf4j.LoggerFactory

@Service
class GmailApiService {

    private val logger = LoggerFactory.getLogger(GmailApiService::class.java)

    @Value("\${gmail.application.name}")
    private lateinit var applicationName: String

    @Value("\${gmail.service.account.key.path}")
    private lateinit var serviceAccountKeyPath: String

    @Value("\${gmail.from.email}")
    private lateinit var fromEmail: String

    companion object {
        private val JSON_FACTORY = GsonFactory.getDefaultInstance()
        private val SCOPES = listOf(GmailScopes.GMAIL_SEND)
    }

    @Throws(Exception::class)
    private fun getGmailService(): Gmail {
        // 서비스 계정 키 파일 로드
        val inputStream = this::class.java.classLoader.getResourceAsStream(serviceAccountKeyPath)
            ?: throw FileNotFoundException("서비스 계정 키 파일을 찾을 수 없습니다: $serviceAccountKeyPath")

        // 서비스 계정 인증 정보 생성
        val credentials = GoogleCredentials.fromStream(inputStream)
            .createScoped(SCOPES)

        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()

        return Gmail.Builder(
            httpTransport,
            JSON_FACTORY,
            HttpCredentialsAdapter(credentials)
        )
            .setApplicationName(applicationName)
            .build()
    }

    @Throws(Exception::class)
    private fun createEmail(to: String, subject: String, bodyText: String): MimeMessage {
        val props = Properties()
        val session = Session.getDefaultInstance(props, null)

        val email = MimeMessage(session)
        email.setFrom(InternetAddress(fromEmail))
        email.addRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(to))
        email.subject = subject
        email.setText(bodyText, "utf-8", "html")
        return email
    }

    @Throws(Exception::class)
    private fun createMessageWithEmail(emailContent: MimeMessage): Message {
        val buffer = ByteArrayOutputStream()
        emailContent.writeTo(buffer)
        val bytes = buffer.toByteArray()
        val encodedEmail = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
        val message = Message()
        message.raw = encodedEmail
        return message
    }

    fun sendEmail(to: String, subject: String, bodyText: String): Boolean {
        return try {
            logger.info("Gmail API (서비스 계정)를 통해 이메일 발송 시도: $to")
            val service = getGmailService()
            val email = createEmail(to, subject, bodyText)
            val message = createMessageWithEmail(email)

            val result = service.users().messages().send("me", message).execute()
            logger.info("이메일 발송 성공: ${result.id}")
            true
        } catch (e: FileNotFoundException) {
            logger.error("서비스 계정 키 파일을 찾을 수 없습니다: ${e.message}")
            logger.error("Google Cloud Console에서 서비스 계정을 생성하고 키 파일을 다운로드하여 src/main/resources/ 디렉토리에 '${serviceAccountKeyPath}' 이름으로 저장해주세요.")
            false
        } catch (e: Exception) {
            logger.error("Gmail API 이메일 발송 실패: ${e.message}", e)
            false
        }
    }
}
