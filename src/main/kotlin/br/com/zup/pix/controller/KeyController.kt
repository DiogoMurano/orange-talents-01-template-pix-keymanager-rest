package br.com.zup.pix.controller

import br.com.zup.pix.KeyManagerServiceGrpc
import br.com.zup.pix.controller.request.CreatePixKeyRequest
import br.com.zup.pix.controller.request.RemovePixKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Controller("/api/v1/pix/keys/{clientId}")
@Validated
class KeyController(
    @Inject private val keyManager: KeyManagerServiceGrpc.KeyManagerServiceBlockingStub
) {

    @Post
    fun create(@QueryValue clientId: UUID, @Body @Valid request: CreatePixKeyRequest): HttpResponse<Any> {

        val response = keyManager.create(request.toGrpcModel())
        val location = HttpResponse.uri("/api/v1/pix/keys/$clientId/${response.pixId}")

        return HttpResponse.created(location)
    }

    @Delete
    fun delete(@QueryValue clientId: UUID, @Body @Valid request: RemovePixKeyRequest): HttpResponse<Any> {
        keyManager.remove(request.toGrpcModel())
        return HttpResponse.ok()
    }

}