package org.psy.demo.infra.repository

import org.psy.demo.infra.jpaEntity.StoryJpaEntity
import org.psy.demo.infra.jpaEntity.StoryStatus
import org.psy.demo.infra.mapper.FileMapper
import org.psy.demo.infra.mapper.StoryMapper
import org.psy.demo.core.common.domain.File
import org.psy.demo.core.post.domain.Story
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.FileJpaRepository
import org.psy.demo.infra.jpaRepository.StoryJpaRepository
import org.springframework.stereotype.Component


@Component
class StoryRepository(
    private val storyJpaRepository: StoryJpaRepository,
    private val fileJpaRepository: FileJpaRepository,
) {

    fun loadStorys(userId: User.UserId): List<Story> {

        val storys: List<StoryJpaEntity> =
            storyJpaRepository.findCreatedByAndStatus(userId.id.toLong(), StoryStatus.ACTIVE)
        val storyFiles: List<File> =
            fileJpaRepository.findByIdIn(storys.map { it.getThumbnail() }).map { FileMapper.mapToDomain(it) }

        return storys.map {
            StoryMapper.mapToDomain(it).withThumbnail(
                storyFiles.find { file -> file.fileId.id == it.getThumbnail().toString() }?.url
                    ?: throw IllegalArgumentException("Thumbnail not found")
            )
        }
    }


}
