package org.xpathqs.log

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.annotations.LoggerBridge
import org.xpathqs.log.message.MessageDecorator
import org.xpathqs.log.message.NullMessage
import org.xpathqs.log.message.StyledTextMessage
import org.xpathqs.log.message.TextMessage
import org.xpathqs.log.message.decorators.AttachmentMessage
import org.xpathqs.log.message.decorators.ClassMethodMessage
import org.xpathqs.log.message.decorators.TagMessage
import org.xpathqs.log.message.decorators.TimestampMessage
import org.xpathqs.log.printers.StreamLogPrinter
import org.xpathqs.log.printers.args.NoArgsProcessor
import org.xpathqs.log.printers.body.BodyProcessorImpl
import org.xpathqs.log.printers.body.HierarchyBodyProcessor
import org.xpathqs.log.style.StyledBlock
import org.xpathqs.log.style.StyledString
import org.xpathqs.log.style.Stylesheet

open class BaseLogger(
    loggers: ArrayList<Logger>,
    private val stylesheet: Stylesheet = Stylesheet()
) : MessageProcessor(loggers) {
    constructor(log: Logger = Logger(
        streamPrinter = StreamLogPrinter(
            argsProcessor =
                NoArgsProcessor(),
            bodyProcessor =
                HierarchyBodyProcessor(
                    BodyProcessorImpl()
                )
        )
    )): this(arrayListOf(log))

    fun <T> step(msg: String, restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T)
            = step(StyledString(msg), "step", restrictions, lambda)

    fun <T> step(restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T): T {
        return start(
            NullMessage(),
            lambda,
            restrictions
        )
    }

    fun <T> step(msg: StyledString, restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T)
            = step(msg, "step", restrictions, lambda)

    fun <T> step(msg: String, tag: String = "step", restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T)
            = step(StyledString(msg), tag, restrictions, lambda)

    fun <T> step(msg: StyledBlock, tag: String = "step", restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T)
            = step(StyledString(msg), tag, restrictions, lambda)

    fun <T> step(msg: StyledString, tag: String = "step", restrictions: Collection<ILogRestrictions> = emptyList(), lambda: () -> T): T {
        return start(
            getTaggedMessage(
                tag,
                msg
            ),
            lambda,
            restrictions
        )
    }

    fun <T> action(msg: String, tag: String = "action", lambda: () -> T)
        = action(StyledString(msg), tag, lambda)

    fun <T> action(msg: StyledBlock, tag: String = "action", lambda: () -> T)
            = action(StyledString(msg), tag, lambda)

    fun <T> action(msg: StyledString, tag: String = "action", lambda: () -> T): T {
        return start(
            getTaggedMessage(
                tag,
                msg
            ),
            lambda
        )
    }

    fun attachment(data: Array<Byte>, msg: String, tag: String) = attachment(data, StyledBlock(msg), tag)
    fun attachment(data: Array<Byte>, msg: StyledBlock, tag: String) = attachment(data, StyledString(msg), tag)
    fun attachment(data: Array<Byte>, msg: StyledString, tag: String) {
        log(
            AttachmentMessage(
                data, getTaggedMessage(
                    tag,
                    msg
                )
            )
        )
    }

    fun tag(msg: String, tag: String = "") = tag(StyledBlock(msg), tag)
    fun tag(msg: StyledBlock, tag: String = "") = tag(StyledString(msg), tag)
    fun tag(msg: StyledString, tag: String = "") {
        log(
            getTaggedMessage(
                tag,
                msg
            )
        )
    }

    fun debug(msg: String) = debug(StyledBlock(msg))
    fun debug(msg: StyledBlock) = debug(StyledString(msg))
    fun debug(msg: StyledString) {
        log(
            getTaggedMessage(
                "debug",
                msg
            )
        )
    }

    fun info(msg: String) = info(StyledBlock(msg))
    fun info(msg: StyledBlock) = info(StyledString(msg))
    fun info(msg: StyledString) {
        log(
            getTaggedMessage(
                "info",
                msg
            )
        )
    }

    fun trace(msg: String) = trace(StyledBlock(msg))
    fun trace(msg: StyledBlock) = trace(StyledString(msg))
    fun trace(msg: StyledString) {
        log(
            getTaggedMessage(
                "trace",
                msg
            )
        )
    }

    fun error(msg: String) = error(StyledBlock(msg))
    fun error(msg: StyledBlock) = error(StyledString(msg))
    fun error(msg: StyledString) {
        log(
            getTaggedMessage(
                "error",
                msg
            )
        )
    }

    fun always(msg: String) = always(StyledBlock(msg))
    fun always(msg: StyledBlock) = always(StyledString(msg))
    fun always(msg: StyledString) {
        log(
            getTaggedMessage(
                "always",
                msg
            )
        )
    }

    fun warning(msg: String) = warning(StyledBlock(msg))
    fun warning(msg: StyledBlock) = warning(StyledString(msg))
    fun warning(msg: StyledString) {
        log(
            getTaggedMessage(
                "warning",
                msg
            )
        )
    }

    open fun decorateMessage(msg: MessageDecorator): MessageDecorator {
        return msg
    }

    fun getTaggedMessage(tag: String, msg: String) =
        decorateMessage(
            TagMessage(tag,
                TimestampMessage(
                    getClassMethodDecorator(msg)
                )
            )
        )

    fun getTaggedMessage(tag: String, msg: StyledString) =
        decorateMessage(
            TagMessage(tag,
                TimestampMessage(
                    getClassMethodDecorator(msg)
                )
            )
        )

    protected fun getClassMethodDecorator(msg: String): ClassMethodMessage {
        val elems = Thread.currentThread().stackTrace

        var elem = findCallInitializer(elems.drop(1))

        return ClassMethodMessage(
            elems,
            elem.className,
            elem.methodName,
            TextMessage(msg)
        )
    }

    protected fun getClassMethodDecorator(msg: StyledString): ClassMethodMessage {
        val elems = Thread.currentThread().stackTrace

        var elem = findCallInitializer(elems.drop(1))

        return ClassMethodMessage(
            elems,
            elem.className,
            elem.methodName,
            StyledTextMessage(msg, stylesheet)
        )
    }

    fun findCallInitializer(elems: Collection<StackTraceElement>): StackTraceElement {
        elems.forEach {
            val cls = this.javaClass.classLoader.loadClass(it.className)
            if (!cls.isLogger) {
                return it
            }
        }

        throw IllegalArgumentException("No caller was found")
    }

    internal val Class<*>.isLogger: Boolean
        get() {
            if (this.superclass == null && this != MessageProcessor::class.java) {
                return false
            }

            if(this == MessageProcessor::class.java) {
                return true
            }

            val isLoggerBridge = this.isAnnotationPresent(LoggerBridge::class.java)

            return isLoggerBridge
                    || (MessageProcessor::class.java.isAssignableFrom(this.superclass)
                            || this.isAssignableFrom(MessageProcessor::class.java)
                       )
        }

    fun getLogger(name: String)
        = loggers.find { it.name == name }
}