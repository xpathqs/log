package org.xpathqs.log.abstracts

import org.xpathqs.log.message.IMessage

interface IBodyProcessor {
    fun processBody(msg: IMessage): String
}