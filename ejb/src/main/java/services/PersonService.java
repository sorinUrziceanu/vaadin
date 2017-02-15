package services;

import dao.PersonDAO;
import mapper.PersonMapper;
import model.PersonEntity;
import model.PersonJSF;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PersonService {

    @Inject
    PersonDAO personDAO;
    @Inject
    PersonMapper personMapper;

    public List<PersonEntity> findAll() {
        return personDAO.findAll();
    }

    public void update(PersonJSF person) {
        if(person.getId() != null) {
            PersonEntity personDB = personDAO.find(person.getId());
            personMapper.fromTo(person, personDB);
        } else {
            personDAO.persist(personMapper.toDatabaseModel(person));
        }
    }
}
