package org.xpathqs.log.restrictions.source

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.restrictions.IRestrictionSource

class ForAllSource: IRestrictionSource {
    override fun isApplicable(msg: IMessage): Boolean {
        return true
    }
}