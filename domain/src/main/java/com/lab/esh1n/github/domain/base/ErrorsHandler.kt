package com.lab.esh1n.github.domain.base


class ErrorsHandler(private val errorDescriptionProvider: ErrorDescriptionProvider) {

    fun handle(throwable: Throwable): ErrorModel {
        val errorModel = convertToErrorModel(throwable)
        if (errorModel.message.isBlank()) {
            errorModel.message = errorDescriptionProvider.getHumanReadableError(errorModel)
        }
        return errorModel;
    }

    private fun convertToErrorModel(throwable: Throwable): ErrorModel {

        var errorModel = ErrorModel.unexpectedError(throwable.localizedMessage)

        if (throwable is RetrofitException) {
            val response = throwable.response
            errorModel = if (response == null) {
                ErrorModel.connectionError()
            } else {
                ErrorModel.httpError(code = response.code())
            }
        }
        return errorModel
    }

}