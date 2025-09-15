package org.psy.demo.infra.repository

import org.psy.demo.common.exception.CustomException
import org.psy.demo.common.exception.ErrorCode
import org.psy.demo.infra.jpaEntity.*
import org.psy.demo.infra.mapper.UserMapper
import org.psy.demo.infra.vo.UserStatus
import org.psy.demo.core.user.domain.User
import org.psy.demo.infra.jpaRepository.UserJpaRepository
import org.springframework.stereotype.Component

@Component
class UserRepository(
    private val userJpaRepository: UserJpaRepository,
) {

    fun loadUserByUserIds(userIds: List<User.UserId>): List<User> {
        val users: List<UserJpaEntity> = userJpaRepository.findByIdIn(userIds.map {
            it.id.toLong()
        })
        return users.map(UserMapper::mapToDomain)
    }

    fun loadUser(userId: User.UserId): User? {
        val user: UserJpaEntity? = userJpaRepository.findId(userId.id.toLong())
        return user?.let { UserMapper.mapToDomain(it) }
    }

    /**
     * 초기엔 loadActiveUserOrThrow로 진행했었다.
     * 구현 세부사항(예외를 던진다는 것)을 인터페이스 수준에서 노출, 예외를 던지는 것이 해당 함수의 유일한 실패 경로가 아닐 수 있
     * 때문에 삭제처리
     */
    fun loadActiveUser(userId: User.UserId): User {
        val user: UserJpaEntity = userJpaRepository.findIdAndStatus(userId.id.toLong())
        ?: throw CustomException(ErrorCode.USER_NOT_FOUND) //추가한 이유, 알 수 없는 오류 대신 front에도 노출시키는게 명시적으로 좋다고 판단함
        return UserMapper.mapToDomain(user)
    }

    fun loadActiveUserWithFollowedInfo(requestUserId: User.UserId, targetUserId: User.UserId): User {
        val user = userJpaRepository.findIdAndStatus(targetUserId.id.toLong())
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        return UserMapper.mapToDomain(user)
    }

    //Active때문에 여기서 체크하는거긴한데...
    fun loadActiveUserExcludingBlocked(requestUserId: User.UserId, targetUserId: User.UserId): User {

        //block된 데이터면 null 아니면 User 반환
        val user: UserJpaEntity = userJpaRepository.findUserExcludingBlocked(requestUserId.id.toLong(), targetUserId.id.toLong())
            ?.takeIf { it.getStatus() == UserStatus.ACTIVE }
//            ?.takeUnless { it.getStatus() != UserStatus.ACTIVE } //Active가 아닐 경우 null
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

        return UserMapper.mapToDomain(user)
    }


}
