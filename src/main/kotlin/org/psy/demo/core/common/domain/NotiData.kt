package org.psy.demo.core.common.domain

/*data class NotiData(

)*/

data class NotiDataResponse (
        val isReadEvent: Boolean,
        val isReadChallenge: Boolean,
        val isReadTalk: Boolean,
        val isReadIssue: Boolean
){
}
