package org.xpathqs.log.restrictions.source

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.cls
import org.xpathqs.log.message.method
import org.xpathqs.log.restrictions.IRestrictionSource

class ExcludeMethodClsSimple(
    private val cls: String = "",
    private val method: String
) : IRestrictionSource {
    override fun isApplicable(msg: IMessage): Boolean {
        return msg.cls.substringAfterLast(".") != cls && msg.method != method
    }

}