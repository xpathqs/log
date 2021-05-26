package org.xpathqs.log.restrictions

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.IMessage

class RestrictionRule(
    private val source: IRestrictionSource,
    private val rule: IRestrictionValue
) : ILogRestrictions {
    override fun canLog(msg: IMessage): Boolean {
        if(source.isApplicable(msg)) {
            return rule.isApplicable(msg)
        }
        return false
    }
}