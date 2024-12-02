package br.com.budgetpunk.bpcorem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InMemoryRepositoryTest {

    class MockEntity implements Entity {

        private Long id;

        public MockEntity(Long id) {
            this.id = id;
        }

        @Override
        public void id(Long id) {
            this.id = id;
        }

        @Override
        public Long id() {
            return this.id;
        }
    }
    IRepository<MockEntity> repository;

    @Before
    public void setUp() throws Exception {
        List<MockEntity> list = new ArrayList<>();
        list.add(new MockEntity(0L));
        list.add(new MockEntity(1L));
        list.add(new MockEntity(2L));
        list.add(new MockEntity(3L));
        list.add(new MockEntity(4L));

        repository = new InMemoryRepository<>(list);
    }

    @Test
    public void get() {
        assertEquals(4L, repository.get(4L).id().longValue());
    }

    @Test
    public void all() {
        assertEquals(5, repository.all().size());
    }

    @Test
    public void save() {
        MockEntity mockEntity = new MockEntity(null);
        assertEquals(5, repository.all().size());
        repository.save(mockEntity);
        assertEquals(6, repository.all().size());
        assertEquals(5L, mockEntity.id().longValue());
    }

    @Test
    public void delete() {
        assertEquals(5, repository.all().size());
        repository.delete(new MockEntity(4L));
        assertEquals(4, repository.all().size());
    }
}