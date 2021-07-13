package org.xpathqs.log.abstracts

import org.xpathqs.log.message.IMessage

interface ILogCallback {
    fun onStart(msg: IMessage) {}
    fun onComplete(msg: IMessage, canLog: Boolean) {}
    fun onLog(msg: IMessage, canLog: Boolean) {}
}