package org.example.car.repository;

import org.example.car.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
            select m from Message m
            where ((m.sender.id = :me and m.receiver.id = :other) or (m.sender.id = :other and m.receiver.id = :me))
            order by m.createdAt asc
            """)
    List<Message> findConversation(@Param("me") Long me, @Param("other") Long other);
}

