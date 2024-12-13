package org.example.projektdatabas.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.projektdatabas.model.Film;

import java.util.List;
@Transactional // Är en annotation som används för att hantera transaktioner.Är till för att operationer på databasen genomförs inom samma enhet.
@ApplicationScoped//Används för att definera att en klass lever under hela applikationen.
public class MovieRepository {
//Kommunikationen med databasen
    @PersistenceContext
    private EntityManager em; //Detta används för att kunna läsa, ta bort samt upptatera filmerna
//Metoder nedan för att kunna utföra det vi vill med filmerna
    public Film create(Film film) { //Lägger till film till databasen
        em.persist(film);
        return film;
    }

    public Film find(Long id) { //Hämtar film efter ID
        return em.find(Film.class, id);
    }
//Nedan hämtar alla filmer
    public List<Film> findAll() {
        return em.createQuery("SELECT f FROM Film f", Film.class).getResultList();
    }

    //Updaterar med hjälp av denna metod
    public Film update(Film film) {
        return em.merge(film);
    }

    //Ta bort filmer från databasen
    public void delete(Long id) {
        Film film = find(id);
        if (film != null) {
            em.remove(film);
        }
    }
}
