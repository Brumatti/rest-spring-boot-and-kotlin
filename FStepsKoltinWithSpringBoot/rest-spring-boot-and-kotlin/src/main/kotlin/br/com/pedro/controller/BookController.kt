package br.com.pedro.controller

import br.com.pedro.data.vo.v1.BookVO
import br.com.pedro.services.BookService
import br.com.pedro.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book/v1")
@Tag(name = "Books", description = "Endpoints for managing Books")
class BookController {


        @Autowired
        private lateinit var service: BookService

        @GetMapping( produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
        @Operation(summary = "Finds all Books", description = "Finds all Books",
            tags = ["Books"],
            responses = [
                ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = [
                        Content(array = ArraySchema(schema = Schema(implementation = BookVO::class)))
                    ]
                ),
                ApiResponse(description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
            ]
        )
        fun finAll(): List<BookVO>{
            return service.findAll()
        }

        @GetMapping(value = ["{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
        @Operation(summary = "Finds a Book", description = "Finds a Book",
            tags = ["Books"],
            responses = [
                ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = [
                        Content(schema = Schema(implementation = BookVO::class))
                    ]
                ),
                ApiResponse(description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
            ]
        )
        fun findById(@PathVariable(value = "id") id: Long): BookVO {
            return service.findById(id)
        }

        @PostMapping(
            produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
            consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
        @Operation(summary = "Adds a new Book", description = "Adds a new Book",
            tags = ["Books"],
            responses = [
                ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = [
                        Content(schema = Schema(implementation = BookVO::class))
                    ]
                ),
                ApiResponse(description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
            ]
        )
        fun create(@RequestBody book: BookVO): BookVO {
            return service.create(book)

        }

        @PutMapping(
            produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
            consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
        @Operation(summary = "Updates a book's information", description = "Updates a book's information",
            tags = ["Books"],
            responses = [
                ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = [
                        Content(schema = Schema(implementation = BookVO::class))
                    ]
                ),
                ApiResponse(description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
            ]
        )
        fun update(@RequestBody book: BookVO): BookVO {
            return service.update(book)

        }

        @DeleteMapping(value = ["{id}"],
            produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
        @Operation(summary = "Delete a book", description = "Delete a book",
            tags = ["Books"],
            responses = [
//            ApiResponse(
//                description = "Success",
//                responseCode = "200",
//                content = [
//                    Content(schema = Schema(implementation = BookVO::class))  Já que não retorna nada, não necessita
//                ]
//            ),
                ApiResponse(description = "No Content", responseCode = "204", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
                ApiResponse(description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ] ),
            ]
        )
        fun delete(@PathVariable(value = "id") id: Long) : ResponseEntity<*> { //Permite o retorno do erro 204 no content
            service.delete(id)
            return ResponseEntity.noContent().build<Any>() //Permite o retorno do erro 204 no content
        }

}


