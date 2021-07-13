package org.xpathqs

import org.xpathqs.log.BaseLogger
import org.xpathqs.log.annotations.LoggerBridge

@LoggerBridge
object TestLog {
    var log: BaseLogger = BaseLogger()

    fun info(msg: String) {
        log.info(msg)
    }

    fun trace(msg: String) {
        log.trace(msg)
    }

    fun debug(msg: String) {
        log.debug(msg)
    }
}