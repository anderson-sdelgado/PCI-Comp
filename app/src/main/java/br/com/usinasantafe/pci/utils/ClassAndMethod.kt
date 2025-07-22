package br.com.usinasantafe.pci.utils

fun getClassAndMethod(): String {
    return Thread.currentThread().stackTrace
        .dropWhile {
            it.className.startsWith("java.lang.Thread") ||
                    it.className.startsWith("kotlin.coroutines") ||
                    it.className.contains("Continuation") ||
                    it.methodName == "getClassAndMethod"
        }
        .firstOrNull {
            it.className.contains("br.com.usinasantafe")
        }?.let { element ->
            val rawClassName = element.className
                .substringAfterLast('.')

            val className = rawClassName
                .substringBefore('$')

            val methodName = rawClassName
                .substringAfter("$className$", "")
                .substringBefore('$')
                .ifBlank { element.methodName }
                .substringBefore('-')
            val method = if ((methodName == "invoke") || (methodName == "invokeSuspend")) "" else ".$methodName"
            "$className$method"
        } ?: "Classe.MétodoDesconhecido"
}