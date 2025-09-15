package org.psy.demo.core.sticker.service

import org.psy.demo.app.sticker.response.StickerManagementResponse
import org.psy.demo.app.sticker.usecase.GetStickerManagementUseCase
import org.psy.demo.app.sticker.usecase.UpdateStickerManagementUseCase
import org.psy.demo.common.PageParam
import org.psy.demo.core.vo.CommonStatus
import org.psy.demo.core.sticker.domain.StickerManagement
import org.psy.demo.infra.sticker.repository.StickerRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
private class StickerManagementService(
    private val stickerRepository: StickerRepository,
) : GetStickerManagementUseCase, UpdateStickerManagementUseCase {
    override fun getStickers(tagName: String?, pageParam: PageParam): StickerManagementResponse {
        return stickerRepository.loadStickerManagementsBytagName(tagName, pageParam)
    }

    @Transactional
    override fun createStickerManagement(command: UpdateStickerManagementUseCase.CreateStickerManagementCommand) {
        stickerRepository.createStickerManagement(
            StickerManagement(
                null,
                command.name,
                command.tagName,
                command.imgUrl,
                CommonStatus.ACTIVE,
                command.writerName,
                true, null
            )
        )
    }

    @Transactional
    override fun updateStickerManagement(command: UpdateStickerManagementUseCase.UpdateStickerManagementCommand) {

        stickerRepository.loadStickerManagement(command.id)?.let { st ->
            stickerRepository.createStickerManagement(
                with(command) {
                    StickerManagement(
                        id = id,
                        name = name,
                        tagName = tagName,
                        imgUrl = imgUrl,
                        status = st.status,
                        writerName = writerName,
                        isShow = st.isShow,
                        createdAt = st.createdAt
                    )
                }
            )
        }
    }

    @Transactional
    override fun toggleIsShowStickerManagement(id: Long?, isShow: Boolean) {
        stickerRepository.loadStickerManagement(id)?.let { st ->
            stickerRepository.createStickerManagement(
                with(st) {
                    StickerManagement(
                        id = id,
                        name = name,
                        tagName = tagName,
                        imgUrl = imgUrl,
                        status = status,
                        writerName = writerName,
                        isShow = isShow,
                        createdAt = createdAt
                    )
                }
            )
        }
    }


}
