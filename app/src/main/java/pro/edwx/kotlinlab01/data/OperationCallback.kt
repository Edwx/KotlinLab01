package pro.edwx.kotlinlab01.data

interface OperationCallback<T> {
    fun onSuccess(data: T)
    fun onError(error: String?)
}