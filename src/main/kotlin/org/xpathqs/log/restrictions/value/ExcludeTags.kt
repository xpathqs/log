package org.xpathqs.log.restrictions.value

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.tag
import org.xpathqs.log.restrictions.IRestrictionValue

class ExcludeTags(
    private vararg val tags: String
) : IRestrictionValue {
    override fun isApplicable(msg: IMessage): Boolean {
        val tag = msg.tag
        return tags.find { it == tag } == null
    }
}