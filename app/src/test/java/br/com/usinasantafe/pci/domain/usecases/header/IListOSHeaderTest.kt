package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.domain.entities.stable.OS
import br.com.usinasantafe.pci.domain.entities.stable.Plant
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.OSRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IListOSHeaderTest {

    private val osRepository = mock<OSRepository>()
    private val plantRepository = mock<PlantRepository>()
    private val checkListRepository = mock<CheckListRepository>()
    private val usecase = IListOSHeader(
        osRepository = osRepository,
        plantRepository = plantRepository,
        checkListRepository = checkListRepository
    )

    @Test
    fun `Check return failure if have error in CheckListRepository getIdFactorySectionHeaderOpen`() =
        runTest {
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.getIdFactorySectionHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListOSHeader -> ICheckListRepository.getIdFactorySectionHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository listAll`() =
        runTest {
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                osRepository.listByIdFactorySection(1)
            ).thenReturn(
                resultFailure(
                    "IOSRepository.listAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListOSHeader -> IOSRepository.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in PlantRepository listAll`() =
        runTest {
            val osList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 120000,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO",
                    idFactorySectionOS = 1
                ),
                OS(
                    idOS = 2,
                    nroOS = 140000,
                    idPlantOS = 1,
                    qtdDayOS = 7,
                    descPeriodOS = "SEMANAL",
                    idFactorySectionOS = 1
                ),
                OS(
                    idOS = 3,
                    nroOS = 150000,
                    idPlantOS = 2,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO",
                    idFactorySectionOS = 1
                ),
            )
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                osRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(osList)
            )
            whenever(
                plantRepository.listByIdFactorySection(1)
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListOSHeader -> IPlantRepository.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if not have plant of OS`() =
        runTest {
            val osList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 120000,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO",
                    idFactorySectionOS = 1
                ),
                OS(
                    idOS = 2,
                    nroOS = 140000,
                    idPlantOS = 1,
                    qtdDayOS = 7,
                    descPeriodOS = "SEMANAL",
                    idFactorySectionOS = 1
                ),
                OS(
                    idOS = 3,
                    nroOS = 150000,
                    idPlantOS = 2,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO",
                    idFactorySectionOS = 1
                ),
                OS(
                    idOS = 4,
                    nroOS = 160000,
                    idPlantOS = 3,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO",
                    idFactorySectionOS = 1
                ),
            )
            val plantList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "001",
                    descPlant = "PLANT 001",
                    idFactorySectionPlant = 1
                ),
                Plant(
                    idPlant = 2,
                    codPlant = "002",
                    descPlant = "PLANT 002",
                    idFactorySectionPlant = 1
                ),
            )
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                osRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(osList)
            )
            whenever(
                plantRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(plantList)
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListOSHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.util.NoSuchElementException: Collection contains no element matching the predicate."
            )
        }

    @Test
    fun `Check return list correct if process execute successfully`() =
        runTest {
            val osList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 120000,
                    idPlantOS = 1,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO",
                    idFactorySectionOS = 1
                ),
                OS(
                    idOS = 2,
                    nroOS = 140000,
                    idPlantOS = 1,
                    qtdDayOS = 7,
                    descPeriodOS = "SEMANAL",
                    idFactorySectionOS = 1
                ),
                OS(
                    idOS = 3,
                    nroOS = 150000,
                    idPlantOS = 2,
                    qtdDayOS = 1,
                    descPeriodOS = "DIÁRIO",
                    idFactorySectionOS = 1
                ),
            )
            val plantList = listOf(
                Plant(
                    idPlant = 1,
                    codPlant = "001",
                    descPlant = "PLANT 001",
                    idFactorySectionPlant = 1
                ),
                Plant(
                    idPlant = 2,
                    codPlant = "002",
                    descPlant = "PLANT 002",
                    idFactorySectionPlant = 1
                ),
                Plant(
                    idPlant = 3,
                    codPlant = "003",
                    descPlant = "PLANT 003",
                    idFactorySectionPlant = 1
                ),
            )
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                osRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(osList)
            )
            whenever(
                plantRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(plantList)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                3
            )
            assertEquals(
                list[0].id,
                1
            )
            assertEquals(
                list[0].os,
                "OS 120000"
            )
            assertEquals(
                list[0].period,
                "DIÁRIO"
            )
            assertEquals(
                list[0].codPlant,
                "001"
            )
            assertEquals(
                list[0].descPlant,
                "PLANT 001"
            )
            assertEquals(
                list[1].id,
                2
            )
            assertEquals(
                list[1].os,
                "OS 140000"
            )
            assertEquals(
                list[1].period,
                "SEMANAL"
            )
            assertEquals(
                list[1].codPlant,
                "001"
            )
            assertEquals(
                list[1].descPlant,
                "PLANT 001"
            )
            assertEquals(
                list[2].id,
                3
            )
            assertEquals(
                list[2].os,
                "OS 150000"
            )
            assertEquals(
                list[2].period,
                "DIÁRIO"
            )
            assertEquals(
                list[2].codPlant,
                "002"
            )
            assertEquals(
                list[2].descPlant,
                "PLANT 002"
            )
        }
}