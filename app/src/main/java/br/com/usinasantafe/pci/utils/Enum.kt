package br.com.usinasantafe.pci.utils

enum class StatusSend { STARTED, SEND, SENT }
enum class Errors { FIELD_EMPTY, TOKEN, UPDATE, EXCEPTION, INVALID }
enum class TypeButton { NUMERIC, CLEAN, OK, UPDATE }
enum class FlagUpdate { OUTDATED, UPDATED }
enum class Status { OPEN, CLOSE, FINISH }
enum class LevelUpdate { RECOVERY, CLEAN, SAVE, GET_TOKEN, SAVE_TOKEN, FINISH_UPDATE_INITIAL, FINISH_UPDATE_COMPLETED, CHECK }

enum class OptionResp { ACCORDING, NON_CONFORMING }
enum class TypeFlow { ADD, EDIT }
enum class StatusPlant { OPEN, CLOSE, CLOSE_ALL }

