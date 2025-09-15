package org.psy.demo.infra.mapper

import org.psy.demo.common.ImgUtil
import org.psy.demo.core.user.domain.Badge
import org.psy.demo.infra.jpaEntity.BadgeJpaEntity
import org.psy.demo.infra.jpaEntity.BadgeType
import org.psy.demo.core.user.domain.User

object BadgeMapper {

    fun mapToDomain(entity: BadgeJpaEntity) = Badge(
        badgeId = Badge.BadgeId.of(entity.getId().toString()),
        ownerId = User.UserId(entity.getOwnerId().toString()),
        badgeType = entity.getBadgeType(),
        badgeImg = settingBadgeImgInfo(entity.getBadgeType()),
        badgeName = settingBadgeNameInfo(entity.getBadgeType())
    )


    private fun settingBadgeImgInfo(badgeType: BadgeType) : String{

        val badgeImgName = when (badgeType) {
            BadgeType.MAFIA_1 -> "bg_mafia_1"
            BadgeType.UPLOAD_1 -> "bg_upload_1"
            BadgeType.UPLOAD_10 -> "bg_upload_10"
            BadgeType.UPLOAD_50 -> "bg_upload_50"
            BadgeType.UPLOAD_100 -> "bg_upload_100"
            BadgeType.FOLLOWER_10 -> "bg_follower_10"
            BadgeType.FOLLOWER_50 -> "bg_follower_50"
            BadgeType.FOLLOWER_100 -> "bg_follower_100"
            BadgeType.COMMENT_1 -> "bg_comment_1"
            BadgeType.COMMENT_10 -> "bg_comment_10"
            BadgeType.COMMENT_50 -> "bg_comment_50"
            BadgeType.COMMENT_100 -> "bg_comment_100"
            BadgeType.FOLLOWING_10 -> "bg_following_10"
            BadgeType.FOLLOWING_50 -> "bg_following_50"
            BadgeType.FOLLOWING_100 -> "bg_following_100"
//            BadgeType.DAKKUZZANG -> "bg_dakkuzzang"
//            BadgeType.KKOOMMINGFAM -> "bg_kkoo_fam"
        }

        return ImgUtil.getImgUrl("/PROFILE/${badgeImgName}.png")
    }
    private fun settingBadgeNameInfo(badgeType: BadgeType) : String{
        return when (badgeType) {
            BadgeType.MAFIA_1 -> "다꾸마피아 1기"
            BadgeType.UPLOAD_1 -> "업로드 1"
            BadgeType.UPLOAD_10 -> "업로드 10"
            BadgeType.UPLOAD_50 -> "업로드 50"
            BadgeType.UPLOAD_100 -> "업로드 100"
            BadgeType.FOLLOWER_10 -> "팔로워 10"
            BadgeType.FOLLOWER_50 -> "팔로워 50"
            BadgeType.FOLLOWER_100 -> "팔로워 100"
            BadgeType.COMMENT_1 -> "댓글 1"
            BadgeType.COMMENT_10 -> "댓글 10"
            BadgeType.COMMENT_50 -> "댓글 50"
            BadgeType.COMMENT_100 -> "댓글 100"
            BadgeType.FOLLOWING_10 -> "팔로잉 10"
            BadgeType.FOLLOWING_50 -> "팔로잉 50"
            BadgeType.FOLLOWING_100 -> "팔로잉 100"
//            BadgeType.DAKKUZZANG -> "다꾸짱"
//            BadgeType.KKOOMMINGFAM -> "꾸밍팸 1기"
        }

    }


}
