package org.psy.demo.infra.mapper

import org.psy.demo.infra.jpaEntity.StoryJpaEntity
import org.psy.demo.core.domain.entity.Story

object StoryMapper {

    fun mapToDomain(entity: StoryJpaEntity) = Story(
        storyId = Story.StoryId.of(entity.getId().toString()),
        title = entity.getTitle(),
        description = entity.getDescription(),
        storyPosts = emptyList(),
        thumbnailUrl = ""
    )


}
