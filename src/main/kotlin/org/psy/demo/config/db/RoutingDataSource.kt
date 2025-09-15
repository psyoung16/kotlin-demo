package org.psy.demo.config.db

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

import org.slf4j.LoggerFactory
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource

enum class RoutingType {
    WRITE,
    READ_ONLY
}


class RoutingDataSource(
    writeDataSource: DataSource,
    readOnlyDataSource: DataSource,
) : AbstractRoutingDataSource() {
    init {
        super.setTargetDataSources(
            mapOf(
                RoutingType.WRITE to writeDataSource,
                RoutingType.READ_ONLY to readOnlyDataSource,
            )
        )
    }

    override fun determineCurrentLookupKey(): Any {
        val routingType =
            if (TransactionSynchronizationManager.isCurrentTransactionReadOnly())
                RoutingType.READ_ONLY
            else
                RoutingType.WRITE

        println(routingType)
        LOGGER.debug("Datasource is routed to {}", routingType)
        return routingType
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java)
    }
}
