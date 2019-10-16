package bme.cateringunitmonitor.cateringunitservice.service;

import bme.cateringunitmonitor.api.dto.CateringUnitDTO;
import bme.cateringunitmonitor.cateringunitservice.dao.CateringUnitDAO;
import bme.cateringunitmonitor.cateringunitservice.util.CateringUnitConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchService {

    private static final String[] fields = {"name", "description", "address.address",
            "address.otherInformation", "categoryParameters.value"};

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CateringUnitConverter cateringUnitConverter;

    @Transactional
    @SuppressWarnings("unchecked assignment")
    public List<CateringUnitDTO> search(String searchTerm) {
        log.debug("Search for: {}", searchTerm);
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(CateringUnitDAO.class).get();

        Query query = createQuery(queryBuilder, searchTerm);
        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, CateringUnitDAO.class);

        int resultSize = jpaQuery.getResultSize();
        log.debug("Search result size: {}", resultSize);

        List<CateringUnitDAO> resultList = jpaQuery.getResultList();
        return resultList.stream().map(c -> cateringUnitConverter.convertToDTO(c)).collect(Collectors.toList());
    }

    private Query createQuery(QueryBuilder queryBuilder, String searchTerm) {
        return queryBuilder.simpleQueryString()
                .onFields(fields[0], fields[1], fields[2], fields[3], fields[4])
                .matching(searchTerm)
                .createQuery();
    }
}
