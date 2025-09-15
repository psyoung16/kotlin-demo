package org.psy.demo.common

import org.psy.demo.common.exception.CustomException
import org.psy.demo.common.exception.ErrorCode
import org.psy.demo.config.JwtService
import org.psy.demo.infra.jpaRepository.DeviceSessionJpaRepository
import org.psy.demo.infra.jpaEntity.DeviceSessionJpaEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class AuthClient(
    private val redisUtil: RedisUtil,
    private val jwtService: JwtService,
    private val deviceSessionJpaRepository: DeviceSessionJpaRepository
) {

    @Value("\${data.sessionTime}") // Kotlin에서는 $ 문자를 이스케이프 처리해야 합니다.
    private lateinit var sessionTime: String

    fun authenticationClient(token: String?){

        token ?: throw CustomException(ErrorCode.TOKEN_EXPIRED_OR_NOT_AVAILABLE)
        //token check
        _getToken(token)
        //토큰이 있다면, 타 기기에 로그인 되어있는지 확인
        val data  = jwtService.decodeJwt(token) //decode를 못하는대ㅔ..
        _checkDeviceTokenValid(data["deviceId"].toString())
    }

    fun _getToken(token: String): String {

        return if (token.startsWith("Bearer ")) {
            // "Bearer " 이후의 토큰 부분만 반환
            token.substring(7)
        } else {
            // "Bearer "가 아니면 그대로 반환
            token
        }
    }


    fun _checkDeviceTokenValid(decodeDeviceId: String) : Boolean{

        val deviceRedisInfo = redisUtil.getValue("DEVICE_ID_$decodeDeviceId") ?: throw CustomException(ErrorCode.DEVICE_SESSION_EXPIRED)
        if (deviceRedisInfo != "true") return true

        val ds: DeviceSessionJpaEntity = deviceSessionJpaRepository.findAndDeviceId(decodeDeviceId.toLong()) ?: throw CustomException(ErrorCode.DEVICE_SESSION_EXPIRED)
        ds.let {
            redisUtil.setValue("DEVICE_ID_$decodeDeviceId", "true", sessionTime.toLong())
            return true
        }

        /*const deviceRedisInfo = await Redis.getJson(`${DEVICE_SESSION_REDIS_KEY}${deviceId}`);
        if (deviceRedisInfo) return true;
        const deviceInfo = await DeviceSessionRepository.findByPk(deviceId);
        if (deviceInfo && deviceInfo.status == DEVICE_SESSION_STATUS.ACTIVE) {
            console.log("SESSON_TIME===",SESSION_TIME)
            await Redis.setJson(`${DEVICE_SESSION_REDIS_KEY}${deviceId}`, true, SESSION_TIME);
            return true;
        } else {
            return { error: AUTHENTICATION_ERRORS.DEVICE_SESSION_EXPIRED() };
        }*/

    }
}
