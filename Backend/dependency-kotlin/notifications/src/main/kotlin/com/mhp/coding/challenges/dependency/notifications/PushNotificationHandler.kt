package com.mhp.coding.challenges.dependency.notifications

import com.mhp.coding.challenges.dependency.inquiry.Inquiry
import com.mhp.coding.challenges.dependency.inquiry.InquiryService
import com.mhp.coding.challenges.dependency.inquiry.InquiryTrigger
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class PushNotificationHandler(inquiryService: InquiryService) : InquiryTrigger(inquiryService) {
    fun sendNotification(inquiry: Inquiry) {
        logger.info {
            "Sending push notification for: $inquiry"
        }
    }

    override fun triggerInquiry(inquiry: Inquiry) {
        sendNotification(inquiry)
    }
}
