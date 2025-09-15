package org.psy.demo.infra.jpaEntity

import org.psy.demo.common.ImgUtil
import org.psy.demo.core.user.domain.AccountType
import org.psy.demo.core.user.domain.Role
import org.psy.demo.core.user.domain.UserType
import jakarta.persistence.*
import org.psy.demo.infra.vo.UserStatus
import java.util.*

@Entity
@Table(name = "users")
class UserJpaEntity(

    private val clientId: String?,
    private val password: String?,

    @Enumerated(EnumType.STRING)
    private val accountType: AccountType? = null,
    @Column(nullable = false)
    private val nickname: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private val role: Role,
    private val avatar: Long? = null,


    @Column(nullable = false)
    private val totalFollower: Int = 0,
    private val userBirth: String?,
    @Column(nullable = false)
    private val totalFollowing: Int = 0,

// 추가한 필드들
    private val linkShare: String? = "",
    private val bio: String? = "",
    private val isOnline: Boolean? = false,
    private val createdAt: Date? = null,
    private val updatedAt: Date? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private val status: UserStatus,
    private val email: String? = null,
    @Column(nullable = false)
    private val numOrder: Int = 0,

    @Enumerated(EnumType.STRING)
    private val userType: UserType? = null,
    @Column(nullable = false)
    private val totalReported: Int = 0,
    @Column(nullable = false)
    private val totalMyPost: Int = 0,
    private val memberID: String? = null,
    @Column(nullable = false)
    private val totalAccessApp: Int = 0,
    private val managerID: String? = null,
    private val position: String? = null,
    private val background: Long? = null,

    private val preClientId: String? = null,
    private val preAccountType: String? = null,
    private val isPrivateTerm: Boolean? = false,
    private val isUseTerm: Boolean? = false,
    private val preAccountClientId: String? = null,
    private val authNumber: String? = null,
    private val newAvatarImgUrl: String? = "",

    ) {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    fun getAvatar(): Long? {
        return avatar
    }

    fun getId(): Long? {
        return id
    }

    fun getBio(): String? {
        return bio
    }
    fun getLinkShare(): String? {
        return linkShare
    }

    fun getNickname(): String {
        return nickname
    }

    fun getNewAvatarImgUrl(): String {
        //구버전 img ImgUtil.getImgUrl("avatarDefault${ (getId() ?: 0) % 6 + 2}.png")
        //            return
        return newAvatarImgUrl.takeUnless { it.isNullOrBlank() }
            ?: ImgUtil.getImgUrl("/PROFILE/user_profile_${(getId() ?: 0) % 3 + 1}.png")
    }

    fun getAccountType(): AccountType? {
        return accountType
    }
    fun getTotalFollower(): Int {
        return totalFollower
    }
    fun getTotalFollowing(): Int {
        return totalFollowing
    }
    fun getTotalReported(): Int {
        return totalReported
    }
    fun getStatus(): UserStatus {
        return status
    }


}
