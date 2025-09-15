package org.psy.demo.infra.jpaEntity

import org.psy.demo.core.user.domain.Role
import org.psy.demo.core.user.domain.UserType
import jakarta.persistence.*
import org.psy.demo.core.post.domain.Writer
import org.psy.demo.core.post.domain.WriterStatus
import java.util.*


@Entity
@Table(name = "user_writers")
class WriterJpaEntity(


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long? = null,

    @Column(name = "userId")
    private var userId: Long? = null,

    @Column(name = "password")
    private var password: String? = null,

    @Column(name = "nickname", nullable = false, length = 255)
    private var nickname: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private var role: Role = Role.WRITER,

    @Column(name = "avatar")
    private var avatar: Long? = null,

    @Column(name = "totalLiker", nullable = false)
    private var totalLiker: Int = 0,

    @Column(name = "linkShare", columnDefinition = "TEXT")
    private var linkShare: String? = null,

    @Column(name = "instarLink", columnDefinition = "TEXT")
    private var instarLink: String? = null,

    @Column(name = "facebookLink", columnDefinition = "TEXT")
    private var facebookLink: String? = null,

    @Column(name = "twitterLink", columnDefinition = "TEXT")
    private var twitterLink: String? = null,

    @Column(name = "blogLink", columnDefinition = "TEXT")
    private var blogLink: String? = null,

    @Column(name = "youtubeLink", columnDefinition = "TEXT")
    private var youtubeLink: String? = null,

    @Column(name = "bio", columnDefinition = "TEXT")
    private var bio: String? = null,

    @Column(name = "createdAt")
    private var createdAt: Date? = null,

    @Column(name = "updatedAt")
    private var updatedAt: Date? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private var status: WriterStatus = WriterStatus.ACTIVE,

    @Column(name = "email", length = 255)
    private var email: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "userType")
    private var userType: UserType = UserType.NORMAL,

    @Column(name = "backImg")
    private var backImg: Long? = null,

    @Column(name = "linkShareName", length = 200)
    private var linkShareName: String? = null,

    @Column(name = "isSync")
    private var isSync : Boolean,

    @Column(name = "hottracksLink", columnDefinition = "TEXT")
    private var hottracksLink: String? = null,

    @Column(name = "smartStoreLink", columnDefinition = "TEXT")
    private var smartStoreLink: String? = null,

    @Column(name = "postypeLink", columnDefinition = "TEXT")
    private var postypeLink: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "goodsLinkType")
    private var goodsLinkType: WriterGoodsType? = null,

    @Column(name = "idusLink", columnDefinition = "TEXT")
    private var idusLink: String? = null,

    private var avatarUrl: String?,  //db가져오는 것 때문에 생성, @transient는 조회시 무시되기때문에 생략 , 저장할때 유의 필요

    private var backImgUrl: String?,

    @Column(name = "smartStoreAppId", length = 100)
    private var smartStoreAppId: String? = null,

    @Column(name = "smartStoreSecretKey", length = 100)
    private var smartStoreSecretKey: String? = null,



    ) {

    constructor(
        id: Long, userId: Long?, nickname: String?, role: Role, linkShare: String?,
        instarLink: String?, facebookLink: String?, blogLink: String?, youtubeLink: String?, bio: String?,
        createdAt: Date?, updatedAt: Date?, status: WriterStatus, email: String?, userType: UserType,
        isSync: Boolean, hottracksLink: String?, smartStoreLink: String?, postypeLink: String?, goodsLinkType: WriterGoodsType?,
        avatarUrl: String?, backImgUrl: String?
    ) : this (
        id,  userId, null,  nickname,  role,
        null,  0, linkShare,  instarLink,  facebookLink,
        null, blogLink,  youtubeLink,  bio,  createdAt,
        updatedAt, status, email, userType, null,
        null, isSync, hottracksLink, smartStoreLink, postypeLink ,  goodsLinkType,
        null, avatarUrl, backImgUrl, null, null
    )


    companion object{

        fun mapToDomain(entity: WriterJpaEntity) = Writer(
            entity.id ?: 0,
            entity.nickname.orEmpty(),
            entity.instarLink.orEmpty(),
            entity.facebookLink.orEmpty(),
            entity.twitterLink.orEmpty(),
            entity.blogLink.orEmpty(),
            entity.youtubeLink.orEmpty(),
            entity.bio.orEmpty(),
            entity.status,
            entity.email.orEmpty(),
            entity.avatarUrl.orEmpty(),
            entity.backImgUrl.orEmpty(),
        )

    }

}

enum class WriterGoodsType {
    HOTTRACKS, SMARTSTORE, OWNSTORE, IDUS,
    MARPPLE, TEN, POSTYPE
}
