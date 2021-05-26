package org.xpathqs.log.restrictions

import org.xpathqs.log.message.IMessage

interface IRestrictionValue {
    fun isApplicable(msg: IMessage): Boolean
}