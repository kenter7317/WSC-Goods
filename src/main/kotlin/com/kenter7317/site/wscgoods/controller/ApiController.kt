package com.kenter7317.site.wscgoods.controller

import com.kenter7317.site.wscgoods.service.EmailFilterService
import com.kenter7317.site.wscgoods.service.EmailVerificationService
import com.kenter7317.site.wscgoods.service.GoodsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class EmailRequest(val email: String)
data class VerificationRequest(val email: String, val code: String)
data class GoodsApplicationRequest(val email: String, val goodsId: Long, val verificationCode: String)
data class EmailFilterRequest(val emails: List<String>)

@RestController
@RequestMapping("/api")
class ApiController(
    private val emailVerificationService: EmailVerificationService,
    private val goodsService: GoodsService,
    private val emailFilterService: EmailFilterService
) {

    @PostMapping("/send-verification")
    fun sendVerificationCode(@RequestBody request: EmailRequest): ResponseEntity<Map<String, Any>> {
        val success = emailVerificationService.sendVerificationCode(request.email)

        return if (success) {
            ResponseEntity.ok(mapOf(
                "success" to true,
                "message" to "인증번호가 발송되었습니다."
            ))
        } else {
            ResponseEntity.badRequest().body(mapOf(
                "success" to false,
                "message" to "허용되지 않은 이메일이거나 발송에 실패했습니다."
            ))
        }
    }

    @PostMapping("/verify-code")
    fun verifyCode(@RequestBody request: VerificationRequest): ResponseEntity<Map<String, Any>> {
        val success = emailVerificationService.verifyCode(request.email, request.code)

        return ResponseEntity.ok(mapOf(
            "success" to success,
            "message" to if (success) "인증이 완료되었습니다." else "인증번호가 올바르지 않거나 만료되었습니다."
        ))
    }

    @GetMapping("/goods")
    fun getAvailableGoods(): ResponseEntity<Any> {
        val goods = goodsService.getAvailableGoods()
        return ResponseEntity.ok(goods)
    }

    @PostMapping("/apply-goods")
    fun applyForGoods(@RequestBody request: GoodsApplicationRequest): ResponseEntity<Map<String, Any>> {
        // 먼저 인증번호 확인
        val isVerified = emailVerificationService.verifyCode(request.email, request.verificationCode)

        if (!isVerified) {
            return ResponseEntity.badRequest().body(mapOf(
                "success" to false,
                "message" to "인증번호가 올바르지 않거나 만료되었습니다."
            ))
        }

        val result = goodsService.applyForGoods(request.email, request.goodsId)
        val success = result == "신청이 완료되었습니다."

        return ResponseEntity.ok(mapOf(
            "success" to success,
            "message" to result
        ))
    }

    @PostMapping("/admin/email-filter")
    fun updateEmailFilter(@RequestBody request: EmailFilterRequest): ResponseEntity<Map<String, Any>> {
        emailFilterService.updateAllowedEmails(request.emails)
        return ResponseEntity.ok(mapOf(
            "success" to true,
            "message" to "이메일 필터가 업데이트되었습니다.",
            "allowedEmails" to emailFilterService.getAllowedEmails()
        ))
    }
}
