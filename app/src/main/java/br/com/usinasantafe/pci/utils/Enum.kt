package br.com.usinasantafe.pci.utils

enum class StatusSend { STARTED, SEND, SENT }
enum class Errors { FIELD_EMPTY, TOKEN, UPDATE, EXCEPTION, INVALID, HEADER_EMPTY }
enum class TypeButton { NUMERIC, CLEAN, OK, UPDATE }
enum class FlagUpdate { OUTDATED, UPDATED }
enum class Status { OPEN, CLOSE, FINISH }

enum class StatusLeira { LIBERADA, DESCARGA, CARGA }
enum class TypeOper { ATIVIDADE, PARADA }
enum class FuncAtividade { RENDIMENTO, TRANSBORDO, IMPLEMENTO, CARRETEL, LEIRA, TRANP_CANA }
enum class FuncParada { CHECKLIST, IMPLEMENTO, CALIBRAGEM }
enum class TypeItemMenu { ITEM_NORMAL, BUTTON_FINISH_HEADER }
enum class TypeView { ITEM, BUTTON }
enum class TypeEquip { NORMAL, FERT }
enum class LevelUpdate { RECOVERY, CLEAN, SAVE, GET_TOKEN, SAVE_TOKEN, FINISH_UPDATE_INITIAL, FINISH_UPDATE_COMPLETED }
enum class OptionRespCheckList { ACCORDING, ANALYZE, REPAIR }

enum class FlowMenu { INVALID, WORK, STOP, FINISH }
enum class FlowApp { HEADER_INITIAL, NOTE_WORK, NOTE_STOP, HEADER_FINISH, CHECK_LIST }
