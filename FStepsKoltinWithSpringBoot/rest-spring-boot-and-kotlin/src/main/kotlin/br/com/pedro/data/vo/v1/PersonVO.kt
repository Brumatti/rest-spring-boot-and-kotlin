package br.com.pedro.data.vo.v1

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import org.springframework.hateoas.RepresentationModel


@JsonPropertyOrder("id","first_name", "last_name", "address", "gender") //mudar a ordem
data class PersonVO (
    @Mapping("id")
    @field:JsonProperty("id")
    var key: Long = 0,
    //@field:JsonProperty("first_name") mudar o nome do campo
    var firstName: String = "",

    var lastName: String = "",

    var address: String = "",
    var gender: String = ""
        ): RepresentationModel<PersonVO>()