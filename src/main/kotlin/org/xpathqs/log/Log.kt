package org.xpathqs.log

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.annotations.LoggerBridge
import org.xpathqs.log.style.StyleFactory
import org.xpathqs.log.style.StyledBlock
import org.xpathqs.log.style.StyledString

@LoggerBridge
object Log {
    var log = BaseLogger()
    var enabled = true

    fun tag(msg: String, tag: String = "") = tag(StyledBlock(msg), tag)
    fun tag(msg: StyledBlock, tag: String = "") = tag(StyledString(msg), tag)
    fun tag(msg: StyledString, tag: String = "") {
        if(enabled) {
            log.tag(msg, tag)
        }
    }

    fun debug(msg: String) = debug(StyledBlock(msg))
    fun debug(msg: StyledBlock) = debug(StyledString(msg))
    fun debug(msg: StyledString) {
        if(enabled) {
            log.debug(msg)
        }
    }

    fun trace(msg: String) = trace(StyledBlock(msg))
    fun trace(msg: StyledBlock) = trace(StyledString(msg))
    fun trace(msg: StyledString) {
        if(enabled) {
            log.trace(msg)
        }
    }
    fun <T> traceResult(msg: StyledString, lambda: () -> T): T {
        return if(enabled) {
            val res = lambda()
            trace(msg + StyleFactory.result(" $res"))
            res
        } else {
            lambda()
        }
    }

    fun warning(msg: String) = warning(StyleFactory.warning(msg))
    fun warning(msg: StyledBlock) = warning(StyledString(msg))
    fun warning(msg: StyledString) {
        if(enabled) {
            log.trace(msg)
        }
    }

    fun result(msg: String) = result(StyleFactory.result(msg))
    fun result(msg: StyledBlock) = result(StyledString(msg))
    fun result(msg: StyledString) {
        if(enabled) {
            log.trace(msg)
        }
    }

    fun xpath(xpath: String) {
        if(enabled) {
            log.trace(StyleFactory.text( "xpath: ") + StyleFactory.xpath(xpath))
        }
    }

    fun info(msg: String) = info(StyledBlock(msg))
    fun info(msg: StyledBlock) = info(StyledString(msg))
    fun info(msg: StyledString) {
        if(enabled) {
            log.info(msg)
        }
    }

    fun always(msg: String) = always(StyledBlock(msg))
    fun always(msg: StyledBlock) = always(StyledString(msg))
    fun always(msg: StyledString) {
        if(enabled) {
            log.always(msg)
        }
    }

    fun error(msg: String) = error(StyleFactory.error(msg))
    fun error(msg: StyledBlock) = error(StyledString(msg))
    fun error(msg: StyledString) {
        if(enabled) {
            log.error(msg)
        }
    }

    fun attachment(value: String, type: String, data: Any) {
        if(enabled) {
            log.addAttachment(value, type, data)
        }
    }

    fun attachment(data: Array<Byte>, msg: String="", tag: String="") = attachment(data, StyledBlock(msg), tag)
    fun attachment(data: Array<Byte>, msg: StyledBlock, tag: String="") = attachment(data, StyledString(msg), tag)
    fun attachment(data: Array<Byte>, msg: StyledString, tag: String="") {
        if(enabled) {
            log.attachment(data, msg, tag)
        }
    }

    fun <T> action(msg: String, tag: String = "action", lambda: () -> T) = action(StyledBlock(msg), tag, lambda)
    fun <T> action(msg: StyledBlock, tag: String = "action", lambda: () -> T) = action(StyledString(msg), tag, lambda)
    fun <T> action(msg: StyledString, tag: String = "action", lambda: () -> T): T {
        return if(enabled) {
            log.action(msg, tag, lambda = lambda)
        } else {
            lambda()
        }
    }

    fun <T> step(restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T): T {
        return if(enabled) log.step(restrictions, lambda) else lambda()
    }

    fun <T> step(msg: String, restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T)
            = step(StyledString(msg), "step", restrictions, lambda)

    fun <T> step(msg: StyledString, restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T)
            = step(msg, "step", restrictions, lambda)

    fun <T> step(msg: String, tag: String = "step", restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T)
            = step(StyledString(msg), tag, restrictions, lambda)

    fun <T> step(msg: StyledBlock, tag: String = "step", restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T)
            = step(StyledString(msg), tag, restrictions, lambda)

    fun <T> step(msg: StyledString, tag: String = "step", restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T): T {
        return if(enabled) log.step(msg, tag, restrictions, lambda = lambda) else lambda()
    }
}

fun noLog(l: ()->Unit) {
    var prev = Log.enabled
    Log.enabled = false
    l()
    Log.enabled = true
}