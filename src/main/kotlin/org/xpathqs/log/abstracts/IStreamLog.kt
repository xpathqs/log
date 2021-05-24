package org.xpathqs.log.abstracts

import org.xpathqs.log.message.IMessage

interface IStreamLog {
    fun onLog(msg: IMessage)
}