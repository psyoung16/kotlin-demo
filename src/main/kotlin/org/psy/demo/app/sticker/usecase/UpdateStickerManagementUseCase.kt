package org.psy.demo.app.sticker.usecase

import jakarta.validation.constraints.Max

interface UpdateStickerManagementUseCase {


    fun createStickerManagement(command: CreateStickerManagementCommand)
    data class CreateStickerManagementCommand(

        val name: String,
        @field:Max(20)
        val tagName: String,
        val writerName: String,
        val imgUrl: String
    ) {
    }


    fun updateStickerManagement(command: UpdateStickerManagementCommand)
    data class UpdateStickerManagementCommand(val id: Long, val name: String, val tagName: String, val writerName: String, val imgUrl: String) {
//        init {
//
//            val validationResult = validateUpdateStickerManagementCommand(this)
//            if (validationResult is Invalid) {
//                throw IllegalArgumentException(validationResult.errors.joinToString() { "[${it.dataPath} : ${it.message}]" })
//            }
//        }
    }
    fun toggleIsShowStickerManagement(id: Long?, isShow: Boolean)
    data class ToggleStickerManagementRequest(val id: Long?, val isShow: Boolean) {
//        init {
//
//            val validationResult = toggleStickerManagementRequest(this)
//            if (validationResult is Invalid) {
//                throw IllegalArgumentException(validationResult.errors.joinToString() { "[${it.dataPath} : ${it.message}]" })
//            }
//        }
    }


    companion object {
//        val validateCreateStickerManagementCommand = Validation<CreateStickerManagementCommand> {
//        }
//        val validateUpdateStickerManagementCommand = Validation<UpdateStickerManagementCommand> {
//        }
//        val toggleStickerManagementRequest = Validation<ToggleStickerManagementRequest> {
//        }


    }

}
