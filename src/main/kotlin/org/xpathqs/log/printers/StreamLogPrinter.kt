package org.xpathqs.log.printers

import org.xpathqs.log.abstracts.IArgsProcessor
import org.xpathqs.log.abstracts.IBodyProcessor
import org.xpathqs.log.abstracts.IStreamLog
import org.xpathqs.log.message.IMessage
import java.io.PrintStream

open class StreamLogPrinter(
    protected val argsProcessor: IArgsProcessor,
    protected val bodyProcessor: IBodyProcessor,
    protected val writer: PrintStream = System.out
) : IStreamLog {

    override fun onLog(msg: IMessage) {
        writer.println(
            argsProcessor.processArgs(msg) + " " + bodyProcessor.processBody(msg)
        )
    }
}