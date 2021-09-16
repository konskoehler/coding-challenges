package com.mhp.coding.challenges.dependency.inquiry

import mu.KotlinLogging
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}


abstract class InquiryTrigger(inquiryService: InquiryService) {
    init {
        inquiryService.registerTrigger(this)
    }

    abstract fun triggerInquiry(inquiry: Inquiry)
}


@Component
class InquiryService(
    val triggers: MutableList<InquiryTrigger> = mutableListOf()
) {

    fun registerTrigger(inquiryTrigger: InquiryTrigger) {
        triggers.add(inquiryTrigger)
    }

    fun create(inquiry: Inquiry) {
        logger.info {
            "User sent inquiry: $inquiry"
        }
        triggers.forEach { it.triggerInquiry(inquiry) }
    }
}

data class Inquiry(
    var username: String,
    var recipient: String,
    var text: String,
)
