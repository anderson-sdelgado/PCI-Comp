package br.com.usinasantafe.pci.domain.usecases.header

import br.com.usinasantafe.pci.domain.entities.stable.OS
import br.com.usinasantafe.pci.domain.entities.stable.Plant
import br.com.usinasantafe.pci.domain.entities.variable.Header
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.OSRepository
import br.com.usinasantafe.pci.domain.repositories.stable.PlantRepository
import br.com.usinasantafe.pci.domain.repositories.variable.CheckListRepository
import br.com.usinasantafe.pci.utils.Status
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
        Plant(
            idPlant = 4,
            codPlant = "004",
            descPlant = "PLANT 004",
            idFactorySectionPlant = 1
        )
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
    fun `Check return failure if have error in PlantRepository listByIdFactorySection -`() =
        runTest {

            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                plantRepository.listByIdFactorySection(1)
            ).thenReturn(
                resultFailure(
                    "IPlantRepository.listByIdFactorySection",
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
                "IListOSHeader -> IPlantRepository.listByIdFactorySection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository listByIdFactorySection`() =
        runTest {
            whenever(
                checkListRepository.getIdFactorySectionHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                plantRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(plantList)
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
    fun `Check return failure if have error in CheckListRepository listHeaderByIdOSList`() =
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
                plantRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(plantList)
            )
            whenever(
                osRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(osList)
            )
            val idOSList = osList.map { it.idOS }.distinct()
            whenever(
                checkListRepository.listHeaderByIdOSList(idOSList)
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.listHeaderByIdOSList",
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
                "IListOSHeader -> ICheckListRepository.listHeaderByIdOSList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CheckListRepository getIdColabHeaderOpen`() =
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
                    nroOS = 200000,
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
                plantRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(plantList)
            )
            whenever(
                osRepository.listByIdFactorySection(1)
            ).thenReturn(
                Result.success(osList)
            )
            val idOSList = osList.map { it.idOS }.distinct()
            whenever(
                checkListRepository.listHeaderByIdOSList(idOSList)
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 1,
                            status = Status.FINISH
                        ),
                        Header(
                            id = 1,
                            idColab = 2,
                            idFactorySection = 1,
                            idOS = 2,
                            status = Status.CLOSE
                        ),
                        Header(
                            id = 1,
                            idColab = 1,
                            idFactorySection = 1,
                            idOS = 3,
                            status = Status.CLOSE
                        )
                    )
                )
            )
            whenever(
                checkListRepository.getIdColabHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "ICheckListRepository.getIdColabHeaderOpen",
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
                "IListOSHeader -> ICheckListRepository.getIdColabHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return success`() =
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

}