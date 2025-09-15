package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.Date


@Entity
@Table(name = "device_sessions")
class DeviceSessionJpaEntity(


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    private val userId: Long? = null,

    private val deviceToken: String? = null,

    private val deviceType: String = "Unknown",
    private val ipAddress: String = "Unknown",
    private val location: String = "Unknown",
    private val loginAt: Date = Date(),
    @Enumerated(EnumType.STRING)
    private val language: Languge = Languge.KR,
    private val createdAt: Date = Date(),
    private val updatedAt: Date = Date(),


    @Enumerated(EnumType.STRING)
    private val status: DeviceSessionStatus = DeviceSessionStatus.ACTIVE
) {


}

enum class Languge {
    EN, KR
}

enum class DeviceSessionStatus {
    ACTIVE, DEACTIVATED, REMOVED
}
