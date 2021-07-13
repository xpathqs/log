package org.xpathqs.log

import com.diogonunes.jcolor.Ansi.colorize
import com.diogonunes.jcolor.AnsiFormat
import com.diogonunes.jcolor.Attribute
import com.diogonunes.jcolor.Attribute.*
import org.junit.jupiter.api.Test
import org.xpathqs.log.printers.StreamLogPrinter
import org.xpathqs.log.printers.args.NoArgsProcessor
import org.xpathqs.log.printers.args.StyleArgsProcessor
import org.xpathqs.log.printers.args.TagArgsProcessor
import org.xpathqs.log.printers.args.TimeArgsProcessor
import org.xpathqs.log.printers.body.BodyProcessorImpl
import org.xpathqs.log.printers.body.HierarchyBodyProcessor
import org.xpathqs.log.printers.body.StyledBodyProcessor
import org.xpathqs.log.style.*
import org.xpathqs.log.style.StyleFactory.keyword
import org.xpathqs.log.style.StyleFactory.selectorName
import org.xpathqs.log.style.StyleFactory.text
import org.xpathqs.log.style.StyleFactory.xpath
import org.xpathqs.log.style.StyledString.Companion.defaultStyles
import java.io.PrintStream
import kotlin.random.Random
import org.xpathqs.log.style.plus

class StyledLogTest {

    @Test
    fun test2() {
        val stylesheet = Stylesheet()
        stylesheet[StyleFactory.SELECTOR_NAME] = Style(textColor = 10, backgroundColor =  62, bold = true)

        val str = StyledString(
            selectorName("Selector.s1")
        ) + " - some text"

        println(
            str.toString(stylesheet/*, Style(11, 60)*/)
        )
    }

    @Test
    fun test3() {
        val loger = Logger(
            streamPrinter = StreamLogPrinter(
                argsProcessor =
                    StyleArgsProcessor(
                        TimeArgsProcessor(
                            NoArgsProcessor()
                        ),
                        Style(textColor = 60, backgroundColor = 235)
                    ),
                bodyProcessor =
                    StyledBodyProcessor(
                        HierarchyBodyProcessor(
                            BodyProcessorImpl()
                        ),
                        default = Style(backgroundColor = 235),
                        level1 = Style(textColor = 48),
                        level2 = Style(textColor = 40),
                        level3 = Style(textColor = 35)
                    ),
                writer = PrintStream(System.out)
            )
        )
        val log = BaseLogger(
            arrayListOf(loger),
            defaultStyles
        )

        log.info("Hi, there")

        log.action(keyword("Когда") + " пользователь хочет") {
            log.action(text("Нажать на ") + selectorName("Selector.s1")) {
                log.action("some text") {

                }
                log.action(xpath("//div")) {

                }
            }
        }
    }

    @Test
    fun test444() {
        println(
            StyledString.fromFormatString(
                //"hey %${StyleFactory.SELECTOR_NAME}, there %${StyleFactory.ARG}",
                "Ввести %${StyleFactory.ARG} в %${StyleFactory.SELECTOR_NAME}",
                defaultStyles,
                "Form.s1",
                "Form.s2"
            ).toString(defaultStyles, Style(textColor = 100))
        )
    }

    @Test
    fun test1() {
        // Use Case 1: use Ansi.colorize() to format inline
        // Use Case 1: use Ansi.colorize() to format inline
        System.out.println(colorize("This text will be yellow on magenta", YELLOW_TEXT(), MAGENTA_BACK()))
        println("\n")

// Use Case 2: compose Attributes to create your desired format

// Use Case 2: compose Attributes to create your desired format
        val myFormat: Array<Attribute> = arrayOf<Attribute>(RED_TEXT(), YELLOW_BACK(), BOLD())
        System.out.println(colorize("This text will be red on yellow", *myFormat))
        println("\n")

// Use Case 3: AnsiFormat is syntactic sugar for an array of Attributes

// Use Case 3: AnsiFormat is syntactic sugar for an array of Attributes
        val fWarning = AnsiFormat(GREEN_TEXT(), BLUE_BACK(), BOLD())
        System.out.println(colorize("AnsiFormat is just a pretty way to declare formats", fWarning))
        println(fWarning.format("...and use those formats without calling colorize() directly"))
        println("\n")

// Use Case 4: you can define your formats and use them throughout your code

// Use Case 4: you can define your formats and use them throughout your code
        val fInfo = AnsiFormat(CYAN_TEXT())
        val fError = AnsiFormat(YELLOW_TEXT(), RED_BACK())
        println(fInfo.format("This info message will be cyan"))
        println("This normal message will not be formatted")
        println(fError.format("This error message will be yellow on red"))
        println("\n")

// Use Case 5: we support bright colors

// Use Case 5: we support bright colors
        val fNormal = AnsiFormat(MAGENTA_BACK(), YELLOW_TEXT())
        val fBright = AnsiFormat(BRIGHT_MAGENTA_BACK(), BRIGHT_YELLOW_TEXT())
        println(fNormal.format("You can use normal colors ") + fBright.format(" and bright colors too"))

// Use Case 6: we support 8-bit colors

// Use Case 6: we support 8-bit colors
        println("Any 8-bit color (0-255), as long as your terminal supports it:")
        for (i in 0..255) {
            val txtColor: Attribute = TEXT_COLOR(i)
            System.out.print(colorize(String.format("%4d", i), txtColor))
        }
        println("\n")

// Use Case 7: we support true colors (RGB)

// Use Case 7: we support true colors (RGB)
        println("Any TrueColor (RGB), as long as your terminal supports it:")

        for (i in 0..300) {
            val bkgColor: Attribute = BACK_COLOR(randomInt(255), randomInt(255), randomInt(255))
            System.out.print(colorize("   ", bkgColor))
        }
        println("\n")

// Credits

// Credits
        print("This example used JColor 5.0.0   ")
        System.out.print(colorize("\tMADE ",  BOLD(), BRIGHT_YELLOW_TEXT(), GREEN_BACK()))
        System.out.println(colorize("IN PORTUGAL\t", BOLD(), BRIGHT_YELLOW_TEXT(), RED_BACK()))
        println("I hope you find it useful ;)")

        print("This example used JColor 5.0.0   ")
        System.out.print(colorize("\tMADE ",  BRIGHT_YELLOW_TEXT(), GREEN_BACK()))
        System.out.println(colorize("IN PORTUGAL\t", BRIGHT_YELLOW_TEXT(), RED_BACK()))
        println("I hope you find it useful ;)")
    }


    @Test
    fun tet11() {
        val s = HashSet<String>()
        s.add("")
    }

    fun randomInt(v: Int) = Random.nextInt(0, v)
}