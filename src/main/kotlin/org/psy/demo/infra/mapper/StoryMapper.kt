package org.psy.demo.infra.mapper

import org.psy.demo.core.post.domain.Story
import org.psy.demo.infra.jpaEntity.StoryJpaEntity

object StoryMapper {

    fun mapToDomain(entity: StoryJpaEntity) = Story(
        storyId = Story.StoryId.of(entity.getId().toString()),
        title = entity.getTitle(),
        description = entity.getDescription(),
        storyPosts = emptyList(),
        thumbnailUrl = ""
    )


}
