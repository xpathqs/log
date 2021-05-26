package org.xpathqs.log.restrictions.source

import org.xpathqs.log.message.IMessage
import org.xpathqs.log.message.cls
import org.xpathqs.log.message.tag
import org.xpathqs.log.restrictions.IRestrictionSource

class ExcludePackage(
    private vararg val packages: String
): IRestrictionSource {
    override fun isApplicable(msg: IMessage): Boolean {
        val pkg = msg.cls.substringBeforeLast(".")
        return packages.find { pkg.startsWith(it) } == null
    }
}