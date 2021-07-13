package org.xpathqs.log.printers

import org.xpathqs.log.abstracts.IArgsProcessor
import org.xpathqs.log.abstracts.IBodyProcessor
import org.xpathqs.log.abstracts.IStreamLog
import org.xpathqs.log.message.IMessage
import org.xpathqs.log.printers.args.NoArgsProcessor
import org.xpathqs.log.printers.body.BodyProcessorImpl
import org.xpathqs.log.printers.body.HierarchyBodyProcessor
import java.io.PrintStream

open class StreamLogPrinter(
    protected val argsProcessor: IArgsProcessor
        = NoArgsProcessor(),
    protected val bodyProcessor: IBodyProcessor
        = HierarchyBodyProcessor(
            BodyProcessorImpl()
        ),
    protected val writer: PrintStream
        = System.out
) : IStreamLog {

    override fun onLog(msg: IMessage) {
        writer.println(
            argsProcessor.processArgs(msg) + " " + bodyProcessor.processBody(msg)
        )
    }
}