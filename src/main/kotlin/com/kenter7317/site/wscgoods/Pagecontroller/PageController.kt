package com.kenter7317.site.wscgoods.Pagecontroller

import com.kenter7317.site.wscgoods.service.GoodsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController(
    private val goodsService: GoodsService
) {
    @GetMapping("/wsc-goods")
    fun wscGoodsPage(model: Model): String {
        val availableGoods = goodsService.getAvailableGoods()
        model.addAttribute("goods", availableGoods)
        return "wsc-goods"
    }
}