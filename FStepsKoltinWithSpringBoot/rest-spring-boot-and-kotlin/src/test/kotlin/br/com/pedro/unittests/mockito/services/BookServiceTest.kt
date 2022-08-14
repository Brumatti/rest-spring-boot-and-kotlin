package br.com.pedro.unittests.mockito.services

import br.com.pedro.exceptions.RequiredObjectIsNullException
import br.com.pedro.repository.BookRepository
import br.com.pedro.services.BookService

import br.com.pedro.unittests.mocks.MockBook
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

internal class BookServiceTest {


    private lateinit var inputObject: MockBook
    @InjectMocks
    private lateinit var service: BookService
    @Mock
    private lateinit var repository: BookRepository

    @BeforeEach
    fun setUp() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)
    }


    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()
        Mockito.`when`(repository.findAll()).thenReturn(list)

        val books = service.findAll()
        assertNotNull(books)
        assertEquals(14,books.size)

        val bookOne = books[1]
        assertNotNull(bookOne)
        assertNotNull(bookOne.key)
        assertNotNull(bookOne.links)
        assertTrue(bookOne.links.toString().contains("</book/v1/1>;rel=\"self\""))
        assertEquals("Um titulo1", bookOne.title)
        assertEquals("Um autor1", bookOne.author)
        assertEquals(25.0, bookOne.price)


        val bookFour = books[4]

        assertNotNull(bookFour)
        assertNotNull(bookFour.key)
        assertNotNull(bookFour.links)
        assertTrue(bookFour.links.toString().contains("</book/v1/4>;rel=\"self\""))
        assertEquals("Um titulo4", bookFour.title)
        assertEquals("Um autor4", bookFour.author)
        assertEquals(25.0, bookFour.price)

        val bookSeven = books[7]

        assertNotNull(bookSeven)
        assertNotNull(bookSeven.key)
        assertNotNull(bookSeven.links)
        assertTrue(bookSeven.links.toString().contains("</book/v1/7>;rel=\"self\""))
        assertEquals("Um titulo7", bookSeven.title)
        assertEquals("Um autor7", bookSeven.author)
        assertEquals(25.0, bookSeven.price)
    }

    @Test
    fun findById() {
        val book = inputObject.mockEntity(1)
        book.id = 1L
        Mockito.`when`(repository.findById(1)).thenReturn(Optional.of(book))

        val result = service.findById(1)
        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</book/v1/1>;rel=\"self\""))
        assertEquals("Um titulo1", result.title)
        assertEquals("Um autor1", result.author)
        assertEquals(25.0, result.price)
    }
    @Test
    fun create() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        Mockito.`when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.create(vo)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</book/v1/1>;rel=\"self\""))
        assertEquals("Um titulo1", result.title)
        assertEquals("Um autor1", result.author)
        assertEquals(25.0, result.price)
    }

    @Test
    fun createWithNullPerson(){
        val exception:Exception = assertThrows(
            RequiredObjectIsNullException::class.java

        ){service.create(null)}
        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun updateWithNullPerson(){
        val exception:Exception = assertThrows(
            RequiredObjectIsNullException::class.java

        ){service.update(null)}
        val expectedMessage = "It is not allowed to persist a null object!"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        Mockito.`when`(repository.findById(1)).thenReturn(Optional.of(entity))
        Mockito.`when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.update(vo)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</book/v1/1>;rel=\"self\""))
        assertEquals("Um titulo1", result.title)
        assertEquals("Um autor1", result.author)
        assertEquals(25.0, result.price)
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(1)

        Mockito.`when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}