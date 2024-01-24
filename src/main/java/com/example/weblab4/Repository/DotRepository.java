package com.example.weblab4.Repository;

import com.example.weblab4.Database.Dot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DotRepository extends JpaRepository<Dot, Long> {
    List<Dot> getDotsByOwner(String owner);

    void deleteByOwner(String owner);
}
