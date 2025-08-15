package br.com.usinasantafe.pci.domain.usecases.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.usinasantafe.pci.domain.usecases.flow.CheckRespSend
import br.com.usinasantafe.pci.domain.usecases.flow.SendNote
import br.com.usinasantafe.pci.utils.getClassAndMethod
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class ProcessWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val checkRespSend: CheckRespSend,
    private val sendNote: SendNote
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val resultCheck = checkRespSend()
        if (resultCheck.isFailure) {
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            return Result.retry()
        }
        if(!resultCheck.getOrNull()!!) return Result.success()
        val resultSend = sendNote()
        if (resultSend.isFailure) {
            val error = resultSend.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            return Result.retry()
        }
        return Result.success()
    }

}