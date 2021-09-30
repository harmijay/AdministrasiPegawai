package AdministrasiPegawai.kantor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KantorRepository extends JpaRepository<Kantor, Long> {

    @Query("SELECT s FROM Kantor s WHERE s.alamat = ?1")
    Optional<Kantor> findKantorByAddress(String alamat);

    @Query("SELECT s FROM Kantor s WHERE s.id = ?1")
    Optional<Kantor> findKantorById(Long id);
}
