package br.com.pedro.unittests.mocks

import br.com.pedro.data.vo.v1.BookVO
import br.com.pedro.model.Book
import java.util.ArrayList

class MockBook {

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..13) {
            books.add(mockEntity(i))
        }
        return books
    }

    fun mockVOList(): ArrayList<BookVO> {
        val books: ArrayList<BookVO> = ArrayList()
        for (i in 0..13) {
            books.add(mockVO(i))
        }
        return books
    }

    fun mockEntity(number: Int): Book {
        val book = Book()
        book.title = "Um titulo$number"
        book.author = "Um autor$number"
        book.id = number.toLong()
        book.price = 25.0
        return book
    }

    fun mockVO(number: Int): BookVO {
        val book = BookVO()
        book.title = "Um titulo$number"
        book.author = "Um autor$number"
        book.key = number.toLong()
        book.price = 25.0
        return book
    }
    
}