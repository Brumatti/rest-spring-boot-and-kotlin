package br.com.pedro.services

import br.com.pedro.controller.PersonController
import br.com.pedro.data.vo.v1.PersonVO
import br.com.pedro.exceptions.RequiredObjectIsNullException
import br.com.pedro.exceptions.ResourceNotFoundException
import br.com.pedro.mapper.DozerMapper
import br.com.pedro.model.Person
import br.com.pedro.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {
    @Autowired
    private lateinit var repository: PersonRepository


    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding all peoples!")

        val persons = repository.findAll()
        val vos = DozerMapper.parseListObjects(persons, PersonVO::class.java)
        for (person in vos){
            val withSelfRel = linkTo(PersonController::class.java).slash(person.key).withSelfRel()
            person.add(withSelfRel)

        }
        return vos

    }
    fun findById(id: Long): PersonVO {
        logger.info("Finding one person with ID $id!")

        var person = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this ID")}
        val personVO: PersonVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)

        return personVO

    }
    fun create(person: PersonVO?): PersonVO {
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating one person with name ${person.firstName}")
        var entity: Person = DozerMapper.parseObject(person, Person::class.java)
        val personVO: PersonVO =  DozerMapper.parseObject (repository.save(entity) , PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)

        return personVO
    }


    fun update(person: PersonVO?) : PersonVO{
        if (person == null) throw RequiredObjectIsNullException()

        logger.info("Updating one person with ID ${person.key}")

        val entity = repository.findById(person.key)
            .orElseThrow{ResourceNotFoundException("No records found for this ID")}

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        val personVO: PersonVO =  DozerMapper.parseObject (repository.save(entity) , PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)

        return personVO

    }

    fun delete(id: Long) {
        logger.info("Deleting one person with ID ${id}")

        val entity = repository.findById(id)
            .orElseThrow{ResourceNotFoundException("No records found for this ID")}
        repository.delete(entity)

    }

//    private fun mockPerson(i: Int): Person {
//        val person = Person()
//        person.id = counter.incrementAndGet()
//        person.firstName = "Person Name $i"
//        person.lastName = "Last Name $i"
//        person.address = "Some Address in Brasil $i"
//        person.gender = "Male"
//        return person
//    }
}