package org.xpathqs.log.restrictions

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.restrictions.source.ForAllSource
import org.xpathqs.log.restrictions.value.NoRestrictValues

class RestrictionRuleSoft(
    private val rule: IRestrictionValue = NoRestrictValues(),
    private val source: IRestrictionSource = ForAllSource(),
    override var isEnabled: Boolean = true
) : ILogRestrictions {
    override fun canLog(msg: IMessage): Boolean {
        if(rule.isApplicable(msg)) {
            return source.isApplicable(msg)
        }
        return true
    }
}