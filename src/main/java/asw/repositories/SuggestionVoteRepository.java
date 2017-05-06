package asw.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import asw.model.SuggestionVote;

@Repository
public interface SuggestionVoteRepository extends JpaRepository<SuggestionVote, Long> {
	
}
