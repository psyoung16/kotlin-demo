package org.psy.demo.infra.jpaEntity

import jakarta.persistence.*
import java.util.*
import org.psy.demo.core.vo.NotificationType


@Entity
@Table(name = "notification_list")
class NotiListJpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private var type: NotificationType? = null

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: Date? = null

    @Column(name = "createdBy")
    private var createdBy: Long? = null

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private var updatedAt: Date? = null // Constructors, getters, and setters



}
