package org.xpathqs.log

import org.xpathqs.log.abstracts.IStreamLog
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

open class TcLogger(
    streamPrinter: IStreamLog = StreamLogPrinter(
        argsProcessor =
            TimeArgsProcessor(
               NoArgsProcessor() //TagArgsProcessor(),
            ),
        bodyProcessor =
            HierarchyBodyProcessor(
                BodyProcessorImpl()
            ),
        System.out
    )
) : Logger(streamPrinter) {

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

    protected fun getClassMethodDecorator(msg: String, level: Int = 5): ClassMethodMessage {
        val elem = Thread.currentThread().stackTrace[level]

        return ClassMethodMessage(
            elem.className,
            elem.methodName,
            TextMessage(msg)
        )
    }

}