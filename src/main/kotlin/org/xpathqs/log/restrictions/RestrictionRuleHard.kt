package org.xpathqs.log.restrictions

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.restrictions.source.ForAllSource
import org.xpathqs.log.restrictions.value.NoRestrictValues

class RestrictionRuleHard(
    private val rules: Collection<IRestrictionValue>,
    private val source: IRestrictionSource
) : ILogRestrictions {

    constructor(
        rule: IRestrictionValue = NoRestrictValues(),
        source: IRestrictionSource = ForAllSource()
    ): this(listOf(rule), source)

    override fun canLog(msg: IMessage): Boolean {
        if(source.isApplicable(msg)) {
            return rules.none { !it.isApplicable(msg) }
        }
        return false
    }
}