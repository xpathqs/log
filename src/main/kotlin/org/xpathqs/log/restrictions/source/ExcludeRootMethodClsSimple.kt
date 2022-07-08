package org.xpathqs.log.restrictions.source

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.method
import org.xpathqs.log.message.stackTrace
import org.xpathqs.log.restrictions.IRestrictionSource

class ExcludeByRootMethodClsSimple(
    private val cls: String = "",
    private val method: String
) : IRestrictionSource {
    override fun isApplicable(msg: IMessage): Boolean {
        msg.stackTrace.forEach {
            if(it.className.substringAfterLast(".") == cls && it.methodName == method) {
                return false
            }
        }

        return true
    }
}