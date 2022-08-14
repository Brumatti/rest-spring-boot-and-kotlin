package br.com.pedro.services

import br.com.pedro.controller.BookController
import br.com.pedro.data.vo.v1.BookVO
import br.com.pedro.model.Book

import br.com.pedro.exceptions.RequiredObjectIsNullException
import br.com.pedro.exceptions.ResourceNotFoundException
import br.com.pedro.mapper.DozerMapper
import br.com.pedro.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var repository: BookRepository


    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(): List<BookVO> {
        logger.info("Finding all books!")

        val books = repository.findAll()
        val vos = DozerMapper.parseListObjects(books, BookVO::class.java)
        for (book in vos){
            val withSelfRel = WebMvcLinkBuilder.linkTo(BookController::class.java).slash(book.key).withSelfRel()
            book.add(withSelfRel)

        }
        return vos

    }
    fun findById(id: Long): BookVO {
        logger.info("Finding one book with ID $id!")

        var book = repository.findById(id)
            .orElseThrow{ ResourceNotFoundException("No records found for this ID") }
        val bookVO: BookVO = DozerMapper.parseObject(book, BookVO::class.java)
        val withSelfRel = WebMvcLinkBuilder.linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)

        return bookVO

    }
    fun create(book: BookVO?): BookVO {
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("Creating one book with name ${book.title}")
        var entity: Book = DozerMapper.parseObject(book, Book::class.java)
        val bookVO: BookVO =  DozerMapper.parseObject (repository.save(entity) , BookVO::class.java)
        val withSelfRel = WebMvcLinkBuilder.linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)

        return bookVO
    }


    fun update(book: BookVO?) : BookVO {
        if (book == null) throw RequiredObjectIsNullException()

        logger.info("Updating one book with ID ${book.key}")

        val entity = repository.findById(book.key)
            .orElseThrow{ ResourceNotFoundException("No records found for this ID") }

        entity.author = book.author
        entity.title = book.title
        entity.price = book.price
        entity.launchDate = book.launchDate

        val bookVO: BookVO =  DozerMapper.parseObject (repository.save(entity) , BookVO::class.java)
        val withSelfRel = WebMvcLinkBuilder.linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)

        return bookVO

    }

    fun delete(id: Long) {
        logger.info("Deleting one book with ID ${id}")

        val entity = repository.findById(id)
            .orElseThrow{ ResourceNotFoundException("No records found for this ID") }
        repository.delete(entity)

    }

//    private fun mockbook(i: Int): book {
//        val book = book()
//        book.id = counter.incrementAndGet()
//        book.firstName = "book Name $i"
//        book.lastName = "Last Name $i"
//        book.address = "Some Address in Brasil $i"
//        book.gender = "Male"
//        return book
//    }
    
    
}