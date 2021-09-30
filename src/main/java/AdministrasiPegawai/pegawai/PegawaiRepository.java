package AdministrasiPegawai.pegawai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PegawaiRepository extends JpaRepository<Pegawai, Long> {

    @Query("SELECT p FROM Pegawai p WHERE p.id = ?1")
    Optional<Pegawai> findPegawaiById(Long id);
}
