package org.psy.demo.admin.response

import org.psy.demo.common.MetaData
import org.psy.demo.core.phrase.domain.ManageTagManagement

data class ManageTagManagementResponse (

    val manageTagManagements: List<ManageTagManagement>,
    val metaData: MetaData
) {
}