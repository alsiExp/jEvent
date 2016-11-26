package ru.jevent.repository.mock;

import org.springframework.stereotype.Repository;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.Participant;
import ru.jevent.model.additionalEntity.Email;
import ru.jevent.model.enums.Sex;
import ru.jevent.repository.ParticipantRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MockParticipantRepositoryImpl implements ParticipantRepository {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MockParticipantRepositoryImpl.class);

    private static Participant participant;

    static {
        participant = new Participant();
        participant.setId(42L);
        participant.setFirstName("Алексей");
        participant.setLastName("Шипилев");
        participant.setSex(Sex.MALE);
        participant.setBirthDay(LocalDateTime.now().minus(39, ChronoUnit.YEARS));
        participant.setRegistered(LocalDateTime.now().minus(6, ChronoUnit.DAYS));
        participant.addEmail(new Email());
        participant.setPhone("+7-000-000");
        participant.setDescription("description");
    }

    public static Participant getParticipant() {
        return participant;
    }

    @Override
    public Participant save(Participant participant) {
        return this.participant;
    }

    @Override
    public boolean delete(long id) {
        return participant.getId() == id;
    }

    @Override
    public Participant get(long id) {
        return participant;
    }

    @Override
    public Participant getByEmail(String email) {
        return participant;
    }

    @Override
    public List<Participant> getAll() {
        return new ArrayList<Participant>();
    }
}
