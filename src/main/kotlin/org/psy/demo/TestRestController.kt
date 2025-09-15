package org.psy.demo


import org.psy.demo.app.sticker.usecase.GetStickerUseCase
import org.psy.demo.core.sticker.domain.Sticker
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestRestController(
    val stickerUseCase: GetStickerUseCase
) {
    @GetMapping("/api/CLIENT/v3.0/test")
    fun test2(): String {
        println("health Check")
        return "tset.html"
    }
    @GetMapping("/test/3")
    fun getStickers(): Sticker {
        val data = stickerUseCase.getSticker(1)
        return data
    }
}

