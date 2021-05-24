package org.xpathqs.log.abstracts

interface IStructureLog<T>  {
    fun open(msg: T)
    fun close(msg: T)

    val current: T
}