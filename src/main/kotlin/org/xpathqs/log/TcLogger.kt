package org.xpathqs.log

import org.xpathqs.log.abstracts.ILogRestrictions
import org.xpathqs.log.abstracts.IStreamLog
import org.xpathqs.log.annotations.LoggerBridge
import org.xpathqs.log.message.TextMessage
import org.xpathqs.log.message.decorators.ClassMethodMessage
import org.xpathqs.log.message.decorators.TagMessage
import org.xpathqs.log.message.decorators.TimestampMessage
import org.xpathqs.log.printers.*
import org.xpathqs.log.printers.args.NoArgsProcessor
import org.xpathqs.log.printers.args.TagArgsProcessor
import org.xpathqs.log.printers.args.TimeArgsProcessor
import org.xpathqs.log.printers.body.BodyProcessorImpl
import org.xpathqs.log.printers.body.HierarchyBodyProcessor
import org.xpathqs.log.restrictions.NoRestrictions

open class TcLogger(
    streamPrinter: IStreamLog = StreamLogPrinter(
        argsProcessor =
            TimeArgsProcessor(
               NoArgsProcessor()
            ),
        bodyProcessor =
            HierarchyBodyProcessor(
                BodyProcessorImpl()
            ),
        System.out
    ),
    restrictions: ILogRestrictions = NoRestrictions()
) : Logger(
    streamPrinter,
    restrictions
) {

    fun <T> action(msg: String, tag: String = "action", lambda: () -> T): T {
        return start(
            getTaggedMessage(
                tag,
                msg
            ),
            lambda
        )
    }

    fun debug(msg: String) {
        log(
            getTaggedMessage(
                "debug",
                msg
            )
        )
    }

    fun info(msg: String) {
        log(
            getTaggedMessage(
                "info",
                msg
            )
        )
    }

    fun trace(msg: String) {
        log(
            getTaggedMessage(
                "trace",
                msg
            )
        )
    }

    fun error(msg: String) {
        log(
            getTaggedMessage(
                "error",
                msg
            )
        )
    }

    fun always(msg: String) {
        log(
            getTaggedMessage(
                "always",
                msg
            )
        )
    }

    fun getTaggedMessage(tag: String, msg: String) =
        TagMessage(tag,
            TimestampMessage(
                getClassMethodDecorator(msg)
            )
        )

    protected fun getClassMethodDecorator(msg: String): ClassMethodMessage {
        val elems = Thread.currentThread().stackTrace

        var elem = findCallInitializer(elems.drop(1))

        return ClassMethodMessage(
            elem.className,
            elem.methodName,
            TextMessage(msg)
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
            if (this.superclass == null && this != Logger::class.java) {
                return false
            }

            if(this == Logger::class.java) {
                return true
            }

            val isLoggerBridge = this.isAnnotationPresent(LoggerBridge::class.java)

            return isLoggerBridge
                    || (Logger::class.java.isAssignableFrom(this.superclass)
                            || this.isAssignableFrom(Logger::class.java)
                       )
        }
}