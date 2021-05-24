package org.xpathqs.log.abstracts

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.LogAttributes

interface IArgsProcessor {
    fun processArgs(msg: IMessage): String
}