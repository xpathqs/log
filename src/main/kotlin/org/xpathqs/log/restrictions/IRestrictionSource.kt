package org.xpathqs.log.restrictions

import org.xpathqs.log.message.IMessage

interface IRestrictionSource {
    fun isApplicable(msg: IMessage): Boolean
}