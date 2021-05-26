package org.xpathqs.log.restrictions.source

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.cls
import org.xpathqs.log.message.tag
import org.xpathqs.log.restrictions.IRestrictionSource

class ExcludeClass(
    private vararg val classes: String
): IRestrictionSource {
    override fun isApplicable(msg: IMessage): Boolean {
        val cls = msg.cls
        return classes.find { it == cls } == null
    }
}