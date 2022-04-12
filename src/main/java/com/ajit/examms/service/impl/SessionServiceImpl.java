package com.ajit.examms.service.impl;

import com.ajit.examms.domain.Session;
import com.ajit.examms.repository.SessionRepository;
import com.ajit.examms.service.SessionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Session}.
 */
@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private final Logger log = LoggerFactory.getLogger(SessionServiceImpl.class);

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session save(Session session) {
        log.debug("Request to save Session : {}", session);
        return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> partialUpdate(Session session) {
        log.debug("Request to partially update Session : {}", session);

        return sessionRepository
            .findById(session.getId())
            .map(
                existingSession -> {
                    if (session.getName() != null) {
                        existingSession.setName(session.getName());
                    }
                    if (session.getCode() != null) {
                        existingSession.setCode(session.getCode());
                    }
                    if (session.getStartDate() != null) {
                        existingSession.setStartDate(session.getStartDate());
                    }
                    if (session.getEndDate() != null) {
                        existingSession.setEndDate(session.getEndDate());
                    }

                    return existingSession;
                }
            )
            .map(sessionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Session> findAll() {
        log.debug("Request to get all Sessions");
        return sessionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Session> findOne(Long id) {
        log.debug("Request to get Session : {}", id);
        return sessionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Session : {}", id);
        sessionRepository.deleteById(id);
    }
}
