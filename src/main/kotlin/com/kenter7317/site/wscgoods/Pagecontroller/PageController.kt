package com.kenter7317.site.wscgoods.Pagecontroller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {
    @GetMapping("/wsc-goods")
    fun wscGoodsPage(): String {
        return "wsc-goods"
    }
}