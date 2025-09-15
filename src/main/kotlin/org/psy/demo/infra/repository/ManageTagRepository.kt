package org.psy.demo.infra.repository

import org.psy.demo.core.common.domain.ManageTag
import org.psy.demo.core.vo.TagType
import org.psy.demo.infra.jpaEntity.ManageTagJpaEntity
import org.psy.demo.infra.jpaRepository.ManageTagJpaRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.util.Date

@Component
class ManageTagRepository(
    private val manageTagJpaRepository: ManageTagJpaRepository
) {
    fun loadManageTag(position: TagType): List<ManageTag> {
        val manageTags: List<ManageTagJpaEntity> = if (position == TagType.STICKER) {
            manageTagJpaRepository.findManageTagByAvailableProductsNotConsiderDate(position, PageRequest.of(0, 30))
        } else {
            manageTagJpaRepository.findByManagTag(position, Date(), Date(),  PageRequest.of(0, 30))
        }
        return manageTags.map(ManageTagJpaEntity.Companion::mapToDomain)
    }

    fun loadManageTagByChallenge(challengeId: Long?): List<ManageTag> {
        val mtList: List<ManageTagJpaEntity> =
            manageTagJpaRepository.findManagTagByChallengeId(PageRequest.of(0, 12), challengeId)

        return mtList.map(ManageTagJpaEntity.Companion::mapToDomain)
    }


}