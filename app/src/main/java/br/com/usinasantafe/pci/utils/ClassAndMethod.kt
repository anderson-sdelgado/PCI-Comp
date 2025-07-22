package br.com.usinasantafe.pci.utils

//fun getClassAndMethod(): String {
//    return Thread.currentThread().stackTrace
//        .dropWhile {
//            it.className.contains("ClassAndMethodKt") ||
//            it.className.startsWith("java.lang.Thread") ||
//            it.className.startsWith("kotlin.coroutines") ||
//            it.className.contains("Continuation") ||
//            it.methodName == "getClassAndMethod"
//        }
//        .firstOrNull {
//            it.className.contains("br.com.usinasantafe")
//        }?.let { element ->
//            val rawClassName = element.className
//                .substringAfterLast('.')
//
//            val className = rawClassName
//                .substringBefore('$')
//
//            val methodName = rawClassName
//                .substringAfter("$className$", "")
//                .substringBefore('$')
//                .ifBlank { element.methodName }
//                .substringBefore('-')
//            val method = if ((methodName == "invoke") || (methodName == "invokeSuspend")) "" else ".$methodName"
//            "$className$method"
//        } ?: "Classe.MétodoDesconhecido"
//}

fun getClassAndMethod(): String {
    val stackTrace = Thread.currentThread().stackTrace
    val utilClassName = "br.com.usinasantafe.pci.utils.ClassAndMethodKt"

    for (i in stackTrace.indices) {
        val element = stackTrace[i]

        if (element.className.startsWith("java.lang.Thread") ||
            element.methodName == "getStackTrace" ||
            element.methodName == "getClassAndMethod" ||
            element.className == utilClassName) {
            continue
        }

        if (element.className.startsWith("kotlin.coroutines") ||
            element.className.contains("Continuation") ||
            element.className.contains("SuspendLambda") ||
            element.methodName.contains("\$resumeWith") ||
            element.methodName.endsWith("\$suspendImpl")) {
            continue
        }

        if (element.className.contains("br.com.usinasantafe") && element.className != utilClassName) {
            val rawClassName = element.className.substringAfterLast('.')
            val className = rawClassName.substringBefore('$')

            var methodName = if (rawClassName.contains('$')) {
                rawClassName.substringAfter("$className$", "")
                    .substringBefore('$')
            } else {
                element.methodName
            }

            if (methodName.isBlank() || methodName == "invoke" || methodName == "invokeSuspend") {
                methodName = element.methodName
            }

            methodName = methodName.substringBefore('$')
            methodName = methodName.substringBefore('-')

            val methodPart = if (methodName == "invoke" || methodName == "invokeSuspend") {
                if (className == methodName) ""
                else ""
            } else {
                ".$methodName"
            }

            return "$className$methodPart"
        }
    }
    return "Classe.MetodoDesconhecido"
}