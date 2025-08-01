package br.com.usinasantafe.pci.domain.usecases.update

import br.com.usinasantafe.pci.domain.entities.stable.Component
import br.com.usinasantafe.pci.domain.errors.resultFailure
import br.com.usinasantafe.pci.domain.repositories.stable.ComponentRepository
import br.com.usinasantafe.pci.domain.usecases.common.GetToken
import br.com.usinasantafe.pci.presenter.model.ResultUpdateModel
import br.com.usinasantafe.pci.utils.Errors
import br.com.usinasantafe.pci.utils.LevelUpdate
import br.com.usinasantafe.pci.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableComponentTest {

    private val getToken = mock<GetToken>()
    private val componentRepository = mock<ComponentRepository>()
    private val usecase = IUpdateTableComponent(
        getToken = getToken,
        componentRepository = componentRepository
    )

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "GetToken",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableComponent -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ComponentRepository listAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                componentRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                resultFailure(
                    "IComponentRepository.listAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableComponent -> IComponentRepository.listAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ComponentRepository deleteAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                componentRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Component(
                            idComponent = 1,
                            codComponent = "1",
                            descComponent = "Component"
                        )
                    )
                )
            )
            whenever(
                componentRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IComponentRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableComponent -> IComponentRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ComponentRepository addAll`() =
        runTest {
            val entityList = listOf(
                Component(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                componentRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                componentRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                componentRepository.addAll(entityList)
            ).thenReturn(
                resultFailure(
                    "IComponentRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableComponent -> IComponentRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return correct if process execute successfully`() =
        runTest {
            val entityList = listOf(
                Component(
                    idComponent = 1,
                    codComponent = "1",
                    descComponent = "Component"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                componentRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                componentRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                componentRepository.addAll(entityList)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_component",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
        }
}