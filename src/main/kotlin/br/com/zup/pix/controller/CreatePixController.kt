package br.com.zup.pix.controller

import br.com.zup.pix.KeyManagerServiceGrpc
import br.com.zup.pix.controller.request.CreatePixKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Controller("/api/v1/pix/keys/{clientId}")
@Validated
class CreatePixController(
    @Inject private val createKey: KeyManagerServiceGrpc.KeyManagerServiceBlockingStub
) {

    @Post
    fun create(@QueryValue clientId: UUID, @Body @Valid request: CreatePixKeyRequest): HttpResponse<Any> {

        val response = createKey.create(request.toGrpcModel())
        val location = HttpResponse.uri("/api/v1/pix/keys/$clientId/${response.pixId}")

        return HttpResponse.created(location)
    }

}