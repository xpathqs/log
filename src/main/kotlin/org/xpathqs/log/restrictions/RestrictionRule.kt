package org.xpathqs.log.restrictions

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.restrictions.source.ForAllSource

class RestrictionRule(
    private val rule: IRestrictionValue,
    private val source: IRestrictionSource = ForAllSource()
) : ILogRestrictions {
    override fun canLog(msg: IMessage): Boolean {
        if(source.isApplicable(msg)) {
            return rule.isApplicable(msg)
        }
        return false
    }
}