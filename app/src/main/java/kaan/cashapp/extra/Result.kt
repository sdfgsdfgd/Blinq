package kaan.cashapp.extra

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object NoData : Result<Nothing>()
    object Error : Result<Nothing>()
//    data class Error(val errorType: Throwable) : Result<Nothing>()

    data class Success<out T>(val data: T) : Result<T>()

    fun successData(): T? {
        return (this as? Success)?.data
    }
}