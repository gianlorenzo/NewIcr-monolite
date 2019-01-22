package it.uniroma3.icr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Manuscript;

@Repository
public interface ManuscriptDao extends JpaRepository<Manuscript, Long> {
    public Manuscript findByName(String name);
}
