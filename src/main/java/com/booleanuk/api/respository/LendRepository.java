package com.booleanuk.api.respository;

import com.booleanuk.api.model.Lend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LendRepository extends JpaRepository<Lend, Integer> {
    public List<Lend> findAllByActiveTrueAndUserId(int userId);

    public List<Lend> findAllByActiveFalseAndUserId(int userId);

    public Lend findByUserIdAndGameId(int userId, int gameId);
}
