package br.com.usinasantafe.pci.domain.usecases.background

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.usinasantafe.pci.domain.usecases.note.CheckRespSend
import br.com.usinasantafe.pci.utils.getClassAndMethod
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface StartWorkManager {
    suspend operator fun invoke()
}

class IStartWorkManager @Inject constructor(
    private val workManager: WorkManager,
    private val checkRespSend: CheckRespSend
): StartWorkManager {

    override suspend fun invoke() {
        val result = checkRespSend()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            return
        }
        if(!result.getOrNull()!!) return
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequest
            .Builder(ProcessWorkManager::class.java)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                2, TimeUnit.MINUTES
            )
            .build()
        workManager.enqueueUniqueWork("WORK-MANAGER-CMM", ExistingWorkPolicy.REPLACE, workRequest)
    }

}